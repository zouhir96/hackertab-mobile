# DESIGN AGENT MASTER PROMPT

> **Audience**: An autonomous AI Design Agent tasked with redesigning the Hackertab Mobile app from scratch.
>
> **Goal**: Produce a complete, modern, cohesive UI/UX redesign — visual direction, screen-by-screen mockups, an end-to-end design system, motion language, and interaction patterns — without ever needing to read the codebase.
>
> **You are the design lead.** Treat everything below as the product brief, the user research summary, the engineering reality, and the brand context, all rolled into one. Make confident, opinionated design decisions. Push the product forward, but respect what already works and what must remain functionally intact.

---

# Product Overview

**Name**: Hackertab Mobile (Unofficial mobile companion to the [hackertab.dev](https://hackertab.dev) browser extension)

**Tagline (working)**: *"All your developer news. One feed. One profile. Zero noise."*

**One-line description**: Hackertab is a cross-platform (Android + iOS) **content-aggregation reader for software developers**. It pulls the latest articles, repositories, products, and conferences from 11 developer-centric sources (GitHub Trending, Hacker News, Dev.to, Reddit, ProductHunt, Lobsters, Hashnode, FreeCodeCamp, IndieHackers, Medium, HackerNoon, plus an "upcoming conferences" feed) and filters them by the user's chosen technology topics (Kotlin, Android, React, Rust, AWS, AI, etc.).

**What the app actually does, end to end**:

1. On first launch, the user picks a *Profile* (e.g. Mobile Engineer, Backend Engineer, ML Engineer).
2. The user then picks the *Topics* they care about — programming languages, frameworks, domains. The category matching their profile is auto-expanded.
3. The user then picks which *Sources* to follow (GitHub, HackerNews, DevTo, …).
4. They land on the **Home feed**: a single-source-at-a-time list, with the active source displayed as a dropdown title in the top bar, and the active topic shown as a horizontally scrollable chip strip below it.
5. Tapping a list item opens a **WebView** of the source URL inside the app.
6. The user can **bookmark** items (saved locally in Room DB) and **share** them via the OS share sheet.
7. A side **drawer** exposes Topics, Sources, Bookmarks, and Contact-us.
8. Settings let the user toggle topics and sources at any time. Saved selections drive the home feed reactively.

**Monetization**: None currently. No paywalls, no ads, no premium tier, no auth. The app is free, anonymous, and stateless beyond the user's own preferences.

**Maturity**: Public on Play Store and App Store, version 3.0.0. Built solo by an indie developer (Zouhir). Codebase is recently migrated to Kotlin Multiplatform + Compose Multiplatform. No major redesign since the original Android-only version.

---

# Target Users

**Primary persona — "Curious Coder Casey"**
- 22–40 years old, software engineer or student of software engineering.
- Specializes in one stack (mobile, frontend, backend, devops, ML, security, data, blockchain, or full-stack).
- Currently relies on a fragmented loop: Twitter/X feed, RSS, HackerNews tab, GitHub Trending, a few Subreddits, dev newsletters.
- Wants a *single tab* on their phone that says "what's new in my world today" — without algorithmic clickbait, without lifestyle content, without LinkedIn noise.
- Reads in short bursts: morning coffee, commute, lunch break, between standups.
- Comfortable with technical UI; not impressed by gimmicks; sensitive to wasted screen real estate, sluggish UX, and "AI slop."

**Secondary persona — "Conference Hunter Carla"**
- Senior engineer or DevRel.
- Uses the app primarily for the *Conferences* source (powered by confs.tech) to plan attendance and speaking submissions.

**Tertiary persona — "Indie Hacker Ivan"**
- Builder, part-time founder, follows ProductHunt + IndieHackers + HackerNews.
- Uses the app as a daily inspiration / market-pulse feed.

**Anti-persona**:
- People wanting a social network (no comments, no profiles, no following users — and that's intentional).
- People wanting AI summaries of articles.
- Casual / non-technical readers.

**Behavioral signals** (inferred from feature set):
- Users have strong opinions about *which* sources they follow. The app stores the user's per-source enable/disable explicitly, and minimum-1-source is enforced — meaning the design must respect that selection power.
- Users return frequently but briefly. Optimize for "scan + tap into webview + back."
- Users are platform-aware: dark mode is mandatory, system theme respect is mandatory, large-screen (tablet/iPad) is supported via list-detail layout.

---

# Core User Problems

The redesign must solve, or at minimum stop blocking, these real user problems:

1. **"I don't want to open six tabs every morning."** Users currently bounce between HackerNews, GitHub Trending, Reddit, DevTo, ProductHunt, etc. Hackertab's *whole reason to exist* is to be the one place that aggregates all of this. The redesign must double down on a single, glanceable feed.

2. **"I don't want to see content that's irrelevant to my stack."** A backend engineer doesn't want SwiftUI tutorials. The Profile + Topics filtering exists for this. The redesign must make filter state ALWAYS legible — the user should know at a glance *which topic and source they're currently looking at*.

3. **"I want to find that article I bookmarked last Tuesday."** The current bookmarks screen is a flat reverse-chronological list with no search, no grouping, and no preview thumbnail. Bookmark discovery is weak.

4. **"I want a more visually interesting feed than just text."** The current feed is dense text-with-icon rows that all look nearly identical regardless of source. ProductHunt is the only card with a visual asset (thumbnail). Users have no way to triage visually.

5. **"Switching sources is hidden behind a dropdown I have to discover."** Today, the only way to change the active source is to tap the title in the top bar to reveal a dropdown menu. There is no swipe-between-sources, no tab strip, no carousel — the source selector is a discoverability problem.

6. **"I never know if loading failed or if there's just nothing new."** Empty states, error states, and "no items match this filter" states currently use the same plain centered text component. They are not distinguishable.

7. **"The onboarding made me click 50 chips at once and then dropped me into a dense feed with no orientation."** Onboarding currently has zero "here's how to use the app" moment after setup completes. The user is teleported straight into Home.

8. **"I can't tell which content is fresh."** Time-ago strings exist but are visually low-contrast. There is no "new since last visit" indicator, no unread state, no day grouping.

9. **"I want to read offline, on the train."** The app is online-only today. The product roadmap explicitly lists "offline-first support" as a future feature. The redesign should anticipate offline-readiness in the visual language even if the data layer doesn't ship it day one.

10. **"I want to share a link in a way that doesn't look spammy."** The current share text auto-appends an app-promotion blurb. Some users may want a "clean share" option.

---

# Existing Features

These are the features that **currently exist in production**. Treat the list as the functional contract — every one of them must be reachable in the redesign unless the brief explicitly says otherwise.

1. **Onboarding (3 steps)**
   - Pick a Profile (one of 9: Mobile, Frontend, Backend, Full-Stack, DevOps, Data, Security, ML, Other).
   - Pick Topics (multi-select chips, grouped by category, at least 1 required).
   - Pick Sources (multi-select chips, all 12 available, at least 1 required).
   - Each step has a "you can change this in settings" reassurance footer (steps 2 and 3).

2. **Home Feed**
   - Single active source at a time, swapped via a dropdown in the top bar.
   - Single active topic at a time, swapped via a horizontal chip strip below the top bar.
   - Topic chip strip is hidden for sources that don't support filtering (HackerNews, Lobsters, IndieHackers, ProductHunt).
   - Pull-to-refresh: NOT IMPLEMENTED — only an in-error retry button.
   - List item types vary by source (12 distinct card layouts).
   - Each list item has a Share and a Bookmark icon-button overlaid bottom-right.
   - Tapping the body of an item opens an in-app WebView at the article URL.
   - On tablets/iPad, a list-detail two-pane layout auto-opens the first article in a side WebView.

3. **Source-Specific Cards** (12 variants)
   - GitHub: `owner/repo` title (in primary blue), description, language dot + stars + forks.
   - HackerNews: title, points (orange dot) + time-ago + comments.
   - HackerNoon: title + time-ago + tag chips.
   - Conferences: title, location (🌐 Online or country/city), date range, tag chips.
   - DevTo: title, time-ago + comments + reactions, tag chips.
   - ProductHunt: 52dp thumbnail image, title, tagline, comments, vertical upvote pill on the right.
   - Reddit: title, time-ago + score + comments, subreddit tag chip.
   - Lobsters: title, score (with up-arrow) + time-ago + comments.
   - Hashnode: title, time-ago + comments + reactions, tag chips.
   - FreeCodeCamp: title + time-ago, tag chips.
   - IndieHackers: title, score (custom blue) + time-ago + comments.
   - Medium: title, claps + comments + time-ago.

4. **Filters**
   - Source dropdown menu in top bar (with "+ Add source" link to settings if user hasn't enabled all 12).
   - Topic chip strip under top bar (with "+ Add topic" if user hasn't enabled all 78 topics in topics.json).

5. **Bookmarks**
   - Reverse-chronological list of saved items (id, title, url, savedAt, source).
   - Each row has the source icon, title (2 lines max), time-ago, and a "remove bookmark" button.
   - Loading and empty states (faded "No bookmarks yet" centered text).
   - Tapping a bookmark opens its WebView.
   - Tablet auto-opens the first bookmark in detail pane.

6. **Settings**
   - **Settings → Topics**: Same expandable category UI as onboarding step 2. Toggle topics individually.
   - **Settings → Sources**: Chip group of all 12 sources. Toggling enabled/disabled. Cannot disable the last source (silent guard).
   - There is **no master Settings screen** — each is reached directly from the drawer.

7. **In-app WebView**
   - JS enabled, zoom enabled, dark-mode-passthrough enabled (Android `isAlgorithmicDarkeningAllowed`).
   - Loading spinner in center.
   - On phones, wrapped with a back-button top app bar; on tablets, no top bar (it lives inside a list-detail pane).

8. **Share**
   - System share sheet with an auto-generated body that includes the article's title, URL, and a footer promoting the app's Play/App Store listing.

9. **Contact Us**
   - Drawer entry → opens system mail composer with prefilled subject, footer with OS version + device model + app version. Falls back to an alert dialog if no mail client is installed.

10. **Theme**
    - Dark/light mode auto-follow system. No in-app override toggle.

11. **Adaptive Layout**
    - List-detail two-pane on width ≥ medium breakpoint (tablets, iPads, foldables unfolded).
    - Auto-selects the first item to display in the detail pane on entering Home or Bookmarks.

12. **Analytics**
    - Firebase Analytics (production) / stub (debug) tracks: profile selected, topics selected, sources selected, setup completed, source filter changed, topic filter changed, source/topic selection changed, every screen view.

13. **Internationalization**
    - English-only. A strings.xml exists, but most screens hard-code copy directly in Kotlin.

14. **Bookmarks persistence**
    - Local Room database. Survives reinstall? No — Room is app-local. No cloud sync. No account.

15. **What does NOT exist** (very important — do not invent these unless explicitly asked):
    - No accounts, no login, no auth.
    - No comments, no upvoting, no any social interaction.
    - No push notifications.
    - No deeplinks.
    - No search bar anywhere in the app.
    - No history (besides bookmarks).
    - No reading-progress tracking.
    - No "open in browser" alternative — it's always the in-app WebView.
    - No swipe gestures (no swipe-to-bookmark, no swipe-to-share, no swipe-between-sources).
    - No haptic feedback patterns.
    - No light "today's brief" or summary view.

---

# Complete Feature Breakdown

This section is the deep functional specification. Use it as the single source of truth for what each feature *does*.

## F1 — Onboarding

**Step 1: Choose Profile**
- Title: "Hi, 👋 Welcome to Hackertab"
- Subtitle: "Let's customize your Hackertab experience!"
- Body label: "Let's get to know you, please choose your profile"
- A 2-column grid of 9 cards: Mobile Engineer, Frontend Engineer, Backend Engineer, Full Stack Engineer, Devops Engineer, Data Engineer, Security Engineer, ML Engineer, "...Other".
- Each card is text-only with the role label split across two lines.
- The selected card gains a 2dp primary-color border and a tiny elevation. The unselected cards have a 1dp 10%-opacity primary border.
- Bottom: full-width primary "Next" button (disabled until selection).
- The chosen profile is persisted and used to pre-expand the matching topic category on Step 2.

**Step 2: Choose Topics**
- Title: "Languages & topics"
- Subtitle: "Select the languages & topics you're interested in following."
- A bordered scrollable container with categories (Frontend, Backend, Mobile, AI, Data, DevOps, Blockchain, Security, Other). Each category is a tappable header with a drop-down arrow that reveals/hides a chip group of topics.
- The category matching the user's profile is auto-expanded; others are collapsed.
- Multi-select. At least one chip must be selected for the Validate button to enable.
- Reassurance footer with info icon: "You still can change this in settings".
- Bottom: full-width "Validate" button.

**Step 3: Choose Sources**
- Title: "Sources"
- Subtitle: "Your feed will be tailored by your followed sources"
- A single bordered container with all 12 sources as chips (with their source icons inside the chip).
- Multi-select. At least one source must be selected.
- Same reassurance footer.
- Bottom: full-width "Finish" button.
- On finish: navigate to Home and log a `setup_completed` analytics event.

**Onboarding gating**:
- The home navigation back-stack is computed from saved state. If a user already chose a profile but never finished topics, they re-enter at the Topics step. If they finished topics but not sources, they re-enter at Sources. If everything is saved, they go straight to Home.

## F2 — Home Feed

**Top App Bar**
- Hamburger icon left → opens drawer.
- Title: source icon + source label + dropdown arrow. The whole thing is one tap target.
- Tapping the title opens a Material 2 `DropdownMenu` listing every enabled source. Each menu item shows the source icon (40dp white circle) + the source label.
- If `enabledSources.size < 12` a final menu item "Add source" with an Add icon appears, navigating to Settings → Sources.
- No actions on the right side of the top bar.

**Topic Filter Strip**
- A horizontal `LazyRow` of `FilterChip`s showing each enabled topic.
- The selected chip flips to inverted colors (background = onBackground, text = background).
- Unselected chips use the secondary color.
- If the active source's `supportsFilters` is false, the entire strip is hidden.
- If `enabledTopics.size < 78` a final round "+" button appears, navigating to Settings → Topics.

**Feed List**
- `LazyColumn` with 12dp vertical spacing and a Material `Divider` between each item.
- 40dp bottom content padding.
- Items keyed by id for stable recomposition.
- Item composable is selected by source via a giant `when` switch (`BaseArticle.ToListItem`).

**Per-card layout** (most cards):
- `SourceItemTemplate` shared composable — `Box` with a `Column` for content and a bottom-right `Row` of share + bookmark circular icon buttons (40dp diameter, 50% secondary tint background).
- Title: `subtitle1` (Nunito Medium 18sp), max 2 lines.
- Description (optional): `body2` (Nunito Medium 14sp), 70% onBackground alpha.
- Primary info row: a `FlowRow` of `TextWithStartIcon` widgets — small grey 16dp icon + small grey caption text (12sp).
- Optional tag row: `FlowRow` of `TextWithStartIcon` using `ic_ellipse` (8dp colored dot) + tag name. Tag colors are mapped from a hard-coded language→color map.

**ProductHunt** is the one structurally different card — a `Row` with 52dp thumbnail, content column, and a bordered upvote column on the right.

**Loading / error / empty states**:
- Loading: a single centered `CircularProgressIndicator`.
- Empty (sources empty): `ErrorMsgWithBtn` with text "You didn't follow any source, you can follow your favorite sources in settings !!" and a button to Settings → Sources.
- Network error: `ErrorMsgWithBtn` with "Something went wrong, please verify your internet connection and try again" and a Retry button.
- No matches: `ErrorMsgWithBtn` with "No items found, try adjusting your filter or choosing a different tag." (no button).

**Hidden behaviors**:
- Auto-selects first article into detail pane on tablet width.
- Re-selecting a source already-selected is a no-op (silent).
- Refresh trigger only fires from the in-error Retry button — there is **no pull-to-refresh**.

## F3 — Drawer

- Header: "Hackertab" wordmark in `h5`.
- Divider.
- 4 large rectangular cards stacked vertically: Topics, Sources, Bookmarks, Contact us. Each card is a Material `Card` with a right-arrow icon, secondary background, 0.4dp elevation.
- Bottom: centered text "with love ❤️ by Zouhir \n Version(3.0.0)".
- Drawer shape uses a 8dp top-end and bottom-end corner (rounded outer edge).

## F4 — Bookmarks

- No top bar of its own (handled by `ScreenWithBackButton` in nav).
- States:
  - Loading → centered spinner.
  - Empty → centered "No bookmarks yet" h6 text at 60% alpha. **Hardcoded copy, not localized.**
  - List → `LazyColumn` of `BookmarkItem` rows.
- `BookmarkItem`: 40dp source icon (circular, white background) + title (subtitle1, 2-line) + time-ago row + circular "remove bookmark" icon button on the right.
- Time-ago format: "Just now", "5m ago", "2h ago", "3d ago", "1w ago", "2mo ago", "1y ago".
- Tapping a row opens the URL in WebView.
- Tablet auto-opens first bookmark in detail pane.

## F5 — Settings: Topics

- Title bar from `SettingScreen` (h5 + body2 description).
- Body: a `LazyColumn` of category groups. Each category header is tappable; tapping toggles expansion (only one expanded at a time on this screen). Auto-expands the first category on first composition.
- Inside each expanded category: a `ChipGroup` (FlowRow) of all topics in that category. Selected chips have primary background + white text.
- Tapping a chip toggles it. Unlike Sources, **no minimum** — the user can technically deselect all topics (this leaves the home feed empty and shows the empty state).
- Saved instantly on tap (no batch save, no commit/cancel).

## F6 — Settings: Sources

- Same `SettingScreen` shell (title + description).
- Body: a `ChipGroup` of all 12 sources, each chip showing a circular source icon and the label.
- Tapping toggles. **Minimum-1-source** is enforced: if you try to deselect the last source, the tap is silently ignored. There is no toast, snackbar, or visual hint explaining why.
- Saved instantly.

## F7 — WebView

- Renders the article URL inside the app via `compose-webview-multiplatform`.
- Loading spinner overlay while loading.
- On phone: wrapped in a back-button bar.
- On tablet: lives in the detail pane of a list-detail layout; no extra chrome.
- Web settings: JavaScript on, zoom on, wide viewport, DOM storage, file access. Dark mode auto via `isAlgorithmicDarkeningAllowed`.

## F8 — Share

- Triggered by the share icon on any feed card (not on bookmarks).
- Opens the system share sheet with text: `"<title>\n<url>\n\nDownload Hackertab: <play store + app store links>"` (template).

## F9 — Contact Us

- Drawer entry → opens email client with `mailto:rajdaouizouhir.pro@gmail.com`.
- Subject: "Suggestions for Hackertab team".
- Body footer: `-Os version: <ver>\n-Device Model: <model>\n-Application Version: <ver>` plus a "Please do not delete this information…" message.
- If no mail client is installed, an alert dialog tells the user.

---

# User Flows

## Flow 1 — First-Run Onboarding (cold start, fresh install)
1. Launcher → splash screen (`ic_splash_screen` Hackertab logo).
2. App boots, asynchronously checks for saved profile/topics/sources.
3. No profile found → Setup Profile screen.
4. User taps a profile card → "Next" enables → user taps Next.
5. Profile saved → Setup Topics screen, with the user's profile category pre-expanded.
6. User selects ≥1 topic → "Validate" enables → user taps Validate.
7. Topics saved → Setup Sources screen, with all 12 chips deselected by default.
8. User selects ≥1 source → "Finish" enables → user taps Finish.
9. Sources saved + analytics event → Home, the first source/topic auto-selected, articles loading.

## Flow 2 — Returning User (warm start)
1. Launcher → splash.
2. App boots, sees saved profile + topics + sources → straight to Home.
3. Home auto-selects the previously selected source if still enabled, otherwise the first source.
4. Home auto-selects the previously selected topic if still enabled, otherwise the first topic.
5. Articles load.

## Flow 3 — Reading an Article
1. From Home: scroll list → tap card body.
2. Phone: WebView opens full-screen with back button.
3. Tablet: WebView replaces the detail pane on the right of the list.
4. Read → tap back (or system back gesture) → return to Home with same source/topic.

## Flow 4 — Switching Source
1. Tap source label in top bar → dropdown menu opens.
2. Tap a source → dropdown closes, source becomes active, list re-fetches, topic strip may appear/disappear.
3. There is no animation between source switches; the list simply reloads.

## Flow 5 — Switching Topic
1. Tap a chip in the topic strip → chip becomes selected, list re-fetches.

## Flow 6 — Bookmarking
1. On any feed card, tap the bookmark icon.
2. Icon flips from outline to filled.
3. The item is now persisted in Room. The icon state is reactive (driven by a Flow of bookmarked IDs).
4. Tapping again removes the bookmark.

## Flow 7 — Reviewing Bookmarks
1. Drawer → Bookmarks.
2. Reverse-chronological list of saved items.
3. Tap a row → WebView opens.
4. Tap the circular bookmark-remove icon → item disappears.

## Flow 8 — Adding a Source mid-use
1. Top bar dropdown → "Add source" (only present if some sources are disabled).
2. Navigates to Settings → Sources.
3. User taps chip(s) → toggled on instantly.
4. User taps system back → returns to Home, dropdown can now show the new source.

## Flow 9 — Sharing
1. Tap share icon on a card → system share sheet appears.
2. User picks a target → standard OS share completes.

## Flow 10 — Contacting Support
1. Drawer → Contact us.
2. System mail composer opens with prefilled subject and footer.
3. User types body, sends.
4. Or if no mail client → alert dialog "No email client" / "No messaging application is configured" / OK.

## Flow 11 — Error Recovery (network)
1. Home tries to fetch → fails (timeout or no internet).
2. List replaced by `ErrorMsgWithBtn`: "Something went wrong…" + Retry button.
3. Tap Retry → re-emit refreshTrigger → re-fetch.

## Flow 12 — No Sources Edge Case
1. User opens Settings → Sources, but the silent minimum-1 guard means this can only happen if state is corrupted. If somehow all sources end up disabled, Home shows the "you didn't follow any source" empty state with an "Open Settings" button.

---

# Screen-by-Screen Description

> Use these descriptions as the canvas for each redesign. Layout dimensions, copy, and components are described in detail.

## Screen 01 — **Splash**
- Black/light backdrop (system theme). Centered `ic_hackertab` vector logo.
- Provided by Android `androidx.core.splashscreen` and equivalent on iOS.
- Duration: ~250ms while the app initializes Koin and computes `GetStartDestinationUseCase`.

## Screen 02 — **Setup Profile**
- Status bar safe-area top, 20dp horizontal padding throughout.
- Stacked vertically:
  - 40dp top spacer.
  - "Hi, 👋 Welcome to Hackertab" — `h5` (Nunito Regular 24sp W900).
  - 4dp spacer.
  - "Let's customize your Hackertab experience!" — `body2`.
  - 20dp spacer.
  - "Let's get to know you, please choose your profile" — `body1`.
  - 8dp spacer.
  - 2-column grid (8dp gaps) of profile cards. Each card is `background`-colored, with 1dp 10% primary border (idle) or 2dp full primary border + 1dp elevation (selected). Card content is centered 2-line text (`body1`).
  - Push: `Spacer(weight=1f)`.
  - Full-width primary "Next" button with arrow-right trailing icon.
- Back stack starts here on first run; back press exits the app.

## Screen 03 — **Setup Topics**
- Same vertical scaffolding (top spacer, h5 title "Languages & topics", subtitle).
- A bordered (1dp onBackground 30% alpha, 8dp radius) Box containing a vertical Column.
- Each category has a header Row (label + drop-arrow) and an `AnimatedVisibility` reveal of a `ChipGroup`.
- Categories are: Frontend, Backend, Mobile, AI, Data, DevOps, Blockchain, Security, Other (Other is forced to be the last entry by an explicit map-reorder).
- Bottom row: info icon + "You still can change this in settings".
- Bottom: full-width "Validate" button.

## Screen 04 — **Setup Sources**
- Same scaffolding. Title "Sources", subtitle "Your feed will be tailored by your followed sources".
- A single bordered Box containing one `ChipGroup` of 12 source chips. Each chip's leading element is the source's circular white-background icon (24dp).
- Same info footer.
- Full-width "Finish" button.

## Screen 05 — **Home**
- Status bar safe area applied at root. No bottom-nav.
- Top app bar (56dp height, no elevation):
  - Left: 40dp circular hamburger icon-button on a 50% secondary bg.
  - Center: "{source-icon-24dp} {source label} {drop-arrow}". Whole thing is a Row marked as `Role.DropdownList`.
  - No right action.
- 8dp spacer below top bar.
- Topic chip strip (only when source supports filters and topics > 0): horizontal `LazyRow`, 16dp horizontal content padding, 8dp inter-chip spacing.
- Body switch:
  - Loading → centered spinner.
  - Articles → `LazyColumn`.
  - Error → `ErrorMsgWithBtn`.
  - No sources → `ErrorMsgWithBtn` with "Open Settings".
- Drawer overlay (modal): see Drawer screen below.

## Screen 06 — **Drawer**
- Half-width modal drawer with rounded right edges (8dp).
- Header: "Hackertab" wordmark `h5`, then `Divider`.
- 4 stacked entry cards: Topics, Sources, Bookmarks, Contact us. Each card is full-width, 8dp radius, secondary bg, 0.4dp elevation, with right-arrow icon.
- Bottom: "with love ❤️ by Zouhir \n Version(3.0.0)" — `body1`, centered.

## Screen 07 — **WebView**
- Phone: top app bar wrapper with circular back button, then WebView fills the rest.
- Tablet: WebView is the right pane in a list-detail layout. No top bar.
- Loading spinner centered.

## Screen 08 — **Bookmarks**
- Top bar (provided by ScreenWithBackButton wrapper): just a back arrow on a 50%-secondary circle, no title.
- States:
  - Loading: centered spinner.
  - Empty: centered "No bookmarks yet" `h6` 60% alpha.
  - List: `LazyColumn` of `BookmarkItem` rows separated by `Divider`s.
- `BookmarkItem`: Row with 40dp circular source icon + Column(title 2-line subtitle1, time-ago row caption) + 40dp circular "remove bookmark" icon button.

## Screen 09 — **Settings: Topics**
- `SettingScreen` shell: title "Topics" `h5`, description body2 "Your feed will be tailored…".
- Body: same expandable category list as onboarding step 2, but only one category expanded at a time, no "Validate" button (saves on tap).

## Screen 10 — **Settings: Sources**
- `SettingScreen` shell: title "Sources", description.
- Body: a single `ChipGroup` of 12 source chips. Tap toggles. Last-source guard.

## Screen 11 — **Detail Placeholder (tablet only)**
- When the list-detail layout has nothing in the detail pane: a centered `h6` "Select an article to read" or "Select a bookmark to read" at 60% alpha.

## Screen 12 — **Error Dialog (no email client)**
- `AlertDialog` (Android) / `UIAlertController` (iOS).
- Title: "No email client".
- Body: "No messaging application is configured".
- Single OK button.

---

# Current UX Problems

Listed by impact. The redesign agent should treat solving these as table stakes.

## P1 (Critical) — One source at a time, hidden behind a dropdown
The current model forces the user to view exactly one source at a time, and the only way to switch is to discover a tappable title in the top bar. There is no swipe gesture, no tab strip, no segmented control. Users who follow 6 sources have to make 6 dropdown taps to see what's new across them.

## P2 (Critical) — No global "what's new" view
There's no aggregated cross-source feed, no "Today's brief," no "Top 10 of the day." The product positioning is "your one place," but the product execution is "your one place to manually visit one source at a time." This is the single biggest UX gap.

## P3 (Critical) — No pull-to-refresh
The only refresh path is to wait for a network error and then tap Retry. There is no manual refresh from a healthy state. Users assume content auto-refreshes; it doesn't.

## P4 (High) — Onboarding drops the user with no orientation
After clicking Finish on Setup Sources, the user lands on Home with zero coachmarks, zero "swipe here," zero "tap here to change source." Discoverability of the dropdown source switcher is poor for a first-time user.

## P5 (High) — Visual monotony of the feed
Every card except ProductHunt is essentially a title + a row of grey icons + a row of tag dots. Sources should *feel* different — a Conference is a different kind of object from a GitHub repo from a Reddit thread from a ProductHunt launch. Today they all read as "grey text in a grey card."

## P6 (High) — Minimum-1-source guard is silent
If you tap the last source chip in Settings → Sources, nothing happens. No tooltip, no toast, no visual feedback. The user thinks the app is broken.

## P7 (High) — Bookmarks have no organization
A flat reverse-chronological list. No search, no folders, no tags, no "by source" grouping, no recency indicator beyond a small caption, no preview. Power users with hundreds of bookmarks will hit a wall.

## P8 (High) — No "new since last visit" affordance
Time-ago is shown, but there's no badge, dot, or row treatment that signals "this is new for you." Users have no way to triage their feed beyond "I think I haven't seen this one before."

## P9 (Medium) — Information density is uneven
Title can be 2 lines, then 8dp spacer, then optional description, then 8dp spacer, then a FlowRow that may or may not exist, then optional tags. Cards visually breathe inconsistently — a tags-heavy DevTo card stands taller than a Lobsters card. The list rhythm is broken.

## P10 (Medium) — Drawer is a 2010 pattern for a 2025 app
A modal drawer for navigation is dated. Bottom navigation, segmented controls, or a sticky tab bar would be more discoverable on mobile.

## P11 (Medium) — Settings has no master screen
The drawer points directly at "Topics" and "Sources" individually. There is no overview screen showing "you follow X sources, Y topics, your profile is Z, here's how to change your theme." Settings feels fragmented.

## P12 (Medium) — Theme can't be overridden in-app
"Auto-follow system" is a sensible default, but power users want a Light/Dark/System trichotomy in settings. Today, this is impossible without changing OS settings.

## P13 (Medium) — Topic chip strip can't be reordered
The order is whatever order the topics happen to be in `topics.json`. The user has no way to surface their most-used topics first.

## P14 (Low) — Tag colors are based on a 22-language hard-coded map
A topic that isn't in the map renders as `Color.DarkGray` regardless of theme. Many tags fall through to the default and look identical.

## P15 (Low) — Time-ago is a string, not a relative timestamp tooltip
Users who want to know an exact published time can't get it.

## P16 (Low) — Share text always includes a "Download Hackertab…" promo footer
Some users will perceive this as spammy.

## P17 (Low) — Conference dates use English month names hard-coded in code
`Oct 10 - 12` style. No locale awareness.

## P18 (Low) — Several typo / copy issues
- Module folder named `mediun` instead of `medium`.
- "iOSMain~1.kt" filename oddity for iOS contact support.
- Double exclamation marks in error copy.
- "you can follow your favorite sources in settings !!" — hardcoded, not localized.

---

# Current UI Problems

## V1 (Critical) — Built on Material 2 (legacy `androidx.compose.material`)
Material 2 uses `Colors`, `Typography`, `Shapes`, `MaterialTheme.colors.background`, `MaterialTheme.colors.onBackground`, etc. Material 3 (`androidx.compose.material3`) — with dynamic color, color schemes, expressive typography, and updated components — is not used. The whole visual language is one major version behind.

## V2 (Critical) — A single primary color (Blue #0366D6) for both themes
The same blue is used in light and dark mode. There is no expressive accent system, no semantic role separation (success / warning / info), no surface tonal palette. The app reads as monochrome blue.

## V3 (Critical) — Background blends with surface
Light theme: background = `HawkesBlue #EFF6FE` (a very pale blue). Surface = default Material light. Cards barely distinguish from background. No depth.

## V4 (High) — Nunito everywhere
The app uses one font (Nunito) with three weights (regular, medium, bold). It's pleasant but generic. Code-context content (repos, code snippets, language names) would benefit from a monospace pair.

## V5 (High) — Icon system is mixed
Some icons are vector XML drawables (`ic_github.xml`, `ic_reddit.xml`), some are Material icons via `Icons.Default.Bookmark`, some are emoji (🌐 for Online conferences). There's no harmonized icon library.

## V6 (High) — Border-with-low-opacity-onBackground is the default container style
Onboarding Topics, Onboarding Sources, and most "boxes" use `BorderStroke(1.dp, onBackground.copy(0.3f))`. This works but feels like a Bootstrap form. No tonal surface, no shadow, no glassmorphism, no card layering.

## V7 (High) — Floating bottom-right share/bookmark cluster on every card is visually heavy
Two 40dp circular buttons in the bottom-right of every card, on a 50%-secondary circular background, take up a lot of card real estate and overlap with text on narrow widths.

## V8 (Medium) — Spacing tokens are well-defined but inconsistently applied
`Dimens` defines `none, tiny(2), small(4), medium(8), large(12), default(16), big(20), bigger(24), extraBig(40), screenPaddingHorizontal(20)`. But screens mix vertical paddings with `medium`, `large`, `default` interchangeably, leading to visually inconsistent rhythm.

## V9 (Medium) — Typography uses Material 2 names (h4, h5, h6, subtitle1, body1, etc.)
Material 3 retired this nomenclature in favor of `displayLarge`, `headlineMedium`, `titleLarge`, etc. The whole type ramp will need a rename + restructure if migrating to M3.

## V10 (Medium) — Source chips inside chip-groups have no visual lock between icon size and chip height
24dp icon on a chip whose height is determined by 8dp vertical padding + 14sp text — visually inconsistent.

## V11 (Medium) — Filter chip selection style is "invert background and content"
Selected topic chips become `onBackground` (very dark in light, very light in dark) with `background` text. This is harsh and high-contrast; the selected state looks like a different component.

## V12 (Low) — No skeleton loaders
All loading is a single centered spinner. No shimmer placeholders, no progressive list reveal.

## V13 (Low) — No motion design
Beyond `AnimatedVisibility` on the topic-category expand, there are no transitions. Source switches are instant; bookmark toggle is instant; navigation is whatever Navigation 3 default is.

## V14 (Low) — Status bar treatment
The app uses `statusBarsPadding()` at the root scaffold but doesn't theme the status bar background. On iOS the status bar will inherit the system; on Android it may flash unexpectedly during cold start.

---

# Technical Constraints

> The Design Agent must produce designs that engineering can ship without rebuilding the world. Below are the real constraints.

## T1 — Platform: Kotlin Multiplatform Mobile (KMM) + Compose Multiplatform 1.9.3
- Single Kotlin codebase for Android and iOS.
- UI is Compose Multiplatform — *not* SwiftUI on iOS, *not* XML on Android. Compose draws everything on a Skia canvas on iOS.
- This means: animations, gestures, modifiers, and components all use Compose APIs. SwiftUI patterns (e.g., navigation pushes with edge-swipe) need explicit Compose equivalents.

## T2 — UI Framework: Material 2 (compose.material), not Material 3
- All current components are M2. Migrating to M3 is possible but is non-trivial: every `MaterialTheme.colors.*` becomes `MaterialTheme.colorScheme.*`, every `Typography` token renames, every `Card`/`Button`/`Chip` API shifts.
- The redesign is the right moment to migrate, but the agent should specify token-name-level mappings to make the engineering migration mechanical.

## T3 — Navigation: Jetpack Navigation 3 (alpha)
- The library is `androidx.navigation3` — a *very new* alpha. Navigation 3 uses an explicit `NavBackStack` of typed `NavKey` objects and an `entryProvider { }` builder. List-detail adaptive layout is via `ListDetailSceneStrategy`.
- The library does NOT yet have a stable bottom-nav helper, tab navigator, or animated transitions API at parity with Compose Navigation 2.
- Bottom nav, tab navigators, swipe-back gestures must be built from primitives.

## T4 — State Management: ViewModel + StateFlow + Koin DI
- Every screen has a `ViewModel` injected via Koin (`koinViewModel()`).
- State flows are immutable `PersistentList`-backed snapshots.
- The redesign can introduce richer states (e.g., a per-card swipe state) but must not break the unidirectional data flow.

## T5 — Data: REST + Room (local) + DataStore (preferences)
- Articles fetched on demand from `https://api.hackertab.dev/engine/`. No pagination — each call returns a single page worth.
- Bookmarks live in a local Room DB.
- User preferences (selected sources, topics, profile) live in DataStore (Preferences).
- There is no caching layer for articles — every source switch is a fresh network call. Designs should communicate freshness explicitly.

## T6 — Image loading: Kamel
- Multiplatform image loader. Supports async, animated, and bitmap decoders.
- ProductHunt thumbnails use it. Other cards do not load any images today.

## T7 — WebView: `compose-webview-multiplatform`
- Works on both Android (`WebView`) and iOS (`WKWebView`).
- Configurable: JS, zoom, dark-mode, file access.
- The library is third-party and version-locked to 2.0.3.

## T8 — Theming: Custom `HackertabTheme` wrapping `MaterialTheme`
- Defines its own `Colors`, `Typography`, `Shapes` plus a `Dimens` extension on `MaterialTheme`.
- Dark-mode follows `isSystemInDarkTheme()`. There's no in-app override.
- A redesign that introduces a Light/Dark/System trichotomy must add a setting + persist it in DataStore + plumb a `darkTheme` boolean from the root.

## T9 — Dependency injection: Koin 4.1
- All ViewModels, repositories, and platform services (ShareManager, ContactSupport) are Koin-bound.
- Adding new services / view models is `viewModelOf(::YourVM)` in a module.

## T10 — Analytics: Firebase Analytics (gitlive multiplatform binding)
- Stub in debug builds. Every meaningful UX action is logged.
- New screens should add a `TrackScreenViewEvent` composable.
- New events should add an entry to `AnalyticsEvent.Types`.

## T11 — Localization: nominal, English-only
- A `strings.xml` exists and is generated to `Res.string.*` via Compose Resources.
- Many strings (especially in onboarding) are hard-coded inline. Adding i18n is a side-quest.

## T12 — Min SDK: Android API 23 / iOS likely 14+
- This caps some platform-specific APIs (e.g., haptics, blur effects, system splash on Android < 12 falls back).

## T13 — Adaptive layout: list-detail strategy already wired
- The library `compose.material3.adaptive.navigation3` provides `ListDetailSceneStrategy`.
- Today, only Home and Bookmarks declare a `listPane`/`detailPane` metadata. Any new screen the redesign introduces would need similar metadata to participate in tablet layouts.

## T14 — No backend control
- Hackertab Mobile does not own its API server. The endpoint at `api.hackertab.dev/engine/` is the same backend powering the browser extension.
- The redesign cannot demand new endpoints; if the design needs richer data (e.g., per-article images, summaries, "new since" markers), that will require server-side work that may or may not happen.

## T15 — No auth, no accounts, no cross-device sync
- Bookmarks and preferences are device-local.
- Designs that imply "sync across devices" or "follow users" cannot be implemented without an account system that does not exist.

## T16 — Testing infra is minimal
- Only one test directory found (`feature/home/.../commonTest/`). The redesign should not assume strong test coverage exists to safeguard refactors.

## T17 — Compose previews
- Many components have `@Preview` annotations. Redesigned components should keep these for the engineering team's productivity.

## T18 — App distribution
- Already on Play Store and App Store. Version 3.0.0. The redesign will ship as a major version (likely 4.0.0). Do not redesign App Store/Play Store assets here; just the in-app surface.

---

# Product Strengths

What's already great about Hackertab and **must be preserved or amplified**, not lost in the redesign:

## S1 — A clean, opinionated single-purpose product
There is no feature creep, no social network, no "for you" tab, no AI clutter. The product does one thing — aggregate dev news — and the redesign should preserve this confident minimalism.

## S2 — Solid information architecture for filtering
"Profile → Topics → Sources" is a sensible ladder of specificity. The mental model maps cleanly: who am I, what do I care about, where do I read about it. Don't break this.

## S3 — Strong technical foundation
KMP + Compose Multiplatform + Koin + Navigation 3 + Room + DataStore. The redesign can stand on this without re-platforming.

## S4 — Reactive state propagation
A user toggling a source in Settings instantly updates the source dropdown on Home. This is invisible UX magic. It must keep working after the redesign.

## S5 — Anonymous by default
No login, no permissions other than internet. This is privacy-respecting and reduces friction. Preserve the "open and use" experience.

## S6 — Adaptive list-detail for tablets
Already implemented. Auto-selecting the first item into the detail pane on tablet width is a small but high-end touch. Keep and improve it.

## S7 — Per-source card variants
The app does try to express each source uniquely, even if the visual differentiation is weak. Lean into this — make each source's card a richer expression of that source's identity (GitHub gets language colors, ProductHunt gets visuals, Conferences gets dates and locations, Reddit gets subreddits, etc.).

## S8 — Minimum-1-source / minimum-1-topic invariants
The product enforces "you must follow at least one." This is a principled choice that prevents the empty-empty state. Preserve it, but communicate it explicitly in the redesigned settings.

## S9 — Adaptive dark mode that respects system
Auto-follow is the right default. Add an override but keep auto as the default.

## S10 — Source-specific iconography
Every source has a recognizable, branded icon. The redesign should keep these icons' identity and use them as anchors in the new card system.

---

# Redesign Objectives

These are the explicit goals of this redesign. The design agent must satisfy all of them.

## O1 — Make "what's new across all my sources" the *primary* surface
Right now, the user has to manually rotate through 6 sources to know what's new. The redesigned home should default to a unified, multi-source feed (or a "Today" view) that surfaces the freshest item from each source at the top of the day.

## O2 — Replace the dropdown source switcher with an obvious, persistent navigator
A tab strip, segmented control, swipeable horizontal pager, or bottom navigation — any of which would be more discoverable than a dropdown title.

## O3 — Visually differentiate sources at a card level
Each source's card should feel like a member of the same family (shared rhythm, shared corner radius, shared interaction patterns) but visually expressive of its source identity (color accent, icon treatment, signature data points).

## O4 — Modernize to a Material 3-equivalent or custom expressive design system
Tokenized color, surface tonal layers, expressive typography (display vs. headline vs. title vs. body), motion-as-language. Avoid pure-Material-3 if it feels too generic; consider a custom skin grounded in M3 primitives.

## O5 — Add a real Settings master screen
A single Settings entry point that exposes Profile, Topics, Sources, Theme, About, Contact — not 4 disconnected drawer entries.

## O6 — Add a Theme override (Light / Dark / System)
Because users want it.

## O7 — Improve Bookmarks
Search bar, group-by-source toggle, group-by-time toggle, swipe-to-remove. Optionally, "read/unread" markers.

## O8 — Add pull-to-refresh and a manual refresh affordance
Even if the data layer is unchanged.

## O9 — Add an onboarding "you're all set" moment
A 1-screen post-setup orientation: "Your home feed is ready. Tap here to switch sources, swipe here to switch topics, tap a card to read." Or contextual coachmarks.

## O10 — Communicate freshness and recency
"New since you last opened the app" badges. Day-grouped feed sections ("Today," "Yesterday," "This week"). Subtle pulse animation on freshly-loaded items.

## O11 — Make share and bookmark less visually heavy
Move them off-card or into a long-press context menu, OR keep them on-card but with smaller, ghosted treatment.

## O12 — Introduce a real empty-state, error-state, and loading-state design system
Three different emotional registers. Skeleton loaders for the first launch, illustrated empty state for the bookmarks-zero case, calm error illustration for network failures.

## O13 — Preserve every existing piece of functionality
Profile, Topics, Sources, 12 source feeds, in-app WebView, share, bookmark, contact, theme, adaptive tablet layout. Nothing should regress.

## O14 — Stay engineering-shippable on Compose Multiplatform
No designs that require native-only iOS gestures (e.g., interactive pop gesture from edge), no designs that require native blur effects unavailable in Compose, no designs that require platform-specific haptics unavailable cross-platform.

---

# Desired Visual Direction

The redesign should feel **modern, technical, and quietly premium** — like a well-built developer tool, not a content-marketing app. Reference points to keep in mind:

- The clarity of **Linear** (calm, system fonts, generous spacing, subtle elevation).
- The information density of **Reeder** (NetNewsWire-style RSS reader: every pixel earns its keep).
- The personality of **Raycast** or **Arc Search** (small, confident animations; sense of intent).
- The branding minimalism of **Vercel** or **Stripe** dashboards (one strong accent, lots of neutral surface).
- The card variety of **Apollo for Reddit** (each source feels like itself).

Visual principles:

- **One canvas, many objects.** A list of cards with consistent rhythm, varying internal expression.
- **Restraint over decoration.** Borders only when they add information. Shadows only when they communicate elevation.
- **Color as semantics.** A primary brand accent (this is where you choose — keep blue or pivot to a warmer/sharper hue). Use language colors meaningfully (GitHub repos: language dot is a real signal). Use semantic colors only for status (error red, fresh green, warning amber).
- **Type as hierarchy.** Use a font pair: a humanist sans for body and an expressive display for headlines. Optionally, a monospace for code-context content (repo names, language tags, conference dates).
- **Surfaces over borders.** Move from "border + transparent bg" to "tonal surface + no border." Modern Material 3 tonal elevation patterns work well here.
- **Day mode that is actually light, night mode that is actually deep.** Today, light = HawkesBlue almost-white, dark = ChineseBlack near-black. Push contrast.

Concrete visual deliverables expected:

1. A token palette: brand accent + 9-step neutral ramp + 5 semantic colors + 22 language tag colors (preserve / refresh).
2. A type ramp: Display, Headline (L/M/S), Title (L/M/S), Body (L/M/S), Label (L/M/S), Code (mono).
3. A spacing scale (already mostly defined in `Dimens` — keep and extend).
4. A radius scale (4 / 8 / 12 / 20).
5. An elevation scale (0 / 1 / 2 / 4 / 8 — but expressed as tonal layers, not drop shadows).
6. An icon set (Material Symbols Rounded would be a sensible base). Source icons stay branded.

---

# Desired UX Direction

## UX1 — Multi-source aggregated feed as the default Home
On launch, Home shows a "Today" stream: for each enabled source, the top 3 fresh items, day-grouped. The user can drill into a source-specific feed via a swipe or tab.

## UX2 — Source switching is always one tap, always visible
Replace the dropdown with a horizontally scrollable, sticky "source rail" with brand icons + labels. The active rail item is filled; others are outlined. This rail is always on top of Home, never hidden.

## UX3 — Topic chip strip becomes a secondary, contextual filter
Below the source rail. When the user is on the multi-source "Today" view, the topic chip strip filters across sources. When the user drills into a single source, the topic chips filter that source.

## UX4 — Pull-to-refresh, skeletons, and progressive reveal
Standard iOS-style elastic pull. Skeleton placeholders during first load. Fade-in for newly-arrived items.

## UX5 — Card actions are quiet by default, expressive on intent
Share and bookmark are *not* always visible on every card. Either:
- (a) Long-press a card → contextual menu with Bookmark, Share, Copy Link, Open in Browser.
- (b) Swipe right → bookmark toggle. Swipe left → share.
- (c) Tap a small kebab/three-dot button on the card.
The design agent should pick one consistent pattern.

## UX6 — Bookmarks is searchable and groupable
Top of the bookmarks screen: a search bar (filter by title and source). Below: a segmented control: All / By Source / By Date. The list rearranges accordingly.

## UX7 — Settings is one screen, not three
Single Settings list with sections: My Profile, My Topics, My Sources, Theme, About (version, links to source code, contact us, privacy). Each section is a tappable row that either expands inline or pushes to a focused sub-screen.

## UX8 — A "What's new for you" onboarding completion screen
After "Finish" on Setup Sources, show a quick "Your feed is ready" screen with three illustrated coachmarks:
- "Tap a source to focus."
- "Swipe a card to bookmark."
- "Pull down to refresh."
A clear "Let's go" button to enter Home.

## UX9 — Status communication
Every state has a designed surface: loading skeleton, error with illustration, empty with illustration, no-network with offline indicator chip pinned at the top of Home.

## UX10 — Read state
Cards that the user has tapped become slightly desaturated. Bookmarks differentiate between "saved but unread" and "saved and read."

## UX11 — Quiet animations everywhere
- Source rail: spring-physics underline/fill on selection.
- Topic chip: 150ms color crossfade.
- Card tap: 100ms scale-down to 0.98 then spring back.
- Bookmark toggle: small heart-burst-style scale + color animation.
- WebView open: shared-element transition where the card "expands" into the WebView container.

## UX12 — Settings persistence is instant and confirmed silently
No "Save" buttons. Every toggle persists immediately. A subtle haptic confirms.

## UX13 — Conference cards become richer
Date prominently displayed with a calendar block (day + month). Location with country flag. Online/in-person tag.

## UX14 — Profile is a soft, editable identity
Treat the user's profile choice as part of their identity displayed in Settings ("You're set up as a Mobile Engineer"). Tappable to change without re-doing the whole onboarding.

---

# Design System Expectations

Deliver a tokenized, named, documentable system. The engineering team will translate this into Compose tokens, so the design system must be semantic, not just decorative.

## Tokens

### Color tokens (semantic, not hex-named)
- `colorBrandPrimary` (and 50–900 ramp)
- `colorBrandOnPrimary`
- `colorBackground` / `colorBackgroundElevated` / `colorBackgroundOverlay`
- `colorSurface` / `colorSurfaceVariant`
- `colorOnBackground` / `colorOnSurface` / `colorOnSurfaceMuted`
- `colorBorder` / `colorBorderSubtle`
- `colorAccentSuccess` / `colorAccentWarning` / `colorAccentError` / `colorAccentInfo`
- `colorContentPositive` / `colorContentNegative` (e.g., for Reddit upvote / HackerNews points)
- `colorTagJavaScript`, `colorTagPython`, … (preserve the language map; expand to all topics in topics.json)
- Source-brand color mapping: `colorSourceGitHub` (#181717 black), `colorSourceHackerNews` (#ff6600), `colorSourceReddit` (#FF4500), `colorSourceProductHunt` (#DA552F), etc.

### Spacing tokens
Keep current `Dimens` scale and extend:
- `spacing2`, `spacing4`, `spacing8`, `spacing12`, `spacing16`, `spacing20`, `spacing24`, `spacing32`, `spacing40`, `spacing48`, `spacing64`.

### Radius tokens
- `radiusSm` (4), `radiusMd` (8), `radiusLg` (12), `radiusXl` (20), `radiusFull` (CircleShape).

### Typography tokens (Material 3 nomenclature)
- `displayLarge`, `displayMedium`, `displaySmall`
- `headlineLarge`, `headlineMedium`, `headlineSmall`
- `titleLarge`, `titleMedium`, `titleSmall`
- `bodyLarge`, `bodyMedium`, `bodySmall`
- `labelLarge`, `labelMedium`, `labelSmall`
- `codeMedium`, `codeSmall` (mono)

### Elevation tokens (tonal, not shadow-based)
- `elevation0`, `elevation1`, `elevation2`, `elevation3`, `elevation4`, `elevation5`. Each maps to a tonal surface tint.

### Motion tokens
- `durationInstant` (100ms), `durationFast` (200ms), `durationStandard` (300ms), `durationSlow` (500ms).
- `easingStandard` (cubic-bezier or spring), `easingEmphasized`, `easingDecelerated`.

## Components

The redesign must define and document these components (Compose-buildable):

1. **AppBar** (Top app bar) — title, optional leading icon, optional actions, optional bottom slot for source rail or topic chips.
2. **SourceRail** (NEW) — horizontally scrollable, sticky, persistent source switcher.
3. **TopicChipStrip** — horizontally scrollable filter chip strip, secondary level.
4. **BottomNav** (NEW, optional) — 3–4 tab bottom navigator: Today, Bookmarks, Settings, (Profile?).
5. **PrimaryButton**, **SecondaryButton**, **TextButton**, **DestructiveButton**, **IconButton**.
6. **InputField** (TextField) — for bookmarks search.
7. **Chip** variants: `FilterChip`, `InputChip`, `SuggestionChip`, `ColorTagChip`.
8. **Card** variants:
   - `ArticleCard` (shared base for HN, DevTo, Reddit, etc.)
   - `RepoCard` (GitHub-specific)
   - `LaunchCard` (ProductHunt-specific with thumbnail + upvote pill)
   - `ConferenceCard` (date block + location + tags)
   - `BookmarkCard` (with quick remove + open swipe actions)
9. **EmptyState** — illustration + title + body + optional primary CTA.
10. **ErrorState** — same shape as EmptyState but with a different mood and a Retry CTA.
11. **LoadingSkeleton** — shimmer placeholder list.
12. **SectionHeader** — "Today", "Yesterday", "This week" sticky day headers.
13. **Drawer** (existing) → may be replaced by Settings sheet or removed in favor of bottom-nav.
14. **Sheet** (NEW if needed) — bottom sheet for "swap source," "swap topic," "manage bookmarks."
15. **Toast/Snackbar** — for "Last source can't be removed" feedback.
16. **OnboardingStepIndicator** — 3-dot progress for the 3 onboarding screens.

Each component must be specified with:
- Anatomy (slots and their content rules)
- Variants (sizes, emphasis levels, states)
- States (default, hover/focus where applicable, pressed, selected, disabled, loading)
- Token references (all paddings, colors, type sizes by token name)
- Dark mode behavior
- Accessibility role and label expectations

---

# Accessibility Requirements

The redesign must meet these explicit accessibility bars:

1. **Color contrast**: All text ≥ 4.5:1 against its surface in both themes. Currently fails in light theme for grey caption text on the pale-blue background.
2. **Tap targets**: Minimum 48×48dp for every interactive element, including chips and small icon buttons. Today, the topic chip uses `LocalMinimumInteractiveComponentEnforcement provides false`, which lets chips become smaller than 48dp — this must change.
3. **Content descriptions**: Every `Icon` and `Image` must have a meaningful `contentDescription`, not `null` or empty string. Today, many icons use `contentDescription = null` (especially decorative icons in cards), which is correct only if the icon truly carries no information; many in the current code carry information (the bookmark state, the score icon, the language dot) and should not be null.
4. **Talkback / VoiceOver order**: A card should read as "Title. Owner. Programming language Kotlin. Stars 1200. Forks 230. Bookmarked. Tap to read." Currently, the FlowRow approach can produce confusing reads.
5. **Dynamic type**: Respect the user's font scale up to at least 130%. Currently, hard-coded `sp` values mostly work but `maxLines = 1` rules in many tag rows cause clipping.
6. **Reduced motion**: Honor a "reduce motion" system flag. Source rail underline animations, card tap scale, etc., should fall back to instant transitions when reduced motion is on.
7. **Focus order**: Once non-touch input arrives (keyboard via foldable, hardware keyboard on iPad), focus order must be logical.
8. **Semantic roles**: Buttons declared as Button, links as Link, lists as List with item count, headings as Heading.
9. **Theme override**: Light/Dark/System trichotomy, settable in app, persisted across sessions.

---

# Motion & Animation Direction

Motion should feel **purposeful, brief, and physical**. No decorative loops, no gratuitous parallax.

## Library of motions

1. **Source rail selection** — the active source's pill background slides between items with a spring (stiffness 380, damping 29). Color crossfades 200ms.
2. **Topic chip selection** — color crossfade 150ms standard easing.
3. **Card tap pressed state** — scale 1.0 → 0.98 over 80ms, release with spring.
4. **Bookmark toggle** — icon morphs from outline to filled (custom path morph if available, or simple crossfade with a 1.0 → 1.15 → 1.0 scale "pulse"). Optional: a subtle particle/heart-ish 200ms decoration.
5. **Pull-to-refresh** — elastic over-scroll, custom Hackertab logo spinner that rotates while loading. On finish, the logo settles back with a tiny bounce.
6. **List entry** — fade + 8dp slide-up, stagger 30ms per item, only on first load of a list (not on subsequent updates).
7. **Source switch transition** — list fades out 100ms, skeleton shows, real list fades in 200ms.
8. **Article tap → WebView** — shared-element transition: card title and source icon fly to the WebView's loading header, then dissolve into the page. (If shared-element transitions are awkward in Navigation 3 alpha, fall back to a vertical slide-up with a 100ms scrim fade.)
9. **Drawer / settings sheet open** — slide from edge with spring, 250ms.
10. **Bookmark removal** — row collapses (height tween 200ms) and fades out simultaneously.
11. **Empty-state illustrations** — gentle 4-second loop (subtle bob), pause respecting reduced motion.

Forbidden motions:
- Multi-second scripted intros.
- Parallax on hero images (we don't have hero images).
- Infinite spinners on the page level — use skeletons and elastic pull instead.

---

# Navigation Expectations

## Top-level structure

Replace the drawer-based navigation with a **bottom navigation** of 3 destinations:

1. **Today** (Home feed, multi-source default; drillable into single-source view)
2. **Bookmarks**
3. **Settings**

(Optionally: a 4th "Profile" tab if the design wants to celebrate the user's identity.)

Drawer becomes obsolete. Contact-us moves into Settings → About.

## Within Today

- Top: an `AppBar` with a small Hackertab wordmark on the left and a refresh icon on the right (also serving as the connectivity status indicator).
- Sticky persistent **SourceRail** below the app bar — horizontal scroll, "All" pseudo-source on the left, then each enabled source. The rail collapses on scroll-down (not hidden, but compressed to icon-only) and re-expands on scroll-up.
- **TopicChipStrip** below the source rail. Sticky.
- **Feed** below: day-grouped, infinite scroll if API supports paging (it doesn't today — but design as if it might).

## Within Bookmarks

- Top: AppBar with title "Bookmarks" + a search icon that expands into a search bar.
- Optional segmented control: All / By Source / By Date.
- List of `BookmarkCard`s with swipe actions.

## Within Settings

- Top: AppBar with title "Settings".
- List of section rows: My profile, My topics, My sources, Appearance, About, Contact, Privacy.
- Each row pushes to a focused sub-screen. Long settings screens (Topics, Sources) live as their own pushed pages — same content as today, but with persistent feedback (toast on minimum-1-source guard, etc.).

## Modal / sheet patterns

- **WebView**: opens as a near-full-screen modal that "rises" from the tapped card. Has a small dismiss handle at the top and a Share + Bookmark + Open-in-browser action bar at the bottom.
- **Theme switcher**: bottom sheet with three tappable rows: Light / Dark / System, with a checkmark on the active.
- **Source picker (alt)**: optional bottom sheet variant of the source rail for users who prefer modal selection.

## Back behavior

- System back / edge swipe pops modals first, then sheets, then sub-screens within a tab. Pressing back from the root of a tab goes to the previous tab if there is one, else exits.

## Tablet adaptive

- Persistent left **NavRail** instead of bottom navigation.
- Today / Bookmarks remain list-detail two-pane with auto-selected first item.
- WebView occupies the right pane permanently while a card is selected; closing the WebView empties the pane and shows a placeholder.

---

# Mobile-First Expectations

The redesign is mobile-first. Design for the phone, then adapt up.

- **Reachability**: Primary actions live in the lower half of the screen, easily reachable by thumb on devices ≥ 6 inches.
- **One-hand operation**: Source switching, topic switching, refresh, bookmark, share — all reachable by the right thumb.
- **Sensible breakpoints**: phone (< 600 dp width), foldable / tablet (600–840 dp), large tablet / iPad / desktop (> 840 dp).
- **Responsive cards**: At foldable/tablet width, cards stack into a 2-column grid in Today; at large tablet, the list-detail kicks in. ProductHunt cards span 2 columns even at tablet width because of the upvote pill on the right.
- **Safe areas**: Top status bar padding via `statusBarsPadding()`; bottom system gesture inset via `navigationBarsPadding()`. Critical actions stay above the gesture area.
- **Edge-swipe gestures**: Use system back gesture only — do not capture edge swipes for app-defined actions.
- **Keyboard handling**: Search bar in Bookmarks adjusts content with `imePadding()` so the keyboard never covers the result list.
- **Cross-platform parity**: The same screen on iOS uses native scroll behavior (rubber band), the same fonts, the same color tokens. Where iOS conventions differ (e.g., navigation bar style), use Compose Multiplatform's platform-aware modifiers but do not ship two visually different versions.

---

# Features That Must Be Preserved

Hard constraints — do not break or remove these:

1. The 3-step onboarding: Profile → Topics → Sources (with reassurance copy).
2. The 9 profile types (Mobile/Frontend/Backend/Full-Stack/DevOps/Data/Security/ML/Other).
3. The 78-topic taxonomy in topics.json (keep all labels, values, categories).
4. The 12 sources, each with their identity (icon, brand, supportsFilters flag).
5. Per-source filtering by topic where the source supports it.
6. In-app WebView for reading.
7. Bookmarking with local persistence in Room.
8. System share sheet for sharing.
9. Email-based contact-us flow with prefilled diagnostics.
10. System dark mode auto-follow as a default option.
11. Adaptive list-detail layout on tablets.
12. Anonymous, login-free experience.
13. Language tag colors for the existing 22 languages (refresh the palette but keep the mapping).
14. Minimum-1-source and minimum-1-topic invariants.
15. Reactive synchronization between Settings changes and Home feed (toggle a source in settings → it appears in Home immediately).
16. The Hackertab logo / wordmark identity (the brand mark is a known asset; refresh OK, replace not OK).

---

# Features That Can Be Reinvented

Soft territory — feel free to redesign or replace these:

1. The drawer-based navigation pattern.
2. The dropdown source switcher in the top bar.
3. The "single source at a time" home feed model.
4. The card layouts for each source (keep the data shown, change the visual).
5. The chip-group onboarding screens (consider richer profile cards with illustrations, topic categories with hero images, etc.).
6. The error-empty-loading state visual treatments.
7. The bookmark list's flat reverse-chronological model.
8. The lack of pull-to-refresh.
9. The lack of a master settings screen.
10. The lack of theme override.
11. The lack of post-onboarding orientation.
12. The visual treatment of the source rail (you're inventing it).
13. The visual treatment of the topic chip strip.
14. The motion language entirely.
15. The typography (replace Nunito with a sharper pair if appropriate).
16. The neutral palette (currently HawkesBlue / ChineseBlack — consider warmer or more neutral).
17. The bookmark/share button cluster on each card.
18. The contact-us email-only flow (could become an in-app feedback form).
19. The "with love by Zouhir" footer in the drawer (move to Settings → About).
20. The "Add source" / "Add topic" inline buttons (could become bottom-sheet flows).

---

# Suggested Redesign Priorities

The design agent should propose a redesign in **three waves**, ordered by user impact and engineering blast radius. Engineering will likely implement them in roughly this order.

## Wave 1 — Foundation (highest priority, ship first)
- Migrate to a Material 3-equivalent token system (color, typography, spacing, radius, elevation, motion).
- Replace drawer with bottom nav (Today, Bookmarks, Settings).
- Build the new SourceRail + TopicChipStrip pattern.
- Redesign the home feed list rhythm (consistent vertical spacing, day grouping, sticky section headers).
- Redesign Bookmarks (search, grouping, swipe-to-delete).
- Build the Settings master screen.
- Add the Theme override (Light/Dark/System).
- Add pull-to-refresh.
- Redesign loading / empty / error states.

## Wave 2 — Card identities and onboarding polish (second ship)
- Per-source card variants (RepoCard, LaunchCard, ConferenceCard, ArticleCard with source accent).
- Quiet card actions (long-press menu or swipe).
- Onboarding completion / orientation screen.
- Profile editability from Settings.
- Animation language: source switch, card tap, bookmark toggle.

## Wave 3 — Aspirational (third ship, requires future infra)
- "Today" multi-source aggregated feed (depends on either client-side aggregation logic or server support).
- Read state and "new since last visit" indicators (requires per-user state — can be device-local at first).
- Offline reading (matches the project roadmap; needs data layer work).
- Search across articles (requires API change).
- Reading progress / stats / weekly digest.

---

# Final Instructions To The Design Agent

Read this brief end to end before producing anything. Then deliver, in this order:

1. **Mood board / visual references** (5–10 images or descriptions) showing the emotional and stylistic target.
2. **Token sheet** (color, type, spacing, radius, elevation, motion) for both light and dark themes.
3. **Component library** — every component in §"Design System Expectations," with anatomy, variants, states, tokens, dark mode, and accessibility notes.
4. **Screen redesigns** for every screen in §"Screen-by-Screen Description":
   - Splash
   - Setup Profile
   - Setup Topics
   - Setup Sources
   - Onboarding completion (NEW)
   - Today / Home (multi-source feed)
   - Source-focused feed (drilled-into single source)
   - Source-specific card examples (GitHub, HackerNews, ProductHunt, Conferences, DevTo, Reddit — at minimum)
   - WebView reader
   - Bookmarks (with empty state, with content, with search active)
   - Settings master
   - Settings → Profile
   - Settings → Topics
   - Settings → Sources
   - Settings → Appearance (Theme)
   - Settings → About / Contact
   - Generic loading state
   - Generic error state
   - Generic empty state
5. **Tablet / large-screen variants** for: Today, Bookmarks, WebView (list-detail).
6. **Motion specifications** — for each named motion, give duration, easing, and the involved properties.
7. **Onboarding coachmark sequence** for first-run.
8. **Edge cases** — designs for: no internet, last-source-deselect attempt, all topics deselected, bookmarks > 100 items, very long article titles (5+ lines).
9. **Engineering hand-off notes** — for each screen, list the Compose components implicated, the data dependencies, and the changes required from the existing module structure.

When you write the redesign, be **opinionated**. Choose. The current product is conservative and unfinished, and a tame redesign will not move the needle. Make a decision about the brand color, the type pair, the navigation pattern, the motion language — defend each choice in 1–2 sentences, then move on.

You have permission to:
- Pivot the brand accent away from #0366D6 if you can defend the choice.
- Add a new feature (e.g., "Today brief," "Read it later queue") if it falls naturally out of an existing capability and does not require backend changes.
- Remove the drawer entirely.
- Replace Material 2 with a custom Compose theme inspired by Material 3.
- Replace the Nunito font with a developer-friendly pair (e.g., Inter + JetBrains Mono).

You do **not** have permission to:
- Require a backend feature that doesn't exist (no comments, no per-user feeds, no push, no auth).
- Drop any of the 12 sources or 9 profiles.
- Break the reactive Settings → Home synchronization invariant.
- Require native-only features unavailable in Compose Multiplatform 1.9.3 (e.g., true platform blur on Android < 12).
- Ship without a dark theme.

Treat this brief as the only context you will receive. If a question arises during design that this brief doesn't answer, default to: **what would a thoughtful, design-led developer-focused company ship in 2026?**

Now design.
