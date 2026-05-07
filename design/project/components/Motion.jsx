/* Motion.jsx — Motion specs + post-onboarding coachmark sequence + handoff. */

const MotionRow = ({ n, name, dur, easing, props, why }) => (
  <div style={{display:"grid",gridTemplateColumns:"56px 1.4fr 1fr 1.6fr 1.8fr",gap:18,padding:"16px 22px",borderTop:"1px solid var(--border-subtle)",alignItems:"flex-start"}}>
    <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)",letterSpacing:".05em"}}>{n}</div>
    <div className="t-title-md" style={{color:"var(--on-bg)"}}>{name}</div>
    <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface)"}}>{dur}<br/><span style={{color:"var(--on-surface-muted)"}}>{easing}</span></div>
    <div className="t-body-sm" style={{color:"var(--on-surface-muted)"}}>{props}</div>
    <div className="t-body-sm" style={{color:"var(--on-surface-muted)"}}>{why}</div>
  </div>
);

const Coachmark = ({ n, label, body, x, y, w = 220, point = "down" }) => (
  <div style={{position:"absolute",left:x,top:y,width:w,padding:"12px 14px",borderRadius:12,background:"var(--neutral-900)",color:"#fff",boxShadow:"0 12px 30px rgba(0,0,0,.25)",zIndex:5}}>
    <div style={{fontFamily:"var(--font-mono)",fontSize:10,letterSpacing:".1em",color:"var(--brand-primary)",marginBottom:4}}>{n}</div>
    <div className="t-title-sm" style={{color:"#fff"}}>{label}</div>
    <div className="t-body-sm" style={{color:"rgba(255,255,255,.7)",marginTop:4,lineHeight:1.4}}>{body}</div>
    <div style={{position:"absolute",left:point==="up"?28:undefined,right:point==="right"?-6:undefined,top:point==="up"?-5:point==="right"?20:undefined,bottom:point==="down"?-5:undefined,width:10,height:10,background:"var(--neutral-900)",transform:"rotate(45deg)"}}/>
  </div>
);

const CoachmarkScene = ({ n, title, body, mark }) => (
  <div style={{position:"relative"}}>
    <div className="phone-screen theme-light" style={{width:340,height:720,borderRadius:38}}>
      <StatusBar/>
      <AppBar wordmark trailing={<><button className="app-bar-icon"><Icon name="search" size={20}/></button><button className="app-bar-icon"><Icon name="refresh" size={20}/></button></>}/>
      <SourceRail sources={SOURCES.slice(0,7)} activeId="all"/>
      <TopicChipStrip topics={TOPICS.slice(0,5)} activeId="kotlin"/>
      <div style={{flex:1,padding:"0 0 90px",overflow:"hidden",filter: "saturate(.7)"}}>
        <SectionHeader label="Today" count={3}/>
        {FEED.slice(0,3).map((a,i) => <FeedCard key={i} a={a}/>)}
      </div>
      {/* Spotlight */}
      <div style={{position:"absolute",inset:0,background:"rgba(10,10,10,.55)",pointerEvents:"none"}}/>
      {mark}
      <TabBar active="today"/>
    </div>
    <div style={{marginTop:16,maxWidth:340}}>
      <div style={{fontFamily:"var(--font-mono)",fontSize:10,letterSpacing:".15em",color:"var(--brand-primary)",marginBottom:4}}>STEP {n}</div>
      <div className="t-title-lg" style={{color:"var(--on-bg)"}}>{title}</div>
      <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:4}}>{body}</div>
    </div>
  </div>
);

const Motion = () => (
  <div style={{padding:"40px 56px 60px",maxWidth:1640,margin:"0 auto"}}>
    <div style={{marginBottom:36}}>
      <div style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".15em",color:"var(--brand-primary)",marginBottom:8}}>06 · MOTION & COACHMARKS</div>
      <div className="t-display-md" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>Quiet, brief, physical.</div>
      <div className="t-body-lg" style={{color:"var(--on-surface-muted)",marginTop:14,maxWidth:780}}>One motion language: spring physics for object moves, decelerated easings for content reveals, ≤500ms ceiling. Reduced-motion replaces every animation with an instant transition.</div>
    </div>

    {/* Spec table */}
    <div style={{borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden",marginBottom:48}}>
      <div style={{display:"grid",gridTemplateColumns:"56px 1.4fr 1fr 1.6fr 1.8fr",gap:18,padding:"14px 22px",fontFamily:"var(--font-mono)",fontSize:10,letterSpacing:".1em",color:"var(--on-surface-muted)",textTransform:"uppercase"}}>
        <div>#</div><div>Motion</div><div>Duration / curve</div><div>Properties</div><div>Why</div>
      </div>
      <MotionRow n="M1" name="Source rail selection" dur="240ms" easing="spring(380, 29)" props="Pill x-translate + bg-color crossfade. Old item shrinks, new item grows." why="The pill physically slides between items, communicating 'same control, new state.'"/>
      <MotionRow n="M2" name="Topic chip selection" dur="150ms" easing="standard" props="bg-color + on-color crossfade." why="Secondary control. Quick acknowledge, no springiness needed."/>
      <MotionRow n="M3" name="Card press" dur="80ms in / 200ms out" easing="standard / spring(400, 30)" props="scale 1 → .98 → 1." why="Tactile confirmation. Cancellable on drag-out."/>
      <MotionRow n="M4" name="Bookmark toggle" dur="240ms" easing="emphasized" props="Icon morph outline → fill, scale 1 → 1.15 → 1, color crossfade." why="Tiny celebration without confetti. The pulse anchors the eye."/>
      <MotionRow n="M5" name="Pull-to-refresh" dur="elastic" easing="—" props="Custom logo spinner rotates while loading; tiny bounce on settle." why="The gesture has weight; the spinner has identity."/>
      <MotionRow n="M6" name="List entry (first load)" dur="200ms" easing="decelerated" props="opacity 0 → 1 + translateY 8 → 0, stagger 30ms/item, max 6 items." why="Reveals the rhythm of the feed without cascading forever."/>
      <MotionRow n="M7" name="Source switch" dur="100ms out / skeleton / 200ms in" easing="standard / decelerated" props="List fade-out, skeleton swap, list fade-in." why="Honest about the data fetch; never blank-screen."/>
      <MotionRow n="M8" name="Article → WebView" dur="300ms" easing="emphasized" props="Card title + source icon fly to header; card body fades to scrim, page reveals from below." why="Spatial continuity — the user sees where they came from."/>
      <MotionRow n="M9" name="Sheet open" dur="300ms" easing="emphasized" props="translateY 100% → 0 + scrim 0 → .5 opacity." why="Standard sheet. Same curve as M8 for rhythm."/>
      <MotionRow n="M10" name="Bookmark removal" dur="200ms" easing="standard" props="height collapse + opacity fade simultaneously." why="Item leaves; the list re-flows without snapping."/>
      <MotionRow n="M11" name="Toast" dur="200ms in / 4000ms hold / 200ms out" easing="decelerated" props="translateY 100% → 0; fade." why="Just long enough to read 'Bookmarked.'"/>
    </div>

    {/* Coachmarks */}
    <div style={{marginBottom:18}}>
      <div className="t-headline-md" style={{color:"var(--on-bg)",letterSpacing:"-.02em"}}>First-run coachmark sequence</div>
      <div className="t-body-md" style={{color:"var(--on-surface-muted)",marginTop:6,maxWidth:680}}>Triggered after the user taps "Open my feed" on the Onboarding Done screen. 3 spotlights, dismissable individually with "Got it" or all at once with "Skip tour". Stored in <span style={{fontFamily:"var(--font-mono)",color:"var(--on-bg)"}}>SettingsStore.coachmarks_seen=true</span>.</div>
    </div>

    <div style={{display:"grid",gridTemplateColumns:"repeat(3, 1fr)",gap:32,padding:"24px",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)"}}>
      <CoachmarkScene n="01" title="Tap a source"
        body="The rail under the title switches between All and any single source — one tap, anywhere in the app."
        mark={<>
          <div style={{position:"absolute",top:96,left:14,right:14,height:46,borderRadius:99,boxShadow:"0 0 0 4px var(--brand-primary), 0 0 0 9999px rgba(10,10,10,.55)",pointerEvents:"none"}}/>
          <Coachmark n="01 / 03" label="Tap a source" body="Switch from All → GitHub, HN, etc. Always one tap." x={20} y={150} point="up"/>
        </>}
      />
      <CoachmarkScene n="02" title="Pull to refresh"
        body="Drag the feed down to fetch the freshest items across every enabled source. The Hackertab spinner appears while loading."
        mark={<>
          <div style={{position:"absolute",top:230,left:"50%",transform:"translateX(-50%)",width:60,height:60,borderRadius:99,border:"2px solid var(--brand-primary)",boxShadow:"0 0 0 9999px rgba(10,10,10,.55)",pointerEvents:"none"}}/>
          <Coachmark n="02 / 03" label="Pull down to refresh" body="Drag the feed down. The logo spinner shows while we fetch." x={60} y={300} point="up"/>
        </>}
      />
      <CoachmarkScene n="03" title="Long-press a card"
        body="Hold any card for the action sheet — Save, Share, Open in browser, Copy link. Same gesture across every source."
        mark={<>
          <div style={{position:"absolute",top:280,left:14,right:14,height:120,borderRadius:16,border:"2px solid var(--brand-primary)",boxShadow:"0 0 0 9999px rgba(10,10,10,.55)",pointerEvents:"none"}}/>
          <Coachmark n="03 / 03" label="Long-press a card" body="Hold for Save · Share · Open · Copy link." x={50} y={420} point="up"/>
        </>}
      />
    </div>

    {/* Reduced motion */}
    <div style={{marginTop:36,padding:"22px 26px",borderRadius:18,background:"var(--surface-variant)",border:"1px solid var(--border-subtle)"}}>
      <div className="t-title-md" style={{color:"var(--on-bg)",marginBottom:8}}>Reduced motion contract</div>
      <div className="t-body-md" style={{color:"var(--on-surface-muted)",lineHeight:1.6,maxWidth:900}}>
        When the system flag is on, every motion above collapses to a 1-frame state swap: pill teleports, chip color flips, card press = no scale, bookmark = no pulse, list entry = instant, sheets fade only (no slide). The pull-to-refresh spinner still rotates because it communicates progress, not personality.
      </div>
    </div>
  </div>
);

window.Motion = Motion;
