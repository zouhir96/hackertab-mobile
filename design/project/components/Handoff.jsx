/* Handoff.jsx — Per-screen engineering notes + edge cases + roadmap. */

const HRow = ({ screen, components, data, changes }) => (
  <div style={{display:"grid",gridTemplateColumns:"1.2fr 1.6fr 1.4fr 1.6fr",gap:18,padding:"16px 22px",borderTop:"1px solid var(--border-subtle)"}}>
    <div className="t-title-md" style={{color:"var(--on-bg)"}}>{screen}</div>
    <div style={{fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface)",lineHeight:1.55}}>{components}</div>
    <div className="t-body-sm" style={{color:"var(--on-surface-muted)"}}>{data}</div>
    <div className="t-body-sm" style={{color:"var(--on-surface-muted)"}}>{changes}</div>
  </div>
);

const Handoff = () => (
  <div style={{padding:"40px 56px 60px",maxWidth:1640,margin:"0 auto"}}>
    <div style={{marginBottom:36}}>
      <div style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".15em",color:"var(--brand-primary)",marginBottom:8}}>09 · ENGINEERING HAND-OFF</div>
      <div className="t-display-md" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>Compose-shippable. Ordered by wave.</div>
      <div className="t-body-lg" style={{color:"var(--on-surface-muted)",marginTop:14,maxWidth:820}}>Compose Multiplatform 1.9.3 · no backend changes · no auth. Each row lists Compose components, data dependencies, and module changes vs. today's <span style={{fontFamily:"var(--font-mono)",color:"var(--on-bg)"}}>:composeApp</span>.</div>
    </div>

    {/* Per-screen */}
    <div style={{borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden",marginBottom:32}}>
      <div style={{display:"grid",gridTemplateColumns:"1.2fr 1.6fr 1.4fr 1.6fr",gap:18,padding:"14px 22px",fontFamily:"var(--font-mono)",fontSize:10,letterSpacing:".1em",color:"var(--on-surface-muted)",textTransform:"uppercase"}}>
        <div>Screen</div><div>Compose components</div><div>Data dependencies</div><div>Required changes</div>
      </div>
      <HRow screen="Splash"
        components="Box(brand-primary)+Logo. coil = none."
        data="None."
        changes="Replace existing splash drawable. Drop status-bar tint to match theme."/>
      <HRow screen="Setup Profile"
        components="LazyVerticalGrid(3) of ProfileCard, OnboardingStepIndicator, PrimaryButton."
        data="profiles.json (9 entries)."
        changes="Replace SuggestionChip group with new card grid. SettingsStore.profile unchanged."/>
      <HRow screen="Setup Topics"
        components="LazyColumn of CategoryAccordion → FilterChipFlowRow."
        data="topics.json (78 entries, 9 categories)."
        changes="Group flat chips into Category accordion. Persist by topic-id (existing key)."/>
      <HRow screen="Setup Sources"
        components="LazyVerticalGrid(2) of SourceTile."
        data="sources.json (12)."
        changes="Replace chip group with 2-col tile grid. Min-1 invariant preserved."/>
      <HRow screen="Onboarding Done (NEW)"
        components="Column of 3 numbered steps + PrimaryButton."
        data="None."
        changes="New screen. Insert between Setup Sources finish and Home. Set settingsStore.coachmarks_seen=false on enter."/>
      <HRow screen="Today (Home)"
        components="Scaffold + AppBar + SourceRail + TopicChipStrip + LazyColumn(SectionHeader, FeedCard*) + PullToRefresh + BottomNav."
        data="repository.feedFor(All) emits aggregated Flow<List<Item>> grouped by day. No backend change — client-side aggregation across enabled sources."
        changes="New 'All' aggregator in :data. Remove Drawer scaffold. Add PullToRefresh wrapper (Compose 1.7+ official)."/>
      <HRow screen="Source-focused feed"
        components="Same as Today, with SourceRail.activeId=source."
        data="Existing per-source repositories."
        changes="No data layer change. Switching activeId is local UI state."/>
      <HRow screen="WebView reader"
        components="Modal route, AppBar(back+external), AndroidView/UIKitView wrapper, BottomActionBar."
        data="article.url."
        changes="Wrap existing CrossPlatformWebView with new modal scaffold + bottom action bar."/>
      <HRow screen="Bookmarks"
        components="AppBar + SegmentedControl + LazyColumn(BookmarkCard) + SwipeToDismiss + SearchBar."
        data="bookmarksDao.observeAll(): Flow<List<Bookmark>>."
        changes="Add Room migration: read column. Add search query state. Group-by-source / by-date computed in UI."/>
      <HRow screen="Bookmarks → empty"
        components="EmptyState component, PrimaryButton('Browse Today')."
        data="bookmarksDao.observeAll().isEmpty()."
        changes="None beyond new state."/>
      <HRow screen="Settings master"
        components="LazyColumn of IdentityCard + grouped SettingsRow."
        data="settingsStore.profile, topics.size, sources.size, theme."
        changes="New screen. Replace drawer entries with section list. Drawer code path deleted."/>
      <HRow screen="Settings → Topics / Sources"
        components="Reuse Setup screens with 'Save' implicit (every toggle persists)."
        data="settingsStore."
        changes="Add Snackbar for min-1 invariant violation."/>
      <HRow screen="Settings → Appearance"
        components="3-row Sheet (Light/Dark/System) + preview Pair."
        data="settingsStore.themeMode."
        changes="New SettingsStore.themeMode { LIGHT, DARK, SYSTEM }. Default SYSTEM."/>
      <HRow screen="Settings → About"
        components="Identity tile + 4-row link list."
        data="BuildConfig.versionName, .versionCode."
        changes="Move 'made with ♥ by Zouhir' here from drawer footer."/>
      <HRow screen="Loading / Error / Empty"
        components="LoadingSkeleton (Brush.shimmer), ErrorState, EmptyState."
        data="Repository.state: Loading | Error(t) | Data | Empty."
        changes="Promote existing loading state machine. Error retains last filter on Retry."/>
      <HRow screen="Tablet — Today / Bookmarks"
        components="WindowSizeClass-driven layout: NavRail + ListPane + DetailPane."
        data="Same as phone."
        changes="Wrap existing tablet two-pane with NavRail. WebView lives in DetailPane."/>
    </div>

    {/* Edge cases */}
    <div style={{marginBottom:32}}>
      <div className="t-headline-md" style={{color:"var(--on-bg)",letterSpacing:"-.02em",marginBottom:14}}>Edge cases</div>
      <div style={{display:"grid",gridTemplateColumns:"repeat(3, 1fr)",gap:14}}>
        {[
          {n:"E1",t:"No internet",d:"Offline chip pinned above feed; cached feed renders dimmed; ErrorState if cache empty. Toast on regain: 'Back online — refreshed.'"},
          {n:"E2",t:"Last source deselect",d:"Toggle reverts; Snackbar: 'You need at least one source enabled.' Same path for last topic."},
          {n:"E3",t:"All topics deselected (per source)",d:"Source returns no items → EmptyState with two CTAs: 'Clear filter' / 'See all sources.' Source rail keeps source selectable."},
          {n:"E4",t:"Bookmarks > 100",d:"LazyColumn handles. Day groups collapse to summary rows after 30 items per group; tap to expand."},
          {n:"E5",t:"Long titles (5+ lines)",d:"Card title clamps to 4 lines with ellipsis; full title shown on tap → WebView header. Talkback always reads full title."},
          {n:"E6",t:"Source returns 500",d:"Per-source ErrorState inline (not whole-screen) when in source-focused view; in All, source omits silently with mono caption 'GitHub unavailable'."},
        ].map(e => (
          <div key={e.n} style={{padding:18,borderRadius:14,background:"var(--surface)",border:"1px solid var(--border-subtle)"}}>
            <div style={{fontFamily:"var(--font-mono)",fontSize:10,letterSpacing:".1em",color:"var(--brand-primary)",marginBottom:6}}>{e.n}</div>
            <div className="t-title-md" style={{color:"var(--on-bg)"}}>{e.t}</div>
            <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:6,lineHeight:1.5}}>{e.d}</div>
          </div>
        ))}
      </div>
    </div>

    {/* Wave plan */}
    <div>
      <div className="t-headline-md" style={{color:"var(--on-bg)",letterSpacing:"-.02em",marginBottom:14}}>Ship plan — three waves</div>
      <div style={{display:"grid",gridTemplateColumns:"repeat(3, 1fr)",gap:14}}>
        {[
          {h:"Wave 1 — Foundation",sub:"Token system + new shell.",items:["Migrate to Material 3 token system (this doc)","Drawer → BottomNav + NavRail","SourceRail + TopicChipStrip","Bookmarks v2 (search + group + swipe)","Settings master + Theme override","Pull-to-refresh","Skeletons / empty / error"]},
          {h:"Wave 2 — Cards & onboarding",sub:"Per-source identity, motion language.",items:["RepoCard / LaunchCard / ConferenceCard / ArticleCard","Quiet card actions (long-press menu)","Onboarding Done + 3 coachmarks","Profile editability from Settings","Spring source switch · bookmark pulse · card press"]},
          {h:"Wave 3 — Aspirational",sub:"Needs new client-side state, not new backend.",items:["Read state (per-device) + 'New since last visit' badges","'Today brief' summary card (top of feed)","Read-later queue distinct from Bookmarks","In-app feedback form (still email-backed)"]},
        ].map(w => (
          <div key={w.h} style={{padding:"22px 22px",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)"}}>
            <div className="t-title-lg" style={{color:"var(--on-bg)"}}>{w.h}</div>
            <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:4}}>{w.sub}</div>
            <div style={{height:1,background:"var(--border-subtle)",margin:"14px 0"}}/>
            <ul style={{margin:0,padding:"0 0 0 18px",fontFamily:"var(--font-sans)",fontSize:13,color:"var(--on-surface)",lineHeight:1.7}}>
              {w.items.map(i => <li key={i}>{i}</li>)}
            </ul>
          </div>
        ))}
      </div>
    </div>

    {/* Accessibility contract */}
    <div style={{marginTop:32,padding:"22px 26px",borderRadius:18,background:"var(--surface-variant)",border:"1px solid var(--border-subtle)"}}>
      <div className="t-title-md" style={{color:"var(--on-bg)",marginBottom:10}}>Accessibility contract — non-negotiable</div>
      <ul style={{margin:0,padding:"0 0 0 18px",fontFamily:"var(--font-sans)",fontSize:13,color:"var(--on-surface)",lineHeight:1.7,columnCount:2,columnGap:32}}>
        <li>All text ≥ 4.5:1 contrast in both themes (verified against on-surface-muted on bg).</li>
        <li>48dp min hit targets — remove <span style={{fontFamily:"var(--font-mono)"}}>LocalMinimumInteractiveComponentEnforcement provides false</span> from chips.</li>
        <li>Every Icon/Image carries semantic <span style={{fontFamily:"var(--font-mono)"}}>contentDescription</span> or <span style={{fontFamily:"var(--font-mono)"}}>Modifier.semantics{"{"}invisibleToUser(){"}"}</span> if decorative.</li>
        <li>Card semantics merge children: title + source + meta + state read as one node.</li>
        <li>Dynamic type up to 130%; no maxLines=1 on data-bearing rows.</li>
        <li>Reduced-motion replaces every spring with snap.</li>
        <li>System back pops modals → sheets → sub-screens → tab switch.</li>
        <li>Theme override persisted; SYSTEM is the default.</li>
      </ul>
    </div>
  </div>
);

window.Handoff = Handoff;
