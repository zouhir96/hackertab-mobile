# Hackertab v4 — Parallel Implementation Plan

> **Goal**: Implement the redesign in `design/project/Hackertab Redesign.html` against the existing Kotlin Multiplatform + Compose Multiplatform codebase, optimized so that multiple sub-agents can work in parallel without merge conflicts.
>
> **Strategy in one line**: Sequential foundation waves first, then a fan-out into parallel lanes whose file ownership never overlaps, then a sequential merge wave, then parallel polish.

---

## 1. The seven waves

| Wave | Mode | Concurrency | What lands | Blocking? |
|------|------|-------------|------------|-----------|
| **0 — Foundation** | Sequential | 1 agent | Tokens, theme, fonts, dependencies, DataStore keys | **Blocks everything** |
| **1 — M2 → M3 mass migration** | Sequential | 1 agent | Mechanical rewrite of every existing Composable to Material 3 | **Blocks Wave 2+** |
| **2 — Component library** | Parallel | 5 agents | New components in `core/design`, no overlap | Blocks Wave 3 |
| **3 — Feature redesigns** | Parallel | 4 agents | Onboarding, Home, Bookmarks, Settings rewritten against the new components | Blocks Wave 4 |
| **4 — Navigation & integration** | Sequential | 1 agent | Bottom nav, new routes, WebView modal, theme wiring | Blocks Wave 5+ |
| **5 — Motion + aggregator + coachmarks** | Parallel | 3 agents | Spring animations, Today aggregator, post-onboarding coachmarks | Blocks Wave 6 (partially) |
| **6 — A11y + tablet + critique fixes** | Parallel | 3 agents | Contrast pass, NavRail/list-detail, top-15 critique items | Blocks Wave 7 |
| **7 — Cleanup & ship** | Sequential | 1 agent | Dead code removal, package rename, lint, version bump, release notes | — |

**Total agent-hours estimate**: ~60–80 agent-hours of work, but with parallelism, **wall-clock time ≈ 18–24 hours** of real-time work distributed across 4–5 days, depending on validation cycle length.

---

## 2. Why this order

The codebase has three properties that determine the parallelization seams:

1. **Material 2 is wired everywhere.** Every screen imports from `androidx.compose.material.*` and reads `MaterialTheme.colors.*`. If we let parallel agents write Material 3 code while old screens still use M2, the same `MaterialTheme` symbol resolves to two different types depending on import. We get a half-migrated app where neither half builds. **Wave 1 must fully migrate everything to M3 before any parallel work.**

2. **The design system is the foundation.** Every parallel feature agent in Wave 3 depends on tokens (`brand-primary`, `surface`, `on-bg`, `radiusLg`, `spacing16`, the 11 motion durations) and on new shared components (SourceRail, BottomNav, ArticleCard, RepoCard). If those don't exist, four feature agents reinvent them four ways. **Wave 0 + Wave 2 must complete before Wave 3.**

3. **Feature modules are file-isolated by Gradle.** The codebase already has `feature/onboarding`, `feature/home`, `feature/settings`, `feature/bookmarks` as separate Gradle modules with separate file trees. **One feature module per agent** in Wave 3 = zero cross-file conflicts.

The only true sequential bottleneck is `shared/src/commonMain/kotlin/com/zrcoding/hackertab/shared/navigation/MainNavHost.kt` and `shared/.../HackertabKmpApp.kt` — the navigation graph and root composable. Every feature ultimately wires into that file. Wave 4 owns it.

---

## 3. Branch & worktree strategy

```
main
└── feat/v4-redesign                      ← long-lived integration branch
    ├── feat/v4-w0-foundation             ← Wave 0 (sequential)
    ├── feat/v4-w1-m3-migration           ← Wave 1 (sequential, after w0)
    ├── feat/v4-w2-comp-shell              ┐
    ├── feat/v4-w2-comp-rail               │
    ├── feat/v4-w2-comp-cards              ├── Wave 2 (parallel, 5 worktrees)
    ├── feat/v4-w2-comp-states             │
    ├── feat/v4-w2-comp-controls           ┘
    ├── feat/v4-w3-feat-onboarding         ┐
    ├── feat/v4-w3-feat-home               │
    ├── feat/v4-w3-feat-bookmarks          ├── Wave 3 (parallel, 4 worktrees)
    ├── feat/v4-w3-feat-settings           ┘
    ├── feat/v4-w4-nav                    ← Wave 4 (sequential)
    ├── feat/v4-w5-motion                  ┐
    ├── feat/v4-w5-aggregator              ├── Wave 5 (parallel, 3 worktrees)
    ├── feat/v4-w5-coachmarks              ┘
    ├── feat/v4-w6-a11y                    ┐
    ├── feat/v4-w6-tablet                  ├── Wave 6 (parallel, 3 worktrees)
    ├── feat/v4-w6-critique                ┘
    └── feat/v4-w7-cleanup                ← Wave 7 (sequential, last)
```

**Use `Agent({ isolation: "worktree" })`** for every parallel agent in Waves 2, 3, 5, 6. Each gets its own working tree on its own branch, branched from `feat/v4-redesign` at the moment its predecessor wave merged.

**Merge gates between waves**: Don't start Wave N until every branch of Wave N-1 has merged to `feat/v4-redesign` and the integration branch builds green on Android and iOS.

---

## 4. Wave-by-wave breakdown

### Wave 0 — Foundation (sequential, ~3h)

**One agent. Do not parallelize.**

**Owns these files exclusively**:
- `gradle/libs.versions.toml` (add Material 3, Geist fonts, Compose pull-refresh, fragment Compose)
- `core/design/src/commonMain/composeResources/font/` (add Geist Sans 400/500/600/700, Geist Mono 500/600 — `.ttf`)
- `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/theme/Color.kt` (replace with new OKLCH-derived sRGB values)
- `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/theme/Type.kt` (M3 `Typography` with new ramp)
- `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/theme/Dimens.kt` (extend spacing scale to 12 stops)
- `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/theme/Shape.kt` (M3 `Shapes` with new radii)
- `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/theme/Theme.kt` (rewrite as M3 `MaterialTheme(colorScheme = …)`, add `darkTheme: Boolean? = null` + `useDynamicColor: Boolean = false` params, new `HackertabTheme(themeMode = ThemeMode.SYSTEM, content)` API)
- `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/theme/Motion.kt` (NEW — durations, easings, springs as constants)
- `core/data/src/commonMain/kotlin/com/zrcoding/hackertab/data/datastore/SettingsKeys.kt` (NEW — add `KEY_THEME_MODE`, `KEY_COACHMARKS_SEEN`, `KEY_LAST_VISITED_AT`)
- `core/domain/src/commonMain/kotlin/com/zrcoding/hackertab/domain/models/ThemeMode.kt` (NEW — `enum class ThemeMode { LIGHT, DARK, SYSTEM }`)

**Deliverables**:
1. `./gradlew :core:design:build` green.
2. New `HackertabTheme` API documented in a leading KDoc on `Theme.kt` showing the call site signature.
3. Token name → CSS variable mapping written as a short table in commit message (so every other agent can reference it).
4. The integration branch builds: `./gradlew assembleDebug` green and `./gradlew :composeApp:linkPodDebugFrameworkIosArm64` green.

**Risk**: dynamic-color (Material You) is Android-12+. Default `useDynamicColor = false`. Don't ship dynamic color in v4.

---

### Wave 1 — M2 → M3 mass migration (sequential, ~5h)

**One agent. Do not parallelize. Mechanical translation.**

This is the most boring and most important wave. Without it, every parallel agent later will have to migrate the file they touch, and merges will be ugly.

**Mechanical replacements** (find/replace across the entire repo, in this order):

| From | To | Notes |
|------|----|-------|
| `import androidx.compose.material.MaterialTheme` | `import androidx.compose.material3.MaterialTheme` | |
| `import androidx.compose.material.Text` | `import androidx.compose.material3.Text` | |
| `import androidx.compose.material.Card` | `import androidx.compose.material3.Card` | M3 `Card` API differs — see notes |
| `import androidx.compose.material.Button` | `import androidx.compose.material3.Button` | colors API differs |
| `import androidx.compose.material.OutlinedButton` | `import androidx.compose.material3.OutlinedButton` | |
| `import androidx.compose.material.TextButton` | `import androidx.compose.material3.TextButton` | |
| `import androidx.compose.material.Icon` | `import androidx.compose.material3.Icon` | |
| `import androidx.compose.material.IconButton` | `import androidx.compose.material3.IconButton` | |
| `import androidx.compose.material.Scaffold` | `import androidx.compose.material3.Scaffold` | API differs (no `scaffoldState` — use `SnackbarHostState`) |
| `import androidx.compose.material.TopAppBar` | `import androidx.compose.material3.TopAppBar` | API differs |
| `import androidx.compose.material.Divider` | `import androidx.compose.material3.HorizontalDivider` | |
| `import androidx.compose.material.CircularProgressIndicator` | `import androidx.compose.material3.CircularProgressIndicator` | |
| `import androidx.compose.material.AlertDialog` | `import androidx.compose.material3.AlertDialog` | |
| `import androidx.compose.material.DropdownMenu` | `import androidx.compose.material3.DropdownMenu` | will be deleted in Wave 3 anyway |
| `import androidx.compose.material.FilterChip` | `import androidx.compose.material3.FilterChip` | |
| `import androidx.compose.material.ChipDefaults` | `import androidx.compose.material3.FilterChipDefaults` | |
| `import androidx.compose.material.rememberDrawerState` | (delete — drawer is gone in Wave 4) | |
| `MaterialTheme.colors.primary` | `MaterialTheme.colorScheme.primary` | |
| `MaterialTheme.colors.background` | `MaterialTheme.colorScheme.background` | |
| `MaterialTheme.colors.onBackground` | `MaterialTheme.colorScheme.onBackground` | |
| `MaterialTheme.colors.surface` | `MaterialTheme.colorScheme.surface` | |
| `MaterialTheme.colors.secondary` | `MaterialTheme.colorScheme.secondaryContainer` | re-eyeball — secondary semantics differ |
| `MaterialTheme.colors.error` | `MaterialTheme.colorScheme.error` | |
| `MaterialTheme.typography.h4` | `MaterialTheme.typography.headlineLarge` | |
| `MaterialTheme.typography.h5` | `MaterialTheme.typography.headlineMedium` | |
| `MaterialTheme.typography.h6` | `MaterialTheme.typography.headlineSmall` | |
| `MaterialTheme.typography.subtitle1` | `MaterialTheme.typography.titleMedium` | |
| `MaterialTheme.typography.subtitle2` | `MaterialTheme.typography.titleSmall` | |
| `MaterialTheme.typography.body1` | `MaterialTheme.typography.bodyLarge` | |
| `MaterialTheme.typography.body2` | `MaterialTheme.typography.bodyMedium` | |
| `MaterialTheme.typography.button` | `MaterialTheme.typography.labelLarge` | |
| `MaterialTheme.typography.caption` | `MaterialTheme.typography.bodySmall` | |
| `MaterialTheme.typography.overline` | `MaterialTheme.typography.labelSmall` | |
| `MaterialTheme.shapes.small` | `MaterialTheme.shapes.small` | retain — but values from Wave 0 |
| `backgroundColor = MaterialTheme.colors.X` (M2 Card/Button) | `colors = CardDefaults.cardColors(containerColor = ...)` | M3 API |
| `elevation = 4.dp` (M2 Card) | `elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)` | |
| `Button(elevation = null)` | `Button(elevation = ButtonDefaults.buttonElevation(0.dp))` | |
| `Divider()` | `HorizontalDivider()` | |

**API-shape changes that need manual review**:
- `Scaffold` no longer takes `scaffoldState`. Replace with `SnackbarHostState` passed via `snackbarHost = { SnackbarHost(state) }`. Affects `HomeScreen.kt` (line ~133) and `MainNavHost.kt` (line ~267).
- `TopAppBar` no longer accepts `backgroundColor` and `elevation` directly. Use `TopAppBarDefaults.topAppBarColors(containerColor = …)` and `TopAppBarDefaults.pinnedScrollBehavior()`. Affects `HomeScreen.kt:255` and `MainNavHost.kt:269`.
- `Card(onClick = …)` is now stable (no `@OptIn(ExperimentalMaterialApi::class)` needed). Affects `HomeScreenDrawerItem` (line ~410, will be deleted in Wave 4 anyway).
- `FilterChip` API in M3: `selected`, `onClick`, `label = { Text(...) }`, `colors = FilterChipDefaults.filterChipColors(...)`. Affects `HomeScreenTopicsFilter` (line ~454).
- `LocalMinimumInteractiveComponentEnforcement` is renamed to `LocalMinimumInteractiveComponentSize` in M3, but **REMOVE all usages entirely** — the brief and critique both flag this as an a11y problem. Affects `HomeScreen.kt:461` and `Components.kt`.
- `ExperimentalMaterialApi` opt-ins → mostly removable in M3.

**Files to delete entirely** (empty after migration):
- None in this wave. Drawer code path stays until Wave 4.

**Deliverables**:
1. Zero remaining `import androidx.compose.material.*` lines (except `material.icons.*` which is fine — Material Icons is shared between M2 and M3).
2. `./gradlew assembleDebug` green.
3. iOS framework links: `./gradlew :composeApp:linkPodDebugFrameworkIosArm64` green.
4. Visual smoke check: app launches and renders **all 11 existing screens** without crashes. They will look slightly off (typography sizes have shifted, colors will read different against the new tokens), but they must render.

**Acceptance criteria for the agent**: a grep for `MaterialTheme.colors` returns zero hits across `app`, `feature`, `core`, `shared`. A grep for `MaterialTheme.typography.h[1-6]` or `subtitle[12]|body[12]|caption|overline` returns zero hits.

---

### Wave 2 — Component library (5 agents, parallel, ~6h wall-clock)

All five agents run in parallel worktrees branched from `feat/v4-w1-m3-migration`. They write **new files only** in `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/components/`, so file-level conflicts are zero.

**Each agent's prompt template** (fill in the lane-specific bits):

> You are implementing Hackertab's redesigned component library in Compose Multiplatform.
>
> **Visual reference**: open `design/project/components/Components.jsx` and `design/project/components/Library.jsx` and `design/project/styles/tokens.css`. The HTML prototype at `design/project/Hackertab Redesign.html` (tab "03 Library") shows the rendered components.
>
> **Brief**: see `DESIGN_AGENT_MASTER_PROMPT.md` §"Design System Expectations" for the required component anatomy, variants, states, tokens, and a11y notes.
>
> **Tokens** are already defined in `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/theme/`. Use `MaterialTheme.colorScheme.*`, `MaterialTheme.typography.*`, `MaterialTheme.dimension.*`, `MaterialTheme.motion.*` (do not hardcode values).
>
> **Your lane** (only touch these files):
> _<lane file list>_
>
> **Out of bounds** (do not modify):
> - Anything outside `core/design/src/commonMain/kotlin/com/zrcoding/hackertab/design/components/`
> - The theme/ directory
> - Any feature/* or app/* file
>
> **Acceptance**: every component has a `@Preview` in light + dark, builds clean, includes content descriptions, and merges semantics for compound items. Use `kotlinx.collections.immutable.PersistentList` for any list-typed parameters.

#### Agent 2A — Shell components
**Branch**: `feat/v4-w2-comp-shell`
**Files (new)**:
- `core/design/.../components/AppBar.kt` — `HackertabAppBar(title, leading, trailing, wordmark, subtitle)`
- `core/design/.../components/BottomNav.kt` — `HackertabBottomNav(items, active, onSelect)` with backdrop blur (Android fallback to flat `surface @ 0.85`)
- `core/design/.../components/NavRail.kt` — `HackertabNavRail(items, active, onSelect)` (tablet)
- `core/design/.../components/SectionHeader.kt` — `SectionHeader(label, count?)` sticky-style
- `core/design/.../components/OnboardingStepIndicator.kt` — 3-segment progress bar

#### Agent 2B — Navigation primitives (the heart of the redesign)
**Branch**: `feat/v4-w2-comp-rail`
**Files (new)**:
- `core/design/.../components/SourceRail.kt` — `SourceRail(sources, activeSourceId, onSelect)` with the "All" pseudo-source built in. Spring physics for the active pill (use `MaterialTheme.motion.springEmphasized`).
- `core/design/.../components/TopicChipStrip.kt` — `TopicChipStrip(topics, activeTopicId, onSelect, canAddTopic, onAddTopic)`. Crossfade-only animation (no inverted shock state — apply the **Issue 4 fix from the critique**: use `surface-variant` bg + `on-bg` text on active, not `on-bg` bg + `bg` text).
- `core/design/.../components/SourceTag.kt` — small composable used inside cards to render the source's icon-block + label + time-ago.

#### Agent 2C — Cards
**Branch**: `feat/v4-w2-comp-cards`
**Files (new)**:
- `core/design/.../components/cards/CardShell.kt` — wrapping container with `fresh: Boolean`, `read: Boolean`, the 3dp left-edge "fresh" bar, and slot for content. **Apply Issue 6 + 7 fixes from the critique**: tone read opacity to 0.78, strengthen the fresh indicator with a small "NEW" pill in the source tag row.
- `core/design/.../components/cards/CardActions.kt` — bookmark + kebab pair (small, 30dp, ghosted on idle, brand-primary when bookmarked).
- `core/design/.../components/cards/ArticleCard.kt` — generic article card; takes a `metaContent: @Composable RowScope.() -> Unit` slot for source-specific meta. The 9 article-source-specific meta variants are dispatched at the call site (Wave 3 / feature/home).
- `core/design/.../components/cards/RepoCard.kt` — GitHub-specific (mono `owner/repo` with brand-primary repo, language dot + stars + forks).
- `core/design/.../components/cards/LaunchCard.kt` — ProductHunt-specific (Kamel `KamelImage` for thumbnail, vertical upvote pill, comments). **Apply Issue 9 fix**: `KamelImage(asyncPainterResource(product.imageUrl))` with a placeholder fallback.
- `core/design/.../components/cards/ConferenceCard.kt` — calendar-block date + location + tags.
- `core/design/.../components/cards/BookmarkCard.kt` — bookmark-list-row variant with swipe-to-remove affordance (rendered by `SwipeToDismissBox` from M3).

#### Agent 2D — States
**Branch**: `feat/v4-w2-comp-states`
**Files (new)**:
- `core/design/.../components/states/EmptyState.kt` — illustrated + headline + body + 0/1/2 CTAs slot.
- `core/design/.../components/states/ErrorState.kt` — same shape, error-tinted icon container, retry CTA.
- `core/design/.../components/states/OfflineChip.kt` — pinned offline indicator chip with retry button.
- `core/design/.../components/states/LoadingSkeleton.kt` — shimmer placeholder rows matching `ArticleCard` rhythm. Use `androidx.compose.animation.core.InfiniteTransition` (CMP-safe). Honor `LocalAccessibilityManager` reduced-motion → static state.
- `core/design/.../components/states/HackertabSnackbarHost.kt` — themed wrapper around M3 `SnackbarHost`.

#### Agent 2E — Controls & sheets
**Branch**: `feat/v4-w2-comp-controls`
**Files (new)**:
- `core/design/.../components/buttons/PrimaryButton.kt`, `SecondaryButton.kt`, `TextButton.kt`, `DestructiveButton.kt`, `IconActionButton.kt`. Sizes: sm (32), md (42), lg (54). States: default / pressed (0.97 scale) / disabled / loading.
- `core/design/.../components/inputs/InputField.kt` — leading icon, placeholder, clear button on focus, optional error caption.
- `core/design/.../components/sheets/ActionSheet.kt` — bottom sheet with drag handle and a list of `ActionRow`. Used by long-press on cards and by Theme picker.
- `core/design/.../components/inputs/SegmentedControl.kt` — pill-style segmented control (used in Bookmarks "All / By source / By date").
- `core/design/.../components/Chips.kt` — `HackertabFilterChip`, `HackertabInputChip`, `HackertabSuggestionChip` wrapping M3 chip APIs with our tokens.

**Wave 2 merge order (sequential merges, even though work is parallel)**:
1. 2A merges first (everyone needs AppBar, BottomNav).
2. 2B (rail + chips) merges second.
3. 2C, 2D, 2E in any order after.
4. Final integration commit on `feat/v4-redesign` runs `./gradlew :core:design:build`.

---

### Wave 3 — Feature redesigns (4 agents, parallel, ~10h wall-clock)

Each agent owns **one feature module entirely**. Conflicts are limited to a few cross-cutting writes documented below.

**Each agent's prompt template**:

> You are implementing the redesigned <FEATURE NAME> feature for Hackertab v4 in Compose Multiplatform.
>
> **Visual reference**: open `design/project/components/Screens.jsx` and find the `<SCREEN NAMES>` composables. The HTML prototype at `design/project/Hackertab Redesign.html` (tab "04 Screens") renders them live.
>
> **Brief**: `DESIGN_AGENT_MASTER_PROMPT.md` §"Screen-by-Screen Description" and §"User Flows" describe the intended functionality and copy. Preserve every existing function in §"Features That Must Be Preserved".
>
> **Components**: import from `com.zrcoding.hackertab.design.components.*`. Do not redefine `SourceRail`, `TopicChipStrip`, `AppBar`, etc. — those exist now (Wave 2).
>
> **Your lane**:
> _<feature module path + cross-cutting writes if any>_
>
> **Out of bounds**:
> - Other feature modules
> - `core/design/.../theme` and `core/design/.../components` (read-only)
> - `shared/.../navigation/MainNavHost.kt` — Wave 4 owns it
>
> **Wire-up note**: when you add a new screen route, declare its `NavKey` and add a `// TODO Wave 4: register in MainNavHost` comment. Do NOT modify MainNavHost yourself.
>
> **Acceptance**: every screen builds, has a `@Preview`, observes its ViewModel via `koinViewModel()`, fires its `TrackScreenViewEvent`, and matches the HTML prototype within reasonable margin.

#### Agent 3F — Onboarding
**Branch**: `feat/v4-w3-feat-onboarding`
**Lane**: `feature/onboarding/src/commonMain/kotlin/**` (entire module)
**Cross-cutting writes (additive only — no merge conflicts expected)**:
- `core/design/.../composeResources/values/strings.xml` — add new onboarding copy keys
- `core/data/.../datastore/SettingRepositoryImpl.kt` — read/write `KEY_COACHMARKS_SEEN`

**Screens to deliver**:
- `SetupProfileScreen` — 3-column grid of profile cards with emoji glyphs + sub-label, OnboardingStepIndicator at top.
- `SetupTopicsScreen` — accordion of categories with expansion state, count of selected per category.
- `SetupSourcesScreen` — 2-column grid of source tiles with circular brand-icon block and check badge when selected.
- `OnboardingDoneScreen` (NEW) — "Your feed is ready" headline, 3 numbered orientation cards, "Open my feed" CTA. After tapping, sets `coachmarks_seen = false` so Wave 5 coachmarks fire on first feed load.

**ViewModel changes**: minor — same flows, but mention the `coachmarks_seen` flag set on completion.

#### Agent 3G — Home / Today
**Branch**: `feat/v4-w3-feat-home`
**Lane**: `feature/home/src/commonMain/kotlin/**`, `feature/home/src/androidMain/kotlin/**`, `feature/home/src/iosMain/kotlin/**`
**Cross-cutting writes**:
- `core/data/.../repositories/AggregatedArticleRepository.kt` — NEW interface in `core/domain` + impl in `core/data` for the "All" pseudo-source. Stub/serial impl in this wave; Wave 5 polishes it.
- `core/domain/.../usecases/GetAggregatedFeedUseCase.kt` — NEW
- `core/data/.../datastore/SettingRepositoryImpl.kt` — read/write `KEY_LAST_VISITED_AT`

**Screens to deliver**:
- `TodayScreen` — multi-source aggregated feed, day-grouped (`Today / Yesterday / Earlier this week`), `SourceRail` with "All" + enabled sources, `TopicChipStrip` below, pull-to-refresh wired (use `androidx.compose.material3.pulltorefresh`).
- `FocusedFeedScreen` — same scaffold but `SourceRail.activeId` is a real source; cards filtered to that source.
- `WebViewScreen` — modal-style with bottom action bar (Save / Share / Open-in-browser); on tablet, no chrome (lives in detail pane).
- `LongPressActionSheet` — bottom sheet with Save / Share / Open in browser / Copy link.
- The 12 source-specific cards: each is a small file in `feature/home/.../cards/<source>/<Source>Item.kt` that uses the shared `ArticleCard`/`RepoCard`/`LaunchCard`/`ConferenceCard` from Wave 2 and supplies the source-specific meta slot.

**ViewModel changes**: significant. `HomeViewModel` now exposes:
- An `activeSourceId: String` ("all" or a source id)
- An aggregated articles stream when `activeSourceId == "all"`
- Per-day grouping via a derived state in the screen layer
- A new `refresh()` function for pull-to-refresh (rename of existing `onRefreshBtnClick()`)
- A `markRead(articleId)` function that updates `bookmarks.read = true` if bookmarked, OR a session-only seen-set otherwise (to avoid Room migrations from this agent — Bookmarks agent handles read column)

**Critique fixes baked in here** (from my critique, Issues 3, 6, 7, 11, 14):
- Cards' `read` opacity at 0.78 not 0.65.
- Strengthened "fresh" indicator with NEW pill in source tag.
- Drop the `★ All` star — just "All" with slightly different weight.
- Search icon in AppBar removed from feed (lives only in Bookmarks, where its scope is real).

#### Agent 3H — Bookmarks
**Branch**: `feat/v4-w3-feat-bookmarks`
**Lane**: `feature/bookmarks/src/commonMain/kotlin/**`
**Cross-cutting writes**:
- `core/database/.../entities/BookmarkedArticleEntity.kt` — add `read: Boolean = false` column
- `core/database/.../daos/BookmarkedArticleDao.kt` — add `markRead(id: String)` query
- `core/database/schemas/com.zrcoding.hackertab.database.AppDatabase/1.json` → bump schema to `2.json`
- `core/database/.../AppDatabase.kt` — increment version to 2; add `Migration_1_2` adding `read INTEGER NOT NULL DEFAULT 0`
- `core/domain/.../models/BookmarkedArticle.kt` — add `read: Boolean = false`

**Screens to deliver**:
- `BookmarksScreen` — `AppBar(title="Bookmarks", subtitle="N saved · M unread")`, segmented control (All / By source / By date), bookmark rows with unread dot + brand-icon block + kebab. Swipe-to-remove via `SwipeToDismissBox`.
- `BookmarksSearchScreen` — collapsing search input, result count, highlighted matches (use `<mark>`-style spans via `AnnotatedString`).
- `BookmarksEmptyState` — illustrated empty state with "Browse Today" CTA.

**ViewModel changes**: add search query state, group-by mode state, `removeBookmark` already exists.

#### Agent 3I — Settings
**Branch**: `feat/v4-w3-feat-settings`
**Lane**: `feature/settings/src/commonMain/kotlin/**`
**Cross-cutting writes**:
- `core/data/.../datastore/SettingRepositoryImpl.kt` — read/write `KEY_THEME_MODE` (added in Wave 0)

**Screens to deliver**:
- `SettingsMasterScreen` (NEW) — identity card at top ("You're set up as Mobile Engineer · Change"), Feed section (Topics / Sources), App section (Appearance / About / Send feedback), version footer.
- `SettingsTopicsScreen` — same accordion as onboarding, but persists on every tap; show count selected per category in the header. Snackbar on min-1 violation (Topics has no min-1 in original; preserve that — no snackbar needed unless we add one).
- `SettingsSourcesScreen` — list of source rows with brand-icon block, label, "Filterable by topic" / "Source-wide" caption, and an iOS-style `Switch`. **Surface the silent min-1-source guard with a snackbar** ("You need at least one source enabled.") — this is the Issue from the original UX problems.
- `SettingsAppearanceScreen` (NEW) — 3-row Light / Dark / System list, plus side-by-side preview tiles. Persists `themeMode` to DataStore.
- `SettingsAboutScreen` (NEW) — identity card with logo, version, build number; link rows for Send feedback, Source code, Privacy, Rate on App Store.

**ViewModel changes**: existing `SettingTopicsViewModel`, `SettingSourcesViewModel` keep their flows; add `SettingsMasterViewModel` exposing `Profile + topicsCount + sourcesCount + themeMode`. Add `SettingsAppearanceViewModel` to read/write theme mode.

**Theme wiring**: `HackertabKmpApp` (in `:shared`) now reads `themeMode` from DataStore and passes to `HackertabTheme(themeMode = …)`. **Wave 4 owns the wire-up** — Agent 3I provides the ViewModel + DataStore key + describes the integration in a comment.

---

#### Wave 3 merge order
1. 3I merges first (Settings has the smallest blast radius and wires theme).
2. 3H merges second (database migration is invasive but isolated).
3. 3G merges third (largest agent, most file changes).
4. 3F merges last.
5. Final integration commit on `feat/v4-redesign` runs `./gradlew assembleDebug` and the iOS link.

---

### Wave 4 — Navigation & integration (sequential, ~4h)

**One agent. No parallelization possible — `MainNavHost.kt` is a single file.**

**Owns these files exclusively**:
- `shared/src/commonMain/kotlin/com/zrcoding/hackertab/shared/navigation/MainNavHost.kt`
- `shared/src/commonMain/kotlin/com/zrcoding/hackertab/shared/HackertabKmpApp.kt`
- `shared/src/commonMain/kotlin/com/zrcoding/hackertab/shared/di/KoinHelper.kt` (if new VMs need wiring)

**Tasks**:
1. **Define new `NavKey` routes**: `OnboardingDoneScreen`, `SettingsMasterScreen`, `SettingsAppearanceScreen`, `SettingsAboutScreen`. Add to `SavedStateConfiguration`.
2. **Wire bottom navigation**: introduce a `BottomNavScaffold` composable around the three top-level destinations (`HomeScreen`, `BookmarksScreen`, `SettingsMasterScreen`). Each tab maintains its own back stack. (Use Navigation 3's nested `NavBackStack` per tab, OR a simpler "current tab" state with three sibling backstacks.)
3. **Remove drawer code path**: delete `HomeScreenDrawer`, `HomeScreenDrawerItem`, `AppVersionName` from `HomeScreen.kt`. Delete the `Scaffold(scaffoldState = …, drawerContent = …)` wrapper. The `onNavigateToTopicsSettings`, `onNavigateToSourcesSettings`, `onNavigateToBookmarks` parameters of `HomeRoute` become tab-bar routing instead.
4. **Update onboarding flow**: after `SetupSourcesRoute`'s `navigateToNextScreen`, instead of going straight to `HomeScreen`, push `OnboardingDoneScreen`. Tapping "Open my feed" clears the back stack and pushes `HomeScreen` (the bottom nav root).
5. **WebView modal style**: WebView is a sheet-like push from a feed card. On phones, it's a full-screen route. On tablets, it's the detail pane (already wired in `ListDetailSceneStrategy`).
6. **Theme wiring**: `HackertabKmpApp` reads `themeMode` from DataStore via a `koinInject<SettingRepository>().observeThemeMode().collectAsStateWithLifecycle()`. Pass to `HackertabTheme(themeMode = mode)`.
7. **Coachmark gating**: after `HackertabKmpApp` finishes its `setupStatus` async load AND the user is on `HomeScreen` AND `coachmarks_seen == false`, fire the coachmark overlay. (Wave 5 builds the actual overlay; Wave 4 only wires the gate.)
8. **Delete dead `MainNavHost` entries**: drawer, old setup flows that no longer reach Home directly.

**Deliverables**:
1. `./gradlew assembleDebug` green.
2. iOS framework link green.
3. Manual smoke test of the full onboarding → home → tab switch → settings → bookmarks → webview → back loop.
4. Bottom nav appears on Today, Bookmarks, Settings only — not on onboarding, not on WebView, not on settings sub-screens.

---

### Wave 5 — Motion + aggregator + coachmarks (3 agents, parallel, ~6h wall-clock)

#### Agent 5J — Motion polish
**Branch**: `feat/v4-w5-motion`
**Lane**: edits inside Wave 2 components only (animations attached as modifiers). Touches:
- `core/design/.../components/SourceRail.kt` — spring physics for active pill (M1 in motion spec)
- `core/design/.../components/TopicChipStrip.kt` — 150ms color crossfade (M2)
- `core/design/.../components/cards/CardShell.kt` — 80ms in / 200ms out scale-down on press (M3)
- `core/design/.../components/cards/CardActions.kt` — bookmark icon morph + scale pulse (M4)
- `core/design/.../components/states/PullToRefreshSpinner.kt` (NEW) — custom Hackertab logo spinner with rotation (M5)
- `core/design/.../components/cards/CardShell.kt` — list-entry stagger fade-in via `LaunchedEffect` (M6)
- `feature/home/.../HomeScreen.kt` — source-switch list fade out / skeleton swap / fade in (M7)
- `feature/home/.../WebViewRoute.kt` — Article→WebView shared-element transition (M8) — **fallback to vertical slide-up if shared elements are awkward in Navigation 3 alpha**

**Reduced-motion contract**: every motion above must check `LocalAccessibilityManager.current.isReduceMotionEnabled` (or the iOS equivalent through CMP) and degrade to instant state swap.

#### Agent 5K — Coachmarks
**Branch**: `feat/v4-w5-coachmarks`
**Lane**: new files only.
- `feature/onboarding/.../coachmarks/CoachmarkOverlay.kt` (NEW) — full-screen scrim + spotlight cutout + pointer arrow + body card.
- `feature/onboarding/.../coachmarks/CoachmarkSequence.kt` (NEW) — 3-step state machine: "Tap a source", "Pull to refresh", "Long-press a card".
- `feature/onboarding/.../coachmarks/CoachmarkViewModel.kt` (NEW) — reads/writes `coachmarks_seen`. Exposes `currentStep: StateFlow<Step?>` and `next()`, `skip()`.
- Hook into `HomeScreen` via a `CoachmarkOverlay()` Box overlay when `currentStep != null`.

#### Agent 5L — Aggregator polish
**Branch**: `feat/v4-w5-aggregator`
**Lane**: `core/data/.../repositories/AggregatedArticleRepository.kt` (the stub from Wave 3) + wiring in HomeViewModel.
- Implement parallel `coroutineScope { sources.map { async { fetch(it) } }.awaitAll() }` fan-out.
- De-dup by URL (canonicalized).
- Order by `published_at desc`, with source-priority tiebreak (GitHub > HN > DevTo > Reddit > … as a stable hash).
- Per-source loading state: `Flow<List<SourceLoadState>>` exposed alongside the aggregated articles flow. HomeScreen shows a small "GitHub unavailable" mono caption inline if a source returns 5xx, while still rendering the rest of the feed.
- "Partial load" UI: until at least 50% of sources have responded, show skeletons. After 50%, render whatever has arrived. Update the source rail to badge unloaded sources (small spinner glyph instead of brand icon).

**This addresses Issue 3 of the critique directly. Without this, the All view is a wall of "loading..." until the slowest source resolves.**

---

### Wave 6 — A11y + tablet + critique fixes (3 agents, parallel, ~5h wall-clock)

#### Agent 6M — Accessibility sweep
**Branch**: `feat/v4-w6-a11y`
**Lane**: cross-cutting (sweep mode). Touches every screen file in `feature/*` and component file in `core/design/*`, but the scope per file is narrow: add/repair `Modifier.semantics`, `contentDescription`, `mergeDescendants`, `role`, etc.

**Tasks**:
1. **Contrast bump** — replace `MaterialTheme.colorScheme.onSurfaceVariant` (mapped to `--on-surface-muted`) for body-sized text with a darker token (`neutral-600` → ~`oklch(38%)`). Affects `tokens.css` equivalents in `Color.kt`. **This is Issue 1 of the critique.**
2. **48dp tap targets** — every clickable Modifier on a chip, icon-button, source-rail item, etc., gets `Modifier.minimumInteractiveComponentSize()` (M3 default) or `Modifier.size(48.dp)` minimum. Verify by grep that no `LocalMinimumInteractiveComponentSize provides false` remains.
3. **Content descriptions** — every `Icon` and `Image` with information value gets a description. Decorative icons get `Modifier.semantics { invisibleToUser() }`.
4. **Card semantics** — wrap each `ArticleCard`/`RepoCard`/etc. in `Modifier.semantics(mergeDescendants = true) { … contentDescription = "Repo {owner}/{repo}, {stars} stars, by {source}, {time}, bookmarked: {state}. Tap to read." }`. Talkback now reads the whole card as one node.
5. **Dynamic type up to 130%** — verify no `maxLines = 1` on data-bearing rows. Cards with 5+ line titles clamp at 4 lines with ellipsis (Issue 5 of critique edge cases).
6. **Reduced motion** — confirm all Wave 5 motion respects the system flag.
7. **Theme override final wiring** — confirm `themeMode` flows from DataStore through `HackertabTheme` and switches without restart.

#### Agent 6N — Tablet adaptive
**Branch**: `feat/v4-w6-tablet`
**Lane**: `shared/.../navigation/MainNavHost.kt` (extending it, not rewriting), `feature/home/.../HomeScreen.kt`, `feature/bookmarks/.../BookmarksScreen.kt`, `feature/settings/.../SettingsMasterScreen.kt`.

**Tasks**:
1. Replace `BottomNavScaffold` with a `WindowSizeClass`-driven `NavRailScaffold` at width ≥ 600 dp.
2. Verify list-detail metadata is set on `HomeScreen`, `BookmarksScreen`, `SettingsMasterScreen`. WebView is the detail pane.
3. Verify auto-select-first-item still works on tablet for Today (already exists in `HomeRoute`) and Bookmarks (already exists in `BookmarksRoute`). For Settings, auto-select Topics on tablet.
4. Tablet `TodayScreen` and `TabletBookmarks` HTML mockups should be matched within reasonable margin.

#### Agent 6O — Critique fixes (from my critique pass)
**Branch**: `feat/v4-w6-critique`
**Lane**: distributed but coordinated through a checklist file `CRITIQUE_FIXES.md`.

**The 15 critique items, with owners**:
- Issue 1 — contrast bump → 6M (already covered)
- Issue 2 — Saved tab icon vs. card bookmark icon → 6O updates `BottomNav` icon to a "stack" or "folder" glyph
- Issue 3 — All view aggregator → 5L (covered)
- Issue 4 — TopicChip inverted state → 2B baked it in
- Issue 5 — SectionHeader count noise → 6O drops the count when items > 0 in non-filtered view
- Issue 6 — fresh indicator weak → 3G/2C baked it in
- Issue 7 — read-state opacity → 3G/2C baked it in (0.78)
- Issue 8 — language tag map alignment → 6O regenerates the language map 1:1 with the original 22 entries
- Issue 9 — ProductHunt thumbnail → 2C/3G baked it in (Kamel)
- Issue 10 — Android backdrop blur fallback → 2A baked it in
- Issue 11 — drop ★ from "All" → 3G baked it in
- Issue 12 — profile grid 3-col validation → 6O reviews live with simulated longest copy
- Issue 13 — i18n placeholder → 7 (cleanup wave, prep only)
- Issue 14 — search icon scope → 3G/3H baked it in
- Issue 15 — coachmark replay → 6O adds "Show tour again" row in Settings → About

So 6O's actual lane is: `BottomNav.kt` (icon swap), `SectionHeader.kt` (drop count rule), `Color.kt` / `Components.kt` (language map regen), `SettingsAboutScreen.kt` (replay tour row).

---

### Wave 7 — Cleanup & ship (sequential, ~3h)

**One agent.**

**Tasks**:
1. **Dead code**:
   - Delete drawer composables / handlers (any leftover after Wave 4).
   - Delete the `mediun` folder, rename to `medium`. Update imports.
   - Delete the `HomeScreenDrawerItem`, `AppVersionName` (moved to Settings → About).
   - Remove `HawkesBlue`, `ChineseBlack`, `Black900`, `Black700`, `Black400` from `Color.kt` (they were the M2 palette).
   - Remove `Flamingo` if unused (audit usages first — it's still referenced by HackerNews/Reddit score dots; if so, rename to `colorContentPositive` or alias to a token).
2. **Strings**:
   - Move all hardcoded strings in onboarding screens (`"Hi, 👋 Welcome to Hackertab"`, `"Validate"`, `"Finish"`, `"You still can change this in settings"`, etc.) into `strings.xml`.
   - Replace double exclamation marks (`!!`) with single ones in all error copy.
   - Move `"No bookmarks yet"` to `strings.xml`.
   - Audit for "you didn't follow any source" → fix to "you haven't followed any source yet."
3. **Lint**:
   - `./gradlew lint` clean.
   - `./gradlew detekt` (if configured).
4. **Tests**:
   - Run `./gradlew :feature:home:testDebugUnitTest` (the one existing test).
   - Add basic snapshot tests for `ArticleCard`, `RepoCard`, `LaunchCard`, `ConferenceCard` if a snapshot infra exists; otherwise just verify previews render.
5. **Version**:
   - Bump `versionName` in `gradle/libs.versions.toml` from `3.0.0` → `4.0.0`.
   - Bump iOS `CFBundleShortVersionString` and `CFBundleVersion`.
   - Update `whatsNewDirectory/` if a release-notes mechanism is in use.
6. **README**:
   - Update Stack section to mention Material 3, Geist fonts, BottomNav, etc.
   - Update screenshots in `imgs/` (deferred to post-merge — needs running app).
7. **Final smoke**:
   - `./gradlew assembleRelease` green.
   - iOS framework links for both arm64 and simulator.
   - Manual run on phone + tablet + iOS simulator.
8. **Merge `feat/v4-redesign` to `main`**.

---

## 5. Dependency map (visual)

```
Wave 0: tokens, theme, fonts, datastore keys
   │
   ▼
Wave 1: M2 → M3 mass migration
   │
   ▼
Wave 2: ─┬─ 2A shell (AppBar/BottomNav/NavRail/SectionHeader/StepIndicator)
         ├─ 2B rail (SourceRail/TopicChipStrip/SourceTag)
         ├─ 2C cards (CardShell/CardActions/Article/Repo/Launch/Conference/Bookmark)
         ├─ 2D states (Empty/Error/Offline/Skeleton/Snackbar)
         └─ 2E controls (Buttons/Input/Sheet/SegmentedControl/Chips)
   │
   ▼
Wave 3: ─┬─ 3F onboarding (4 screens)
         ├─ 3G home (Today/Focused/WebView/LongPress + 12 cards + aggregator stub)
         ├─ 3H bookmarks (3 screens + Room migration)
         └─ 3I settings (5 screens + theme wiring stub)
   │
   ▼
Wave 4: navigation graph rewrite, BottomNav scaffold, theme wire-up, coachmark gate
   │
   ▼
Wave 5: ─┬─ 5J motion polish
         ├─ 5K coachmarks
         └─ 5L aggregator polish
   │
   ▼
Wave 6: ─┬─ 6M a11y sweep
         ├─ 6N tablet adaptive
         └─ 6O critique fixes
   │
   ▼
Wave 7: cleanup, lint, version bump, merge to main
```

---

## 6. Conflict-zone heatmap

| File / area | Wave 0 | Wave 1 | Wave 2 | Wave 3 | Wave 4 | Wave 5 | Wave 6 | Wave 7 |
|-------------|--------|--------|--------|--------|--------|--------|--------|--------|
| `gradle/libs.versions.toml` | ✏️ | — | — | — | — | — | — | ✏️ |
| `core/design/.../theme/*` | ✏️ | ✏️ | r/o | r/o | r/o | r/o | ✏️ (a11y) | ✏️ |
| `core/design/.../components/*` | — | — | ✏️ NEW | r/o | r/o | ✏️ (motion) | ✏️ (a11y) | r/o |
| `core/design/.../strings.xml` | — | — | — | ✏️ append | — | — | — | ✏️ |
| `core/data/.../datastore/*` | ✏️ | — | — | ✏️ append | — | — | — | r/o |
| `core/database/*` | — | r/o | — | ✏️ (3H only) | — | — | — | r/o |
| `feature/onboarding/*` | — | r/o | — | ✏️ (3F only) | — | ✏️ (5K) | r/o | r/o |
| `feature/home/*` | — | ✏️ migrate | — | ✏️ (3G only) | r/o | ✏️ (5J,5L) | r/o | r/o |
| `feature/bookmarks/*` | — | ✏️ migrate | — | ✏️ (3H only) | r/o | r/o | r/o | r/o |
| `feature/settings/*` | — | ✏️ migrate | — | ✏️ (3I only) | r/o | r/o | r/o | r/o |
| `shared/.../navigation/*` | — | ✏️ migrate | — | r/o | ✏️ rewrite | r/o | ✏️ (6N) | r/o |
| `shared/HackertabKmpApp.kt` | — | r/o | — | r/o | ✏️ wire theme | r/o | r/o | r/o |
| `app/src/main/*` | — | r/o | — | r/o | r/o | r/o | r/o | ✏️ version |

Legend: `✏️` = writes, `r/o` = read-only / no writes, `—` = not touched.

**Zero overlap rows in any column** = parallel-safe.

---

## 7. Per-wave acceptance criteria (gate to next wave)

| Wave | Gate |
|------|------|
| 0 | `assembleDebug` green; new tokens resolvable; ThemeMode enum usable from any module |
| 1 | Zero `MaterialTheme.colors`, zero `androidx.compose.material.MaterialTheme` imports; app launches and renders all original screens |
| 2 | `:core:design:build` green; every new component has a working `@Preview` light + dark; no feature/* references to old M2 component patterns |
| 3 | `assembleDebug` green; each feature module builds in isolation; new screens compose under their existing routes (still using old MainNavHost) |
| 4 | Full app loop testable: onboarding → today → switch tab → bookmarks → tap card → webview → back → settings → theme switch (works without app restart) |
| 5 | Motion respects reduced-motion; All view shows at least one source's items within 1.5s on a fast network; coachmarks fire once on first home visit, never again |
| 6 | Talkback reads each card as one merged node; light-mode contrast ≥ 4.5:1 on body text (verified with a contrast checker tool); tablet 3-pane works on 1024+ dp |
| 7 | `assembleRelease` green; iOS archive builds; release notes drafted |

---

## 8. Rollback plan

- **Per-wave**: each wave's branch is short-lived. If a wave fails, revert the merge commit on `feat/v4-redesign` and re-spin the wave.
- **Per-agent**: each parallel branch is independently reverable. If 2C cards regress, revert only 2C without losing 2A/2B/2D/2E.
- **Final**: `feat/v4-redesign` is never merged to `main` until Wave 7 completes. If late-stage issues emerge, the entire redesign sits on the integration branch indefinitely without affecting production. Worst case: kill `feat/v4-redesign` and the production app on `main` is unchanged.

---

## 9. Risks & mitigations

| Risk | Likelihood | Impact | Mitigation |
|------|------------|--------|------------|
| Material 3 components have subtle API differences that break Wave 1 | High | Medium | Wave 1 agent reads M3 migration docs first, references the table in §4 above |
| Compose Multiplatform 1.9.3 lacks one of the M3 components we want | Medium | Medium | Validate each used component during Wave 0; if missing, fall back to custom Compose primitives or M2 stragglers (with rationale) |
| Navigation 3 alpha has bugs around bottom-nav back stacks | Medium | High | Wave 4 agent prototypes BottomNav scaffold first as a 30-min spike; if blocked, fall back to manual `currentTab` state with three sibling rememberSaveable backstacks |
| Pull-to-refresh in M3 on iOS doesn't ship | Low | Medium | Use `androidx.compose.material3.pulltorefresh.PullToRefreshBox` (CMP-supported); fallback to manual `Modifier.scrollable` + threshold detection if it doesn't work on iOS |
| The "All" aggregator hammers 12 endpoints simultaneously | Medium | High (perf) | Wave 5 agent caps concurrency to 6; serializes the slowest 6; respects per-host backoff. Also: cache last successful response per source for 5 minutes. |
| Room migration of `read` column corrupts existing user bookmarks | Low | Critical | Wave 3H writes a deterministic migration: `ALTER TABLE bookmarked_articles ADD COLUMN read INTEGER NOT NULL DEFAULT 0` and adds a unit test verifying old DB → new DB upgrade preserves all rows |
| Geist font license forbids redistribution in app bundles | Low | Medium | Verify SIL OFL license at `https://vercel.com/font` before Wave 0; if fails, fall back to Inter Tight + JetBrains Mono |
| iOS Compose framework size balloons from new fonts + M3 | Low | Low | Acceptable; binary size is not a release-blocker for v4 |
| Coachmark spotlight rendering doesn't work on iOS Compose | Medium | Low | Wave 5K spike: validate `Modifier.drawWithContent` + `BlendMode.Clear` on iOS canvas. If broken, render a simpler bordered rectangle highlight without scrim cutout. |

---

## 10. Suggested agent prompts (copy-paste ready)

Each parallel agent gets a self-contained prompt. Below is the template + a concrete fill-in for one of the most complex agents (3G — Home).

### Template

```
You are implementing <WAVE>.<LANE> of the Hackertab v4 redesign in a Kotlin
Multiplatform + Compose Multiplatform codebase.

Read these first:
- /Users/zouhir/AndroidStudioProjects/hackertab/DESIGN_AGENT_MASTER_PROMPT.md
- /Users/zouhir/AndroidStudioProjects/hackertab/IMPLEMENTATION_PLAN.md (this file)
- /Users/zouhir/AndroidStudioProjects/hackertab/design/project/components/<RELEVANT JSX FILES>
- /Users/zouhir/AndroidStudioProjects/hackertab/design/project/styles/tokens.css

Your branch: <BRANCH NAME>
Your lane (only files you may modify): <LANE FILE LIST>
Off-limits: every file outside the lane.

Use existing tokens from core/design/.../theme/* (Wave 0 already shipped them).
Use existing components from core/design/.../components/* (Wave 2 shipped them).
Do not edit the navigation graph (Wave 4 owns it). Add a TODO comment if you
introduce a new route.

Work plan:
1. <step 1>
2. <step 2>
…

Acceptance:
- ./gradlew :<your module>:build green
- All new screens have @Preview light + dark
- Every new ViewModel is wired into the feature's Koin module
- Every screen calls TrackScreenViewEvent
- Use kotlinx.collections.immutable.PersistentList for list-typed state
- Match the HTML prototype within reasonable margin (typography sizes, spacing, color tokens)

When done: commit, push your branch, summarize what you built in <100 words.
```

### Concrete fill-in: Agent 3G (Home)

```
You are implementing Wave 3 / Lane G — the Home (Today + Focused feed + WebView
+ LongPress) screens — of the Hackertab v4 redesign.

Read these first:
- /Users/zouhir/AndroidStudioProjects/hackertab/DESIGN_AGENT_MASTER_PROMPT.md (esp.
  §"Screen-by-Screen Description" Screens 05–07, §"Existing Features" F2 + F7 + F8,
  §"User Flows" Flows 3, 4, 5, 6, 9, 11, 12).
- /Users/zouhir/AndroidStudioProjects/hackertab/IMPLEMENTATION_PLAN.md §"Wave 3" 3G
  (this file).
- /Users/zouhir/AndroidStudioProjects/hackertab/design/project/components/Screens.jsx
  (find TodayScreen, FocusedFeed, WebViewScreen, LongPressSheet, PullToRefresh).
- /Users/zouhir/AndroidStudioProjects/hackertab/design/project/components/Components.jsx
  (FeedCard, ArticleCard, RepoCard, LaunchCard, ConferenceCard, SourceRail,
  TopicChipStrip, CardShell, CardActions, SourceTag).

Your branch: feat/v4-w3-feat-home
Your lane (only files you may modify):
- feature/home/src/commonMain/kotlin/**/*.kt (entire module)
- feature/home/src/androidMain/kotlin/**/*.kt
- feature/home/src/iosMain/kotlin/**/*.kt
- core/data/src/commonMain/kotlin/com/zrcoding/hackertab/data/repositories/AggregatedArticleRepository.kt (NEW)
- core/data/src/commonMain/kotlin/com/zrcoding/hackertab/data/DataModule.kt (append binding)
- core/domain/src/commonMain/kotlin/com/zrcoding/hackertab/domain/usecases/GetAggregatedFeedUseCase.kt (NEW)
- core/domain/src/commonMain/kotlin/com/zrcoding/hackertab/domain/repositories/AggregatedArticleRepository.kt (NEW interface)
- core/domain/src/commonMain/kotlin/com/zrcoding/hackertab/domain/domainModule.kt (append binding)
- core/data/.../datastore/SettingRepositoryImpl.kt (add KEY_LAST_VISITED_AT read/write)
Off-limits: every file outside the lane. Do not touch shared/.../MainNavHost.kt
(Wave 4 owns it). Do not redefine SourceRail/TopicChipStrip/cards (Wave 2 shipped them).

Work plan:
1. Define AggregatedArticleRepository in core/domain (interface) + impl in core/data
   that fans out to per-source repositories and merges results by published_at desc.
   Stub the implementation: parallel coroutineScope + async + awaitAll, no dedup,
   no per-source loading state yet (Wave 5L polishes). Bind in DataModule.
2. Add GetAggregatedFeedUseCase wrapping the repo.
3. Rewrite HomeViewModel:
   - State machine: ViewState now has activeSourceId: String ("all" or source.id),
     enabledSources: PersistentList<Source>, enabledTopics: PersistentList<Topic>,
     selectedTopic: Topic?, articlesByDay: PersistentMap<DayBucket, PersistentList<BaseArticle>>,
     loading: PerSourceLoadState (Wave 5 polishes), error: String?, canRefresh: Boolean.
   - Init: same observe sources + topics + bookmarks pattern as before.
   - When activeSourceId == "all", call GetAggregatedFeedUseCase.
   - Otherwise call existing per-source repository methods.
   - refresh() function for pull-to-refresh.
   - markRead() function (no-op for now if article isn't bookmarked; Bookmarks
     agent in 3H owns the read column).
4. Rewrite HomeScreen.kt:
   - Replace Scaffold(scaffoldState/drawer) with simple Scaffold(topBar = AppBar,
     bottomBar = TODO Wave 4) — leave a TODO for the BottomNav slot.
   - AppBar: wordmark on left, refresh icon on right (no search icon — see
     critique Issue 14). Pull-to-refresh handles refresh too; the icon is for
     a11y / non-gesture users.
   - SourceRail with "All" pseudo-source first, then enabled sources.
   - TopicChipStrip below SourceRail, only when activeSource.supportsFilters
     (or when activeSourceId == "all" — All always supports topic filtering).
   - Body: PullToRefreshBox wrapping a LazyColumn that emits SectionHeader +
     FeedCards per day bucket. Use the kotlinx.collections.immutable groupings.
   - Loading: LoadingSkeleton (from core/design Wave 2D).
   - Error: ErrorState (from core/design Wave 2D).
   - Empty (no items match): EmptyState with "Clear filter" + "See all sources"
     CTAs.
5. The 12 source-specific cards: each lives in feature/home/.../cards/<source>/<Source>Item.kt.
   They are thin wrappers that pick ArticleCard/RepoCard/LaunchCard/ConferenceCard
   from core/design and supply the source-specific meta slot. Delete the old
   SourceItemTemplate.kt; it's superseded by core/design's CardShell.
   - Note: the existing folder is called "mediun" (typo). Don't fix it now —
     Wave 7 owns the rename.
6. WebView: rewrite WebViewRoute to use the modal-style chrome (back button +
   page-title + URL host on top, bottom action bar with Save/Share/Open-in-browser).
   Use the WebView component from core/design (already exists).
7. LongPressActionSheet: a new file feature/home/.../LongPressActionSheet.kt
   that uses the ActionSheet component from core/design Wave 2E. Wire to a
   modifier on each FeedCard via Modifier.combinedClickable(onLongClick = …).
8. iOS-specific: ShareManager.ios.kt, ContactSupport.iosMain~1.kt — keep as
   they are; they don't need redesign work.

Critique fixes baked in here:
- Issue 6: strengthen "fresh" indicator with a "NEW" pill in SourceTag. (Use the
  Wave 2C component; pass fresh=true).
- Issue 7: read opacity = 0.78 in CardShell. (Wave 2C bakes this; you just pass
  read=article.read).
- Issue 11: drop the ★ from "All" in the source rail — use plain "All" with
  slightly heavier weight. (Wave 2B already implemented this; verify).
- Issue 14: no search icon in feed AppBar. (Just don't pass it.)

Acceptance:
- ./gradlew :feature:home:build green
- ./gradlew assembleDebug green (the integration branch already builds; your
  changes shouldn't break it)
- TodayScreen visually matches design/project/components/Screens.jsx::TodayScreen
- FocusedFeedScreen visually matches FocusedFeed
- WebViewScreen visually matches WebViewScreen with bottom action bar
- LongPress works on a feed card and opens the bottom sheet
- Pull-to-refresh fires AggregatedArticleRepository.refresh()
- All cards show their source-specific meta correctly (verify each of 12 sources
  with the existing repository test fixtures)
- @Previews exist for TodayScreen (light + dark), FocusedFeedScreen (light +
  dark), each card variant
- Use PersistentList for any list state

When done: commit, push, summarize what you built in <100 words.
```

---

## 11. Quick reference — what to ship in this order

1. Wave 0 + 1 (sequential, ~8h)
2. Spawn 5 worktrees for Wave 2 (parallel, ~6h wall-clock)
3. Merge Wave 2; spawn 4 worktrees for Wave 3 (parallel, ~10h wall-clock)
4. Merge Wave 3; Wave 4 sequential (~4h)
5. Spawn 3 worktrees for Wave 5 (parallel, ~6h wall-clock)
6. Merge Wave 5; spawn 3 worktrees for Wave 6 (parallel, ~5h wall-clock)
7. Merge Wave 6; Wave 7 sequential (~3h)
8. Final QA, screenshots, release notes, merge `feat/v4-redesign` to `main`, tag `v4.0.0`.

**Wall-clock estimate (with 4-5 parallel agents per wave): ~32–35 hours over 4–5 work days.**
**Single-agent serial estimate: ~70+ hours.**
