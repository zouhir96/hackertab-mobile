/* Library.jsx — Component library reference (anatomy, variants, states, a11y). */

const SubH = ({ children }) => (
  <div style={{fontFamily:"var(--font-mono)",fontSize:10,letterSpacing:".1em",color:"var(--on-surface-muted)",marginBottom:8,textTransform:"uppercase"}}>{children}</div>
);

const ComponentBlock = ({ id, name, slug, anatomy, variants, states, a11y, tokens, children }) => (
  <div id={id} style={{
    marginBottom:30,padding:"26px 28px",borderRadius:18,
    background:"var(--surface)",border:"1px solid var(--border-subtle)",
  }}>
    <div style={{display:"flex",alignItems:"baseline",gap:14,marginBottom:18}}>
      <span style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".15em",color:"var(--brand-primary)"}}>{slug}</span>
      <div className="t-headline-sm" style={{color:"var(--on-bg)"}}>{name}</div>
    </div>

    <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:24,alignItems:"flex-start"}}>
      <div style={{
        minHeight:160,padding:24,borderRadius:14,background:"var(--bg)",
        border:"1px solid var(--border-subtle)",
        display:"flex",alignItems:"center",justifyContent:"center",flexWrap:"wrap",gap:12,
      }}>{children}</div>

      <div style={{display:"flex",flexDirection:"column",gap:14}}>
        <div>
          <SubH>Anatomy</SubH>
          <ul style={{margin:0,padding:"0 0 0 18px",fontFamily:"var(--font-sans)",fontSize:13,color:"var(--on-surface)",lineHeight:1.6}}>
            {anatomy.map(a => <li key={a}>{a}</li>)}
          </ul>
        </div>
        {variants && (
          <div>
            <SubH>Variants</SubH>
            <div style={{display:"flex",flexWrap:"wrap",gap:6}}>
              {variants.map(v => <span key={v} style={{fontFamily:"var(--font-mono)",fontSize:11,padding:"3px 8px",borderRadius:6,background:"var(--surface-variant)",color:"var(--on-bg)"}}>{v}</span>)}
            </div>
          </div>
        )}
        {states && (
          <div>
            <SubH>States</SubH>
            <div style={{display:"flex",flexWrap:"wrap",gap:6}}>
              {states.map(v => <span key={v} style={{fontFamily:"var(--font-mono)",fontSize:11,padding:"3px 8px",borderRadius:6,background:"transparent",border:"1px solid var(--border)",color:"var(--on-surface-muted)"}}>{v}</span>)}
            </div>
          </div>
        )}
        {tokens && (
          <div>
            <SubH>Tokens</SubH>
            <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)",lineHeight:1.7}}>{tokens}</div>
          </div>
        )}
        {a11y && (
          <div>
            <SubH>Accessibility</SubH>
            <div className="t-body-sm" style={{color:"var(--on-surface-muted)",lineHeight:1.5}}>{a11y}</div>
          </div>
        )}
      </div>
    </div>
  </div>
);

const Library = () => (
  <div style={{padding:"40px 56px 60px",maxWidth:1640,margin:"0 auto"}}>
    <div style={{marginBottom:36}}>
      <div style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".15em",color:"var(--brand-primary)",marginBottom:8}}>03 · COMPONENT LIBRARY</div>
      <div className="t-display-md" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>16 components. Compose-buildable.</div>
      <div className="t-body-lg" style={{color:"var(--on-surface-muted)",marginTop:14,maxWidth:780}}>Each component declares its slots, states, tokens, and accessibility role. Engineering should be able to reproduce these from the spec without re-deriving design intent.</div>
    </div>

    <ComponentBlock slug="03.01" name="AppBar"
      anatomy={["52dp height","Leading slot (icon button or null)","Title or wordmark slot (mutually exclusive)","Trailing actions (max 3 IconButtons)","Optional bottom slot for SourceRail / TopicChipStrip"]}
      variants={["wordmark","title","title+subtitle"]}
      states={["default","scrolled (border-subtle reveals)"]}
      tokens="bg, on-bg, surface-variant (icon hover)"
      a11y="Title is heading role. Wordmark sets contentDescription='Hackertab'.">
      <div style={{width:340,height:52,padding:"0 16px",borderRadius:14,background:"var(--surface)",border:"1px solid var(--border-subtle)",display:"flex",alignItems:"center",justifyContent:"space-between"}}>
        <div className="wordmark" style={{display:"flex",alignItems:"center",gap:8,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:16,color:"var(--on-bg)",letterSpacing:"-.02em"}}>
          <span style={{width:16,height:16,borderRadius:5,background:"var(--brand-primary)"}}/> hackertab
        </div>
        <div style={{display:"flex",gap:4}}>
          <span style={{width:32,height:32,borderRadius:99,display:"inline-flex",alignItems:"center",justifyContent:"center",color:"var(--on-surface-muted)"}}><Icon name="search" size={18}/></span>
          <span style={{width:32,height:32,borderRadius:99,display:"inline-flex",alignItems:"center",justifyContent:"center",color:"var(--on-surface-muted)"}}><Icon name="refresh" size={18}/></span>
        </div>
      </div>
    </ComponentBlock>

    <ComponentBlock slug="03.02" name="SourceRail (NEW)"
      anatomy={["Sticky horizontal scroll","'★ All' pseudo-source first","Each enabled source as filled-when-active pill","16dp content padding, 6dp gap","Collapses to icon-only on scroll-down"]}
      variants={["expanded (default)","collapsed (icon-only on scroll)"]}
      states={["default","selected","pressed (scale 0.97)","disabled"]}
      tokens="brand-primary (active), surface, border, on-bg"
      a11y="Tab role. Active item gets aria-selected=true. List labelled 'Sources'.">
      <SourceRail sources={SOURCES.slice(0,5)} activeId="github"/>
    </ComponentBlock>

    <ComponentBlock slug="03.03" name="TopicChipStrip"
      anatomy={["30dp height (44dp hit target)","6dp dot + 12px mono label","Active = filled on-bg, inactive = ghost border-subtle","'+' chip last for add-topic"]}
      variants={["filter","input","suggestion","color-tag"]}
      states={["default","selected","disabled"]}
      tokens="on-bg (active), border-subtle (idle)"
      a11y="Filter chips role=switch with aria-checked.">
      <TopicChipStrip topics={TOPICS.slice(0,6)} activeId="kotlin"/>
    </ComponentBlock>

    <ComponentBlock slug="03.04" name="Buttons — Primary, Secondary, Text, Destructive, Icon"
      anatomy={["Primary: 46dp height, brand-primary fill","Secondary: ghost + 1dp border","Text: no chrome","Destructive: error fill","Icon: 36×36 hit target"]}
      variants={["sm","md","lg"]}
      states={["default","pressed (0.97)","disabled (opacity .4)","loading (spinner replaces label)"]}
      tokens="brand-primary, brand-on-primary, error, on-surface"
      a11y="role=button. Disabled sets aria-disabled.">
      <button style={{height:42,padding:"0 18px",borderRadius:12,background:"var(--brand-primary)",color:"var(--brand-on-primary)",border:0,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:13,cursor:"pointer"}}>Primary</button>
      <button style={{height:42,padding:"0 18px",borderRadius:12,background:"transparent",color:"var(--on-bg)",border:"1px solid var(--border)",fontFamily:"var(--font-sans)",fontWeight:500,fontSize:13,cursor:"pointer"}}>Secondary</button>
      <button style={{height:42,padding:"0 14px",borderRadius:12,background:"transparent",color:"var(--brand-primary)",border:0,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:13,cursor:"pointer"}}>Text</button>
      <button style={{height:42,padding:"0 18px",borderRadius:12,background:"var(--error)",color:"#fff",border:0,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:13,cursor:"pointer"}}>Delete</button>
      <button style={{width:42,height:42,borderRadius:12,background:"var(--surface-variant)",color:"var(--on-bg)",border:0,display:"inline-flex",alignItems:"center",justifyContent:"center",cursor:"pointer"}}><Icon name="bookmark" size={18}/></button>
    </ComponentBlock>

    <ComponentBlock slug="03.05" name="InputField"
      anatomy={["Leading icon slot","Placeholder","Clear button on focus","Error caption slot below"]}
      states={["empty","focused","filled","error","disabled"]}
      tokens="surface-variant (bg), on-surface-muted (placeholder), error"
      a11y="aria-label required. Errors via aria-describedby.">
      <div style={{width:300,height:40,padding:"0 14px",borderRadius:10,background:"var(--surface-variant)",display:"flex",alignItems:"center",gap:8}}>
        <Icon name="search" size={16} color="var(--on-surface-muted)"/>
        <span style={{fontFamily:"var(--font-sans)",fontSize:14,color:"var(--on-surface-muted)"}}>Search bookmarks…</span>
      </div>
    </ComponentBlock>

    <ComponentBlock slug="03.06" name="Card variants — Article / Repo / Launch / Conference / Bookmark"
      anatomy={["16dp radius shell","SourceTag header (icon + label + time)","Title (Title Lg)","Source-specific meta row","CardActions: bookmark + kebab"]}
      variants={["ArticleCard","RepoCard","LaunchCard","ConferenceCard","BookmarkCard"]}
      states={["default","fresh (3dp brand bar)","read (.65 opacity)","pressed (.98 scale)"]}
      tokens="surface, border-subtle, brand-primary (fresh)"
      a11y="role=article. Title is heading. Talkback reads: title, source, time, meta, bookmark state.">
      <div style={{width:380,maxWidth:"100%"}}>
        <RepoCard a={FEED[0]}/>
      </div>
    </ComponentBlock>

    <ComponentBlock slug="03.07" name="EmptyState / ErrorState / LoadingSkeleton"
      anatomy={["80dp icon container","Headline Sm + Body Md","Optional CTA (or two)","Skeleton: shimmer rows matching card rhythm"]}
      variants={["empty.bookmarks","empty.match","error.network","loading.first"]}
      states={["pulse-loop (paused on reduce-motion)"]}
      tokens="surface-variant (icon bg), brand-primary (CTA), error (error variant)"
      a11y="role=status. Live region for loading→loaded transitions.">
      <div style={{display:"flex",flexDirection:"column",alignItems:"center",gap:8,padding:24}}>
        <div style={{width:60,height:60,borderRadius:18,background:"var(--surface-variant)",display:"flex",alignItems:"center",justifyContent:"center"}}><Icon name="bookmark" size={28} color="var(--on-surface-muted)" strokeWidth={1.5}/></div>
        <div className="t-title-md" style={{color:"var(--on-bg)"}}>Nothing saved yet</div>
        <div className="t-body-sm" style={{color:"var(--on-surface-muted)",textAlign:"center"}}>Tap bookmark on any card.</div>
      </div>
    </ComponentBlock>

    <ComponentBlock slug="03.08" name="SectionHeader"
      anatomy={["22dp top padding · 10dp bottom","Headline Sm label","Optional uppercase mono count on right"]}
      variants={["Today","Yesterday","Earlier this week","Custom"]}
      tokens="on-bg, on-surface-muted"
      a11y="role=heading aria-level=2. Sticky in scroll region.">
      <SectionHeader label="Today" count={4}/>
    </ComponentBlock>

    <ComponentBlock slug="03.09" name="BottomNav (TabBar)"
      anatomy={["3 tabs: Today / Saved / Settings","78dp incl. safe-area","Active = brand-primary text + icon","Backdrop blur"]}
      variants={["mobile","tablet → NavRail (84dp wide, vertical)"]}
      states={["default","selected","pressed (.97)","badge (count)"]}
      tokens="bg (blurred), brand-primary (active)"
      a11y="role=tablist. Each tab role=tab with aria-selected.">
      <div style={{position:"relative",width:340,height:78}}><TabBar active="today"/></div>
    </ComponentBlock>

    <ComponentBlock slug="03.10" name="Toast / Snackbar"
      anatomy={["Auto-dismiss 4s","Optional action (Undo)","Pinned 16dp above tab bar"]}
      variants={["info","success","warning","error"]}
      tokens="neutral-900 (bg), neutral-50 (text), brand-primary (accent)"
      a11y="role=status, aria-live=polite.">
      <div style={{padding:"14px 16px",borderRadius:12,background:"var(--neutral-900)",color:"var(--neutral-50)",display:"flex",alignItems:"center",gap:10,boxShadow:"0 8px 24px rgba(0,0,0,.2)"}}>
        <Icon name="info" size={16} color="var(--brand-primary)"/>
        <span className="t-body-sm" style={{color:"var(--neutral-50)"}}>Bookmarked. Saved to library.</span>
        <button style={{height:26,padding:"0 8px",borderRadius:99,background:"transparent",border:"1px solid rgba(255,255,255,.2)",color:"#fff",fontFamily:"var(--font-sans)",fontSize:11,fontWeight:500,marginLeft:8,cursor:"pointer"}}>Undo</button>
      </div>
    </ComponentBlock>

    <ComponentBlock slug="03.11" name="Sheet (BottomSheet)"
      anatomy={["Drag handle, 36×4 dp","Optional title","List of action rows","16dp side padding"]}
      variants={["actions (long-press)","picker (theme/source)","form (filter)"]}
      states={["expanded","peek","dismissed (slide-down 300ms)"]}
      tokens="surface, border, on-bg"
      a11y="role=dialog aria-modal. Trap focus while open.">
      <div style={{width:300,padding:"6px 8px 8px",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border)",boxShadow:"0 12px 30px rgba(0,0,0,.1)"}}>
        <div style={{width:32,height:4,background:"var(--border)",borderRadius:99,margin:"4px auto 8px"}}/>
        {[{i:"bookmark",l:"Save"},{i:"share",l:"Share"},{i:"external",l:"Open in browser"}].map(r => (
          <div key={r.l} style={{display:"flex",alignItems:"center",gap:14,padding:"10px 10px",borderRadius:8}}>
            <span style={{width:28,height:28,borderRadius:8,background:"var(--surface-variant)",display:"inline-flex",alignItems:"center",justifyContent:"center"}}><Icon name={r.i} size={14}/></span>
            <div className="t-title-sm" style={{color:"var(--on-bg)"}}>{r.l}</div>
          </div>
        ))}
      </div>
    </ComponentBlock>

    <ComponentBlock slug="03.12" name="OnboardingStepIndicator"
      anatomy={["3 segments, equal flex","3dp height, 2dp radius","Active = brand-primary; complete = neutral-300; idle = border"]}
      variants={["3-step (default)"]}
      a11y="role=progressbar aria-valuenow / aria-valuemax=3.">
      <div style={{width:280,display:"flex",gap:6}}>
        <span style={{flex:1,height:3,borderRadius:2,background:"var(--neutral-300)"}}/>
        <span style={{flex:1,height:3,borderRadius:2,background:"var(--brand-primary)"}}/>
        <span style={{flex:1,height:3,borderRadius:2,background:"var(--border)"}}/>
      </div>
    </ComponentBlock>

  </div>
);

window.Library = Library;
