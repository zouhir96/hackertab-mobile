/* Screens.jsx — all 18+ screen mockups, plus tablet variants. */

const PHONE_W = 390;
const PHONE_H = 844;

/* Wrap a screen in an iOS-style frame slot (no actual iPhone bezel — that
   lives in the canvas artboard styling). The screen is rendered as the
   plain content surface; the artboard provides the device chrome. */

const Phone = ({ children, theme = "light" }) => (
  <div className={"phone-screen theme-" + theme} style={{width: PHONE_W, height: PHONE_H, borderRadius: 44}}>
    {children}
  </div>
);

/* ===================== 01 SPLASH ===================== */
const SplashScreen = ({ theme = "dark" }) => (
  <Phone theme={theme}>
    <div style={{flex:1,display:"flex",alignItems:"center",justifyContent:"center",flexDirection:"column",gap:16}}>
      <div style={{
        width:88,height:88,borderRadius:24,
        background:"var(--brand-primary)",
        position:"relative",
        boxShadow:"0 16px 60px rgba(123,255,170,.25)",
      }}>
        <div style={{
          position:"absolute",inset:"22px 26px 22px 22px",
          background:"var(--neutral-950)",
          clipPath:"polygon(0 0, 60% 0, 100% 50%, 60% 100%, 0 100%, 40% 50%)",
        }}/>
      </div>
      <div className="t-headline-md" style={{color:"var(--on-bg)",letterSpacing:"-.02em"}}>hackertab</div>
      <div className="t-label-md" style={{color:"var(--on-surface-muted)",fontFamily:"var(--font-mono)"}}>v4.0.0</div>
    </div>
  </Phone>
);

/* ===================== 02 PROFILE ===================== */
const SetupProfile = ({ theme = "light", selected = "mobile" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <div style={{padding:"8px 24px 0"}}>
      <div style={{display:"flex",gap:6,marginBottom:28}}>
        <span style={{flex:1,height:3,borderRadius:2,background:"var(--brand-primary)"}}/>
        <span style={{flex:1,height:3,borderRadius:2,background:"var(--border)"}}/>
        <span style={{flex:1,height:3,borderRadius:2,background:"var(--border)"}}/>
      </div>
      <div className="t-display-sm" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>What kind of<br/>developer are you?</div>
      <div className="t-body-md" style={{color:"var(--on-surface-muted)",marginTop:8,maxWidth:300}}>We'll prefill the topics that match your role. You can change anything later.</div>
    </div>
    <div style={{padding:"24px 16px 0",display:"grid",gridTemplateColumns:"1fr 1fr 1fr",gap:8}}>
      {PROFILES.map(p => {
        const active = p.id === selected;
        return (
          <div key={p.id} style={{
            aspectRatio:"1 / 1.15",
            borderRadius:18,
            background: active ? "var(--brand-primary)" : "var(--surface)",
            color: active ? "var(--brand-on-primary)" : "var(--on-bg)",
            border: active ? "0" : "1px solid var(--border-subtle)",
            display:"flex",flexDirection:"column",justifyContent:"space-between",
            padding:"12px 12px 14px",
            position:"relative",
          }}>
            <div style={{fontSize:24,filter: active ? "none" : undefined}}>{p.icon}</div>
            <div>
              <div style={{fontFamily:"var(--font-sans)",fontWeight:600,fontSize:14,letterSpacing:"-.01em"}}>{p.title}</div>
              {p.sub && <div style={{fontFamily:"var(--font-mono)",fontSize:10,opacity:.7,marginTop:1,letterSpacing:".02em"}}>{p.sub}</div>}
            </div>
          </div>
        );
      })}
    </div>
    <div style={{flex:1}}/>
    <div style={{padding:"0 20px 36px"}}>
      <button style={{
        width:"100%",height:54,borderRadius:14,
        background:"var(--brand-primary)",color:"var(--brand-on-primary)",
        border:0,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:15,
        display:"inline-flex",alignItems:"center",justifyContent:"center",gap:8,
        cursor:"pointer",letterSpacing:"-.005em",
      }}>
        Continue <Icon name="arrow-right" size={18}/>
      </button>
    </div>
    <HomeIndicator/>
  </Phone>
);

/* ===================== 03 TOPICS ===================== */
const SetupTopics = ({ theme = "light" }) => {
  const cats = [
    { name:"Mobile", expanded:true,
      topics:[{l:"Android",sel:true},{l:"iOS",sel:false},{l:"Kotlin",sel:true},{l:"Swift",sel:false},{l:"Flutter",sel:false},{l:"React Native",sel:false},{l:"Jetpack Compose",sel:true}]},
    { name:"Frontend", expanded:false, topics:[]},
    { name:"Backend", expanded:false, topics:[]},
    { name:"AI / ML", expanded:false, topics:[]},
    { name:"DevOps", expanded:false, topics:[]},
    { name:"Data", expanded:false, topics:[]},
    { name:"Security", expanded:false, topics:[]},
    { name:"Blockchain", expanded:false, topics:[]},
    { name:"Other", expanded:false, topics:[]},
  ];
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <div style={{padding:"8px 24px 0"}}>
        <div style={{display:"flex",gap:6,marginBottom:28}}>
          <span style={{flex:1,height:3,borderRadius:2,background:"var(--neutral-300)"}}/>
          <span style={{flex:1,height:3,borderRadius:2,background:"var(--brand-primary)"}}/>
          <span style={{flex:1,height:3,borderRadius:2,background:"var(--border)"}}/>
        </div>
        <div className="t-display-sm" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>Pick the topics<br/>you live in.</div>
        <div className="t-body-md" style={{color:"var(--on-surface-muted)",marginTop:8}}>Multi-select. Mobile is pre-expanded based on your profile.</div>
      </div>
      <div style={{flex:1,overflow:"hidden",margin:"22px 16px 0",borderRadius:16,border:"1px solid var(--border-subtle)",background:"var(--surface)"}}>
        {cats.map((c,i) => (
          <div key={c.name} style={{borderTop: i===0?"0":"1px solid var(--border-subtle)"}}>
            <div style={{display:"flex",alignItems:"center",justifyContent:"space-between",padding:"14px 16px",cursor:"pointer"}}>
              <div style={{display:"flex",alignItems:"center",gap:10}}>
                <span className="t-title-md" style={{color:"var(--on-bg)"}}>{c.name}</span>
                {c.topics.length>0 && c.expanded && <span style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)"}}>{c.topics.filter(t=>t.sel).length} selected</span>}
              </div>
              <Icon name={c.expanded ? "chevron-up" : "chevron-down"} size={18} color="var(--on-surface-muted)"/>
            </div>
            {c.expanded && (
              <div style={{padding:"0 16px 16px",display:"flex",flexWrap:"wrap",gap:6}}>
                {c.topics.map(t => (
                  <span key={t.l} style={{
                    height:30,padding:"0 12px",
                    borderRadius:999,
                    display:"inline-flex",alignItems:"center",
                    background: t.sel ? "var(--brand-primary)" : "transparent",
                    color: t.sel ? "var(--brand-on-primary)" : "var(--on-surface)",
                    border: t.sel ? "0" : "1px solid var(--border)",
                    fontFamily:"var(--font-mono)",fontSize:12,fontWeight:500,
                  }}>{t.l}</span>
                ))}
              </div>
            )}
          </div>
        ))}
      </div>
      <div style={{padding:"16px 20px 36px"}}>
        <div style={{display:"flex",alignItems:"center",gap:8,marginBottom:14,color:"var(--on-surface-muted)"}}>
          <Icon name="info" size={14}/>
          <span style={{fontFamily:"var(--font-mono)",fontSize:11}}>You can change this anytime in Settings.</span>
        </div>
        <button style={{
          width:"100%",height:54,borderRadius:14,
          background:"var(--brand-primary)",color:"var(--brand-on-primary)",
          border:0,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:15,cursor:"pointer",
          display:"inline-flex",alignItems:"center",justifyContent:"center",gap:8,
        }}>Continue <Icon name="arrow-right" size={18}/></button>
      </div>
      <HomeIndicator/>
    </Phone>
  );
};

/* ===================== 04 SOURCES ===================== */
const SetupSources = ({ theme = "light" }) => {
  const enabled = ["github","hackernews","devto","producthunt","conferences","lobsters"];
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <div style={{padding:"8px 24px 0"}}>
        <div style={{display:"flex",gap:6,marginBottom:28}}>
          <span style={{flex:1,height:3,borderRadius:2,background:"var(--neutral-300)"}}/>
          <span style={{flex:1,height:3,borderRadius:2,background:"var(--neutral-300)"}}/>
          <span style={{flex:1,height:3,borderRadius:2,background:"var(--brand-primary)"}}/>
        </div>
        <div className="t-display-sm" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>Where should<br/>we read from?</div>
        <div className="t-body-md" style={{color:"var(--on-surface-muted)",marginTop:8}}>12 sources. Pick at least one — your feed pulls from these.</div>
      </div>
      <div style={{padding:"22px 16px 0",display:"grid",gridTemplateColumns:"1fr 1fr",gap:8}}>
        {SOURCES.filter(s=>s.id!=="all").map(s => {
          const sel = enabled.includes(s.id);
          return (
            <div key={s.id} style={{
              height:60,padding:"0 14px",
              borderRadius:14,
              display:"flex",alignItems:"center",gap:12,
              background: sel ? "var(--surface)" : "transparent",
              border: sel ? "1.5px solid var(--brand-primary)" : "1px solid var(--border-subtle)",
              position:"relative",
            }}>
              <span style={{width:30,height:30,borderRadius:8,background:s.color,display:"inline-flex",alignItems:"center",justifyContent:"center",color:"#fff",flexShrink:0}}>
                <SourceIcon source={s.glyph} size={18}/>
              </span>
              <span className="t-title-sm" style={{color:"var(--on-bg)"}}>{s.label}</span>
              {sel && (
                <span style={{position:"absolute",right:10,top:10,width:18,height:18,borderRadius:99,background:"var(--brand-primary)",color:"var(--brand-on-primary)",display:"inline-flex",alignItems:"center",justifyContent:"center"}}>
                  <Icon name="check" size={11} strokeWidth={2.6}/>
                </span>
              )}
            </div>
          );
        })}
      </div>
      <div style={{flex:1}}/>
      <div style={{padding:"16px 20px 36px"}}>
        <div style={{display:"flex",alignItems:"center",gap:8,marginBottom:14,color:"var(--on-surface-muted)"}}>
          <Icon name="info" size={14}/>
          <span style={{fontFamily:"var(--font-mono)",fontSize:11}}>{enabled.length} of 12 selected.</span>
        </div>
        <button style={{
          width:"100%",height:54,borderRadius:14,
          background:"var(--brand-primary)",color:"var(--brand-on-primary)",
          border:0,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:15,cursor:"pointer",
          display:"inline-flex",alignItems:"center",justifyContent:"center",gap:8,
        }}>Finish setup <Icon name="arrow-right" size={18}/></button>
      </div>
      <HomeIndicator/>
    </Phone>
  );
};

/* ===================== 05 ONBOARDING DONE ===================== */
const OnboardingDone = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <div style={{flex:1,display:"flex",flexDirection:"column",justifyContent:"center",padding:"0 28px"}}>
      <div style={{
        width:72,height:72,borderRadius:20,
        background:"var(--brand-primary)",
        display:"inline-flex",alignItems:"center",justifyContent:"center",
        color:"var(--brand-on-primary)",
        marginBottom:32,
        boxShadow:"0 12px 40px rgba(123,255,170,.25)",
      }}>
        <Icon name="check" size={36} strokeWidth={2.6}/>
      </div>
      <div className="t-display-sm" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>Your feed<br/>is ready.</div>
      <div className="t-body-lg" style={{color:"var(--on-surface-muted)",marginTop:14,maxWidth:300}}>One tab. All your dev news. Three things to remember:</div>
      <div style={{marginTop:32,display:"flex",flexDirection:"column",gap:18}}>
        {[
          {n:"01",t:"Tap a source",d:"Switch from All → GitHub, HN, etc. via the rail under the title."},
          {n:"02",t:"Pull to refresh",d:"Drag down anywhere on the feed to fetch what's new."},
          {n:"03",t:"Long-press a card",d:"For Bookmark · Share · Copy link · Open in browser."},
        ].map(s => (
          <div key={s.n} style={{display:"flex",gap:14,alignItems:"flex-start"}}>
            <span style={{
              fontFamily:"var(--font-mono)",fontWeight:600,fontSize:11,
              padding:"5px 8px",borderRadius:6,
              background:"var(--surface-variant)",color:"var(--brand-primary)",
              letterSpacing:".05em",flexShrink:0,
            }}>{s.n}</span>
            <div>
              <div className="t-title-md" style={{color:"var(--on-bg)"}}>{s.t}</div>
              <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:2}}>{s.d}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
    <div style={{padding:"0 20px 36px"}}>
      <button style={{
        width:"100%",height:54,borderRadius:14,
        background:"var(--brand-primary)",color:"var(--brand-on-primary)",
        border:0,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:15,cursor:"pointer",
      }}>Open my feed</button>
    </div>
    <HomeIndicator/>
  </Phone>
);

/* ===================== 06 TODAY (HOME, multi-source) ===================== */
const TodayScreen = ({ theme = "light" }) => {
  const today = FEED.slice(0, 4);
  const yesterday = FEED.slice(4, 8);
  const earlier = FEED.slice(8);
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <AppBar wordmark trailing={
        <>
          <button className="app-bar-icon" aria-label="Search"><Icon name="search" size={20}/></button>
          <button className="app-bar-icon" aria-label="Refresh"><Icon name="refresh" size={20}/></button>
        </>
      }/>
      <SourceRail sources={SOURCES.slice(0,7)} activeId="all"/>
      <TopicChipStrip topics={TOPICS.slice(0,5)} activeId="kotlin"/>
      <div style={{flex:1,overflow:"auto",paddingBottom:90}}>
        <SectionHeader label="Today" count={today.length}/>
        {today.map((a,i) => <FeedCard key={i} a={a} bookmarked={i===2}/>)}
        <SectionHeader label="Yesterday" count={yesterday.length}/>
        {yesterday.map((a,i) => <FeedCard key={"y"+i} a={a}/>)}
        <SectionHeader label="Earlier this week" count={earlier.length}/>
        {earlier.map((a,i) => <FeedCard key={"e"+i} a={a} bookmarked={i===1}/>)}
      </div>
      <TabBar active="today"/>
    </Phone>
  );
};

/* ===================== 07 SOURCE-FOCUSED FEED ===================== */
const FocusedFeed = ({ theme = "light" }) => {
  const items = FEED.filter(a => a.source === "github");
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <AppBar
        wordmark
        trailing={
          <>
            <button className="app-bar-icon"><Icon name="search" size={20}/></button>
            <button className="app-bar-icon"><Icon name="refresh" size={20}/></button>
          </>
        }
      />
      <SourceRail sources={SOURCES.slice(0,7)} activeId="github"/>
      <TopicChipStrip topics={TOPICS.slice(0,5)} activeId="rust"/>
      <div style={{flex:1,overflow:"auto",paddingBottom:90}}>
        <div style={{padding:"6px 20px 10px",display:"flex",alignItems:"baseline",justifyContent:"space-between"}}>
          <div className="t-headline-sm" style={{color:"var(--on-bg)"}}>Trending repos</div>
          <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",letterSpacing:".05em"}}>FILTER · RUST</div>
        </div>
        {items.map((a,i) => <FeedCard key={i} a={a}/>)}
        <div style={{padding:"6px 20px 10px"}}>
          <div className="t-headline-sm" style={{color:"var(--on-bg)"}}>This week</div>
        </div>
        <RepoCard a={{source:"github",owner:"tokio-rs",repo:"tokio",description:"An asynchronous runtime for the Rust programming language.",language:"Rust",langColor:"#dea584",stars:"26.1k",forks:"2.3k",time:"4d ago"}}/>
      </div>
      <TabBar active="today"/>
    </Phone>
  );
};

/* ===================== 08 WEBVIEW ===================== */
const WebViewScreen = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <div style={{display:"flex",alignItems:"center",justifyContent:"space-between",padding:"6px 12px 8px"}}>
      <button className="app-bar-icon"><Icon name="back" size={22}/></button>
      <div style={{flex:1,minWidth:0,padding:"0 6px"}}>
        <div className="t-title-sm" style={{color:"var(--on-bg)",overflow:"hidden",textOverflow:"ellipsis",whiteSpace:"nowrap"}}>Show HN: Self-hostable Linear in Rust</div>
        <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)"}}>news.ycombinator.com</div>
      </div>
      <button className="app-bar-icon"><Icon name="external" size={20}/></button>
    </div>
    <div style={{flex:1,background:"var(--surface)",margin:"4px 14px 0",borderRadius:16,overflow:"hidden",border:"1px solid var(--border-subtle)"}}>
      <div style={{height:1,background:"var(--brand-primary)",width:"60%"}}/>
      <div style={{padding:"24px 22px"}}>
        <div style={{fontFamily:"var(--font-sans)",fontWeight:700,fontSize:22,color:"var(--on-bg)",letterSpacing:"-.02em",lineHeight:1.2}}>Show HN: I built a self-hostable Linear alternative in Rust</div>
        <div style={{display:"flex",gap:8,marginTop:10,fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)"}}>
          <span>482 points</span><span>·</span><span>by zaks_</span><span>·</span><span>1h</span>
        </div>
        <div style={{height:1,background:"var(--border-subtle)",margin:"18px 0"}}/>
        {[1,2,3,4,5].map(i => (
          <div key={i} style={{display:"flex",flexDirection:"column",gap:6,marginBottom:14}}>
            <div style={{height:8,borderRadius:4,background:"var(--surface-variant)",width: 90 - i*4 + "%"}}/>
            <div style={{height:8,borderRadius:4,background:"var(--surface-variant)",width: 70 - i*3 + "%"}}/>
            <div style={{height:8,borderRadius:4,background:"var(--surface-variant)",width: 80 - i*5 + "%"}}/>
          </div>
        ))}
      </div>
    </div>
    {/* bottom action bar */}
    <div style={{padding:"10px 14px 30px",display:"flex",gap:8}}>
      <button style={{flex:1,height:46,border:"1px solid var(--border)",borderRadius:12,background:"var(--surface)",display:"inline-flex",alignItems:"center",justifyContent:"center",gap:8,color:"var(--on-surface)",fontFamily:"var(--font-sans)",fontWeight:500,fontSize:13,cursor:"pointer"}}>
        <Icon name="bookmark" size={16}/> Save
      </button>
      <button style={{flex:1,height:46,border:"1px solid var(--border)",borderRadius:12,background:"var(--surface)",display:"inline-flex",alignItems:"center",justifyContent:"center",gap:8,color:"var(--on-surface)",fontFamily:"var(--font-sans)",fontWeight:500,fontSize:13,cursor:"pointer"}}>
        <Icon name="share" size={16}/> Share
      </button>
      <button style={{flex:"0 0 46px",height:46,border:"1px solid var(--border)",borderRadius:12,background:"var(--surface)",display:"inline-flex",alignItems:"center",justifyContent:"center",color:"var(--on-surface)",cursor:"pointer"}}>
        <Icon name="more" size={18}/>
      </button>
    </div>
    <HomeIndicator/>
  </Phone>
);

/* ===================== 09 BOOKMARKS ===================== */
const BookmarksScreen = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar title="Bookmarks" subtitle={BOOKMARKS.length + " saved · " + BOOKMARKS.filter(b=>!b.read).length + " unread"}
      trailing={<>
        <button className="app-bar-icon"><Icon name="search" size={20}/></button>
        <button className="app-bar-icon"><Icon name="filter" size={20}/></button>
      </>}/>
    <div style={{padding:"4px 16px 16px",display:"flex",gap:6}}>
      {["All","By source","By date"].map((l,i)=>(
        <button key={l} style={{
          height:32,padding:"0 14px",borderRadius:999,
          background: i===0 ? "var(--on-bg)" : "transparent",
          color: i===0 ? "var(--bg)" : "var(--on-surface-muted)",
          border: i===0 ? "0" : "1px solid var(--border-subtle)",
          fontFamily:"var(--font-sans)",fontWeight:500,fontSize:12,cursor:"pointer",
        }}>{l}</button>
      ))}
    </div>
    <div style={{flex:1,overflow:"auto",paddingBottom:90}}>
      {BOOKMARKS.map((b,i) => {
        const src = SOURCES.find(s=>s.id===b.source);
        return (
          <div key={i} style={{
            margin:"0 14px 6px",padding:"14px 14px 14px 16px",
            borderRadius:14,background:"var(--surface)",
            border:"1px solid var(--border-subtle)",
            display:"flex",gap:12,alignItems:"flex-start",
            opacity: b.read ? .6 : 1,
            position:"relative",
          }}>
            {!b.read && <span style={{position:"absolute",left:6,top:"50%",transform:"translateY(-50%)",width:5,height:5,borderRadius:99,background:"var(--brand-primary)"}}/>}
            <span style={{width:30,height:30,borderRadius:8,background:src.color,display:"inline-flex",alignItems:"center",justifyContent:"center",color:"#fff",flexShrink:0}}>
              <SourceIcon source={src.glyph} size={16}/>
            </span>
            <div style={{flex:1,minWidth:0}}>
              <div className="t-title-md" style={{color:"var(--on-bg)"}}>{b.title}</div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",marginTop:3}}>{src.label} · {b.time}</div>
            </div>
            <button style={{width:30,height:30,border:0,background:"transparent",color:"var(--on-surface-muted)",cursor:"pointer",borderRadius:8}}>
              <Icon name="more-v" size={18}/>
            </button>
          </div>
        );
      })}
    </div>
    <TabBar active="bookmarks"/>
  </Phone>
);

const BookmarksEmpty = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar title="Bookmarks"/>
    <div style={{flex:1,display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"center",padding:"0 40px",paddingBottom:80}}>
      <div style={{width:80,height:80,borderRadius:24,background:"var(--surface-variant)",display:"flex",alignItems:"center",justifyContent:"center",marginBottom:22}}>
        <Icon name="bookmark" size={32} color="var(--on-surface-muted)" strokeWidth={1.4}/>
      </div>
      <div className="t-headline-sm" style={{color:"var(--on-bg)",textAlign:"center"}}>Nothing saved yet</div>
      <div className="t-body-md" style={{color:"var(--on-surface-muted)",textAlign:"center",marginTop:6,maxWidth:260}}>Tap the bookmark icon on any card to save it here for later.</div>
      <button style={{
        marginTop:22,height:42,padding:"0 18px",
        background:"transparent",color:"var(--brand-primary)",
        border:"1px solid var(--border)",borderRadius:99,
        fontFamily:"var(--font-sans)",fontWeight:500,fontSize:13,cursor:"pointer",
      }}>Browse Today</button>
    </div>
    <TabBar active="bookmarks"/>
  </Phone>
);

const BookmarksSearch = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <div style={{display:"flex",alignItems:"center",gap:10,padding:"8px 16px 10px"}}>
      <button className="app-bar-icon"><Icon name="back" size={22}/></button>
      <div style={{flex:1,height:40,padding:"0 14px",borderRadius:12,background:"var(--surface-variant)",display:"flex",alignItems:"center",gap:8}}>
        <Icon name="search" size={16} color="var(--on-surface-muted)"/>
        <span className="t-body-md" style={{color:"var(--on-bg)"}}>rust</span>
        <span style={{flex:1}}/>
        <Icon name="close" size={14} color="var(--on-surface-muted)"/>
      </div>
    </div>
    <div style={{padding:"4px 20px 12px",fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",letterSpacing:".05em"}}>2 RESULTS</div>
    <div style={{flex:1,overflow:"auto"}}>
      {BOOKMARKS.filter(b=>b.title.toLowerCase().includes("rust")).map((b,i)=>{
        const src = SOURCES.find(s=>s.id===b.source);
        return (
          <div key={i} style={{margin:"0 14px 6px",padding:"14px 16px",borderRadius:14,background:"var(--surface)",border:"1px solid var(--border-subtle)",display:"flex",gap:12}}>
            <span style={{width:30,height:30,borderRadius:8,background:src.color,display:"inline-flex",alignItems:"center",justifyContent:"center",color:"#fff",flexShrink:0}}>
              <SourceIcon source={src.glyph} size={16}/>
            </span>
            <div style={{flex:1,minWidth:0}}>
              <div className="t-title-md" style={{color:"var(--on-bg)"}}>
                {b.title.split(/(rust)/i).map((p,j)=>p.toLowerCase()==="rust"?<mark key={j} style={{background:"var(--brand-primary)",color:"var(--brand-on-primary)",padding:"0 2px",borderRadius:3}}>{p}</mark>:p)}
              </div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",marginTop:3}}>{src.label} · {b.time}</div>
            </div>
          </div>
        );
      })}
    </div>
    <HomeIndicator/>
  </Phone>
);

/* ===================== 10 SETTINGS MASTER ===================== */
const SettingsMaster = ({ theme = "light" }) => {
  const Section = ({ icon, label, value, sub }) => (
    <div style={{
      display:"flex",alignItems:"center",gap:14,
      padding:"16px 18px",
      borderTop:"1px solid var(--border-subtle)",
    }}>
      <span style={{width:32,height:32,borderRadius:8,background:"var(--surface-variant)",color:"var(--on-surface)",display:"inline-flex",alignItems:"center",justifyContent:"center"}}>
        <Icon name={icon} size={18}/>
      </span>
      <div style={{flex:1,minWidth:0}}>
        <div className="t-title-md" style={{color:"var(--on-bg)"}}>{label}</div>
        {sub && <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",marginTop:2}}>{sub}</div>}
      </div>
      {value && <div className="t-label-md" style={{color:"var(--on-surface-muted)",fontFamily:"var(--font-mono)"}}>{value}</div>}
      <Icon name="chevron-right" size={18} color="var(--on-surface-faint)"/>
    </div>
  );
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <AppBar title="Settings"/>
      <div style={{flex:1,overflow:"auto",paddingBottom:90}}>
        {/* Identity card */}
        <div style={{margin:"6px 14px 18px",padding:"18px 18px",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",display:"flex",alignItems:"center",gap:14}}>
          <div style={{width:48,height:48,borderRadius:14,background:"var(--brand-primary)",color:"var(--brand-on-primary)",display:"inline-flex",alignItems:"center",justifyContent:"center",fontSize:24}}>📱</div>
          <div style={{flex:1}}>
            <div className="t-label-sm" style={{color:"var(--on-surface-muted)"}}>You're set up as</div>
            <div className="t-headline-sm" style={{color:"var(--on-bg)",marginTop:2}}>Mobile Engineer</div>
          </div>
          <button style={{height:32,padding:"0 12px",borderRadius:99,border:"1px solid var(--border)",background:"transparent",color:"var(--on-surface)",cursor:"pointer",fontFamily:"var(--font-sans)",fontWeight:500,fontSize:12}}>Change</button>
        </div>
        <div style={{margin:"0 14px",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden"}}>
          <div style={{padding:"4px 0"}}>
            <div style={{padding:"14px 18px 6px",fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",letterSpacing:".08em"}}>FEED</div>
            <Section icon="tag" label="Topics" sub="3 categories · 23 selected"/>
            <Section icon="grid" label="Sources" sub="6 of 12 enabled"/>
          </div>
        </div>
        <div style={{margin:"14px 14px",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden"}}>
          <div style={{padding:"4px 0"}}>
            <div style={{padding:"14px 18px 6px",fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",letterSpacing:".08em"}}>APP</div>
            <Section icon="moon" label="Appearance" value="System"/>
            <Section icon="info" label="About" sub="Version 4.0.0"/>
            <Section icon="mail" label="Send feedback"/>
          </div>
        </div>
        <div style={{textAlign:"center",padding:"24px 0 8px",fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-faint)"}}>
          made with ♥ by Zouhir · v4.0.0
        </div>
      </div>
      <TabBar active="settings"/>
    </Phone>
  );
};

/* ===================== 11 SETTINGS → TOPICS ===================== */
const SettingsTopics = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar title="Topics" leading={<button className="app-bar-icon"><Icon name="back" size={22}/></button>}/>
    <div style={{padding:"0 20px 16px"}}>
      <div className="t-body-md" style={{color:"var(--on-surface-muted)"}}>78 topics across 9 categories. Saved as you tap.</div>
    </div>
    <div style={{flex:1,overflow:"auto",paddingBottom:90}}>
      {[
        {n:"Mobile",c:7,sel:5,exp:true,topics:[
          {l:"Android",sel:true},{l:"iOS",sel:false},{l:"Kotlin",sel:true},{l:"Swift",sel:true},
          {l:"Flutter",sel:false},{l:"React Native",sel:true},{l:"Jetpack Compose",sel:true}
        ]},
        {n:"Frontend",c:14,sel:0},
        {n:"Backend",c:11,sel:8},
        {n:"AI / ML",c:6,sel:4},
        {n:"DevOps",c:9,sel:0},
        {n:"Data",c:8,sel:0},
        {n:"Security",c:5,sel:0},
        {n:"Blockchain",c:6,sel:0},
      ].map((c,i) => (
        <div key={c.n} style={{margin:"0 14px 8px",borderRadius:14,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden"}}>
          <div style={{display:"flex",alignItems:"center",justifyContent:"space-between",padding:"14px 16px",cursor:"pointer"}}>
            <div style={{display:"flex",alignItems:"center",gap:10}}>
              <span className="t-title-md" style={{color:"var(--on-bg)"}}>{c.n}</span>
              <span style={{fontFamily:"var(--font-mono)",fontSize:11,color: c.sel>0 ? "var(--brand-primary)" : "var(--on-surface-muted)"}}>{c.sel}/{c.c}</span>
            </div>
            <Icon name={c.exp ? "chevron-up" : "chevron-down"} size={18} color="var(--on-surface-muted)"/>
          </div>
          {c.exp && (
            <div style={{padding:"0 16px 16px",display:"flex",flexWrap:"wrap",gap:6}}>
              {c.topics.map(t => (
                <span key={t.l} style={{
                  height:30,padding:"0 12px",borderRadius:999,
                  display:"inline-flex",alignItems:"center",
                  background: t.sel ? "var(--brand-primary)" : "transparent",
                  color: t.sel ? "var(--brand-on-primary)" : "var(--on-surface)",
                  border: t.sel ? "0" : "1px solid var(--border)",
                  fontFamily:"var(--font-mono)",fontSize:12,fontWeight:500,
                }}>{t.l}</span>
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
    <HomeIndicator/>
  </Phone>
);

/* ===================== 12 SETTINGS → SOURCES ===================== */
const SettingsSources = ({ theme = "light", showToast = false }) => {
  const enabled = ["github","hackernews","devto","producthunt","conferences","lobsters"];
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <AppBar title="Sources" leading={<button className="app-bar-icon"><Icon name="back" size={22}/></button>}/>
      <div style={{padding:"0 20px 16px"}}>
        <div className="t-body-md" style={{color:"var(--on-surface-muted)"}}>You must keep at least one source enabled.</div>
      </div>
      <div style={{flex:1,overflow:"auto",paddingBottom: showToast ? 160 : 90}}>
        {SOURCES.filter(s=>s.id!=="all").map(s => {
          const sel = enabled.includes(s.id);
          return (
            <div key={s.id} style={{
              margin:"0 14px 6px",padding:"14px 16px",
              borderRadius:14,background:"var(--surface)",
              border:"1px solid " + (sel ? "var(--border)" : "var(--border-subtle)"),
              display:"flex",alignItems:"center",gap:12,
              opacity: sel ? 1 : 0.55,
            }}>
              <span style={{width:32,height:32,borderRadius:8,background:s.color,display:"inline-flex",alignItems:"center",justifyContent:"center",color:"#fff"}}>
                <SourceIcon source={s.glyph} size={18}/>
              </span>
              <div style={{flex:1}}>
                <div className="t-title-md" style={{color:"var(--on-bg)"}}>{s.label}</div>
                <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",marginTop:2}}>{s.supportsFilters ? "Filterable by topic" : "Source-wide"}</div>
              </div>
              <span style={{
                width:44,height:26,borderRadius:99,
                background: sel ? "var(--brand-primary)" : "var(--surface-variant)",
                position:"relative",transition:"background 200ms",
              }}>
                <span style={{position:"absolute",top:3,left: sel ? 21 : 3, width:20,height:20,borderRadius:99,background:"#fff",boxShadow:"0 1px 2px rgba(0,0,0,.2)",transition:"left 200ms"}}/>
              </span>
            </div>
          );
        })}
      </div>
      {showToast && (
        <div style={{position:"absolute",left:16,right:16,bottom:100,padding:"14px 16px",borderRadius:12,background:"var(--neutral-900)",color:"var(--neutral-50)",display:"flex",alignItems:"center",gap:10,boxShadow:"0 8px 24px rgba(0,0,0,.2)",zIndex:5}}>
          <Icon name="info" size={18} color="var(--brand-primary)"/>
          <span className="t-body-sm" style={{color:"var(--neutral-50)"}}>You need at least one source enabled.</span>
        </div>
      )}
      <HomeIndicator/>
    </Phone>
  );
};

/* ===================== 13 SETTINGS → APPEARANCE ===================== */
const SettingsAppearance = ({ theme = "light" }) => {
  const Row = ({ icon, label, sel }) => (
    <div style={{
      display:"flex",alignItems:"center",gap:14,padding:"18px 18px",
      borderTop:"1px solid var(--border-subtle)",
    }}>
      <span style={{width:32,height:32,borderRadius:8,background:"var(--surface-variant)",display:"inline-flex",alignItems:"center",justifyContent:"center"}}>
        <Icon name={icon} size={18}/>
      </span>
      <div className="t-title-md" style={{flex:1,color:"var(--on-bg)"}}>{label}</div>
      {sel && <span style={{width:22,height:22,borderRadius:99,background:"var(--brand-primary)",color:"var(--brand-on-primary)",display:"inline-flex",alignItems:"center",justifyContent:"center"}}><Icon name="check" size={13} strokeWidth={2.6}/></span>}
    </div>
  );
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <AppBar title="Appearance" leading={<button className="app-bar-icon"><Icon name="back" size={22}/></button>}/>
      <div style={{padding:"0 20px 16px"}}>
        <div className="t-body-md" style={{color:"var(--on-surface-muted)"}}>Pick a theme. System follows your phone.</div>
      </div>
      <div style={{margin:"6px 14px 0",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden"}}>
        <Row icon="sun" label="Light"/>
        <Row icon="moon" label="Dark"/>
        <Row icon="settings" label="System" sel/>
      </div>
      {/* mini preview */}
      <div style={{padding:"24px 14px 0"}}>
        <div className="t-label-sm" style={{color:"var(--on-surface-muted)",padding:"0 6px 8px"}}>PREVIEW</div>
        <div style={{display:"flex",gap:10}}>
          <div style={{flex:1,aspectRatio:"4/3",borderRadius:14,padding:14,background:"var(--neutral-50)",border:"1px solid var(--border-subtle)",display:"flex",flexDirection:"column",gap:6}}>
            <div style={{height:6,width:"60%",background:"var(--neutral-900)",borderRadius:99}}/>
            <div style={{height:4,width:"80%",background:"var(--neutral-300)",borderRadius:99}}/>
            <div style={{height:4,width:"50%",background:"var(--neutral-300)",borderRadius:99}}/>
            <div style={{flex:1}}/>
            <div style={{height:24,borderRadius:8,background:"oklch(78% 0.19 145)"}}/>
          </div>
          <div style={{flex:1,aspectRatio:"4/3",borderRadius:14,padding:14,background:"oklch(9% 0.005 145)",border:"1px solid oklch(22% 0.008 145)",display:"flex",flexDirection:"column",gap:6}}>
            <div style={{height:6,width:"60%",background:"#fff",borderRadius:99}}/>
            <div style={{height:4,width:"80%",background:"oklch(50% .01 95)",borderRadius:99}}/>
            <div style={{height:4,width:"50%",background:"oklch(50% .01 95)",borderRadius:99}}/>
            <div style={{flex:1}}/>
            <div style={{height:24,borderRadius:8,background:"oklch(78% 0.19 145)"}}/>
          </div>
        </div>
      </div>
      <HomeIndicator/>
    </Phone>
  );
};

/* ===================== 14 SETTINGS → ABOUT ===================== */
const SettingsAbout = ({ theme = "light" }) => {
  const Row = ({ label, value, link }) => (
    <div style={{display:"flex",alignItems:"center",gap:14,padding:"16px 18px",borderTop:"1px solid var(--border-subtle)"}}>
      <div className="t-title-md" style={{flex:1,color:"var(--on-bg)"}}>{label}</div>
      {value && <div className="t-label-md" style={{color:"var(--on-surface-muted)",fontFamily:"var(--font-mono)"}}>{value}</div>}
      {link && <Icon name="external" size={16} color="var(--on-surface-muted)"/>}
    </div>
  );
  return (
    <Phone theme={theme}>
      <StatusBar/>
      <AppBar title="About" leading={<button className="app-bar-icon"><Icon name="back" size={22}/></button>}/>
      <div style={{padding:"6px 14px 22px"}}>
        <div style={{borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",padding:"24px",textAlign:"center"}}>
          <div style={{width:56,height:56,borderRadius:16,background:"var(--brand-primary)",margin:"0 auto 12px",position:"relative"}}>
            <div style={{position:"absolute",inset:"14px 17px 14px 14px",background:"var(--neutral-950)",clipPath:"polygon(0 0, 60% 0, 100% 50%, 60% 100%, 0 100%, 40% 50%)"}}/>
          </div>
          <div className="t-headline-sm" style={{color:"var(--on-bg)"}}>Hackertab</div>
          <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",marginTop:4}}>Version 4.0.0 (212)</div>
          <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:14,maxWidth:280,margin:"14px auto 0"}}>Open-source dev-news aggregator. Made with care by Zouhir.</div>
        </div>
      </div>
      <div style={{margin:"0 14px",borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden"}}>
        <Row label="Send feedback" link/>
        <Row label="Source code" link/>
        <Row label="Privacy" link/>
        <Row label="Rate on App Store" link/>
      </div>
      <div style={{flex:1}}/>
      <HomeIndicator/>
    </Phone>
  );
};

/* ===================== 15 LOADING / 16 ERROR / 17 EMPTY ===================== */
const LoadingState = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar wordmark trailing={<><button className="app-bar-icon"><Icon name="search" size={20}/></button><button className="app-bar-icon"><Icon name="refresh" size={20}/></button></>}/>
    <SourceRail sources={SOURCES.slice(0,7)} activeId="all"/>
    <TopicChipStrip topics={TOPICS.slice(0,5)} activeId="kotlin"/>
    <div style={{flex:1,padding:"6px 14px 90px"}}>
      <div style={{padding:"22px 6px 10px"}}><div className="t-headline-sm" style={{color:"var(--on-surface-faint)"}}>Today</div></div>
      {[1,2,3,4].map(i => (
        <div key={i} style={{margin:"0 0 8px",padding:14,background:"var(--surface)",borderRadius:16,border:"1px solid var(--border-subtle)"}}>
          <div style={{display:"flex",alignItems:"center",gap:8,marginBottom:10}}>
            <div className="shimmer" style={{width:18,height:18,borderRadius:5}}/>
            <div className="shimmer" style={{width:90,height:10,borderRadius:4}}/>
          </div>
          <div className="shimmer" style={{height:14,width: 90 - i*5 + "%",borderRadius:4,marginBottom:6}}/>
          <div className="shimmer" style={{height:14,width: 80 - i*5 + "%",borderRadius:4,marginBottom:14}}/>
          <div style={{display:"flex",gap:10}}>
            <div className="shimmer" style={{height:10,width:60,borderRadius:4}}/>
            <div className="shimmer" style={{height:10,width:50,borderRadius:4}}/>
          </div>
        </div>
      ))}
    </div>
    <TabBar active="today"/>
  </Phone>
);

const ErrorState = ({ theme = "light", noNet = false }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar wordmark/>
    <SourceRail sources={SOURCES.slice(0,7)} activeId="all"/>
    {noNet && (
      <div style={{margin:"0 14px 8px",padding:"10px 14px",borderRadius:12,background:"var(--surface-variant)",display:"flex",alignItems:"center",gap:10}}>
        <Icon name="wifi-off" size={16} color="var(--error)"/>
        <span className="t-body-sm" style={{color:"var(--on-bg)",flex:1}}>No internet connection</span>
        <button style={{height:28,padding:"0 10px",borderRadius:99,border:"1px solid var(--border)",background:"transparent",fontFamily:"var(--font-sans)",fontSize:11,fontWeight:500,color:"var(--on-bg)",cursor:"pointer"}}>Retry</button>
      </div>
    )}
    <div style={{flex:1,display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"center",padding:"0 40px 80px"}}>
      <div style={{width:80,height:80,borderRadius:24,background:"color-mix(in oklab, var(--error) 15%, transparent)",display:"flex",alignItems:"center",justifyContent:"center",marginBottom:22}}>
        <Icon name="wifi-off" size={32} color="var(--error)" strokeWidth={1.6}/>
      </div>
      <div className="t-headline-sm" style={{color:"var(--on-bg)",textAlign:"center"}}>Couldn't load your feed</div>
      <div className="t-body-md" style={{color:"var(--on-surface-muted)",textAlign:"center",marginTop:6,maxWidth:260}}>Check your connection and try again. We'll keep your filters.</div>
      <button style={{marginTop:22,height:46,padding:"0 22px",background:"var(--brand-primary)",color:"var(--brand-on-primary)",border:0,borderRadius:99,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:13,cursor:"pointer",display:"inline-flex",alignItems:"center",gap:8}}>
        <Icon name="refresh" size={15}/> Try again
      </button>
    </div>
    <TabBar active="today"/>
  </Phone>
);

const EmptyMatch = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar wordmark/>
    <SourceRail sources={SOURCES.slice(0,7)} activeId="lobsters"/>
    <TopicChipStrip topics={TOPICS.slice(0,5)} activeId="swift"/>
    <div style={{flex:1,display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"center",padding:"0 40px 80px"}}>
      <div style={{width:80,height:80,borderRadius:24,background:"var(--surface-variant)",display:"flex",alignItems:"center",justifyContent:"center",marginBottom:22}}>
        <Icon name="filter" size={32} color="var(--on-surface-muted)" strokeWidth={1.6}/>
      </div>
      <div className="t-headline-sm" style={{color:"var(--on-bg)",textAlign:"center"}}>Nothing here today</div>
      <div className="t-body-md" style={{color:"var(--on-surface-muted)",textAlign:"center",marginTop:6,maxWidth:260}}>Lobsters has no Swift posts right now. Try a different topic or source.</div>
      <div style={{display:"flex",gap:8,marginTop:22}}>
        <button style={{height:42,padding:"0 18px",background:"transparent",color:"var(--on-bg)",border:"1px solid var(--border)",borderRadius:99,fontFamily:"var(--font-sans)",fontWeight:500,fontSize:13,cursor:"pointer"}}>Clear filter</button>
        <button style={{height:42,padding:"0 18px",background:"var(--brand-primary)",color:"var(--brand-on-primary)",border:0,borderRadius:99,fontFamily:"var(--font-sans)",fontWeight:600,fontSize:13,cursor:"pointer"}}>See all sources</button>
      </div>
    </div>
    <TabBar active="today"/>
  </Phone>
);

/* ===================== EDGE: PULL TO REFRESH ===================== */
const PullToRefresh = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar wordmark trailing={<><button className="app-bar-icon"><Icon name="search" size={20}/></button><button className="app-bar-icon"><Icon name="refresh" size={20}/></button></>}/>
    <SourceRail sources={SOURCES.slice(0,7)} activeId="all"/>
    <TopicChipStrip topics={TOPICS.slice(0,5)} activeId="kotlin"/>
    <div style={{flex:1,position:"relative",overflow:"hidden",paddingBottom:90}}>
      {/* Pulldown indicator */}
      <div style={{height:80,display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"flex-end",paddingBottom:14,gap:6}}>
        <div style={{width:34,height:34,borderRadius:10,background:"var(--brand-primary)",position:"relative",animation:"spin 1s linear infinite"}}>
          <div style={{position:"absolute",inset:"8px 10px 8px 8px",background:"var(--bg)",clipPath:"polygon(0 0, 60% 0, 100% 50%, 60% 100%, 0 100%, 40% 50%)"}}/>
        </div>
        <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",letterSpacing:".05em"}}>FETCHING…</div>
      </div>
      {FEED.slice(0,3).map((a,i) => <FeedCard key={i} a={a}/>)}
    </div>
    <TabBar active="today"/>
  </Phone>
);

/* ===================== LONG-PRESS ACTION SHEET ===================== */
const LongPressSheet = ({ theme = "light" }) => (
  <Phone theme={theme}>
    <StatusBar/>
    <AppBar wordmark trailing={<><button className="app-bar-icon"><Icon name="search" size={20}/></button><button className="app-bar-icon"><Icon name="refresh" size={20}/></button></>}/>
    <SourceRail sources={SOURCES.slice(0,7)} activeId="all"/>
    <TopicChipStrip topics={TOPICS.slice(0,5)} activeId="kotlin"/>
    <div style={{flex:1,position:"relative",overflow:"hidden"}}>
      <div style={{filter:"blur(2px)",opacity:.5,paddingBottom:120}}>
        {FEED.slice(0,3).map((a,i) => <FeedCard key={i} a={a}/>)}
      </div>
      <div style={{position:"absolute",inset:0,background:"var(--bg-overlay)"}}/>
      {/* highlighted card */}
      <div style={{position:"absolute",left:14,right:14,top:60}}>
        <div style={{transform:"scale(1.02)",boxShadow:"0 20px 60px rgba(0,0,0,.25)",borderRadius:16}}>
          <FeedCard a={FEED[0]}/>
        </div>
      </div>
      {/* sheet */}
      <div style={{position:"absolute",left:14,right:14,top:280,padding:8,borderRadius:18,background:"var(--surface)",boxShadow:"0 16px 50px rgba(0,0,0,.2)",border:"1px solid var(--border)"}}>
        {[
          {icon:"bookmark",l:"Save"},
          {icon:"share",l:"Share"},
          {icon:"external",l:"Open in browser"},
          {icon:"code",l:"Copy link"},
        ].map(r => (
          <div key={r.l} style={{display:"flex",alignItems:"center",gap:14,padding:"12px 12px",borderRadius:10,cursor:"pointer"}}>
            <span style={{width:30,height:30,borderRadius:8,background:"var(--surface-variant)",display:"inline-flex",alignItems:"center",justifyContent:"center"}}><Icon name={r.icon} size={16}/></span>
            <div className="t-title-md" style={{color:"var(--on-bg)"}}>{r.l}</div>
          </div>
        ))}
      </div>
    </div>
    <TabBar active="today"/>
  </Phone>
);

/* ===================== TABLET — TODAY ===================== */
const TabletToday = ({ theme = "light" }) => (
  <div className={"theme-" + theme} style={{width:1180,height:820,background:"var(--bg)",borderRadius:24,overflow:"hidden",display:"flex",border:"1px solid var(--border)"}}>
    {/* NavRail */}
    <div style={{width:84,padding:"24px 12px",display:"flex",flexDirection:"column",gap:8,alignItems:"center",borderRight:"1px solid var(--border-subtle)"}}>
      <div style={{width:36,height:36,borderRadius:10,background:"var(--brand-primary)",position:"relative",marginBottom:18}}>
        <div style={{position:"absolute",inset:"8px 10px 8px 8px",background:"var(--neutral-950)",clipPath:"polygon(0 0, 60% 0, 100% 50%, 60% 100%, 0 100%, 40% 50%)"}}/>
      </div>
      {[
        {ico:"today",l:"Today",a:true},
        {ico:"bookmark",l:"Saved"},
        {ico:"settings",l:"Settings"},
      ].map(i => (
        <button key={i.l} style={{
          width:60,padding:"10px 0",borderRadius:12,border:0,
          background: i.a ? "var(--surface-variant)" : "transparent",
          color: i.a ? "var(--brand-primary)" : "var(--on-surface-muted)",
          display:"flex",flexDirection:"column",alignItems:"center",gap:4,cursor:"pointer",
          fontFamily:"var(--font-sans)",fontSize:11,fontWeight:500,
        }}>
          <Icon name={i.ico} size={22}/> {i.l}
        </button>
      ))}
    </div>
    {/* List pane */}
    <div style={{width:440,borderRight:"1px solid var(--border-subtle)",display:"flex",flexDirection:"column"}}>
      <div style={{padding:"22px 22px 12px"}}>
        <div className="t-headline-md" style={{color:"var(--on-bg)"}}>Today's feed</div>
      </div>
      <div style={{padding:"0 16px 12px",display:"flex",gap:6,overflowX:"auto"}}>
        {SOURCES.slice(0,6).map(s => (
          <button key={s.id} style={{
            flex:"0 0 auto",height:34,padding:s.id==="all"?"0 12px":"0 10px 0 6px",
            display:"flex",alignItems:"center",gap:6,
            background: s.id==="all" ? "var(--brand-primary)" : "transparent",
            color: s.id==="all" ? "var(--brand-on-primary)" : "var(--on-surface)",
            border: s.id==="all" ? "0" : "1px solid var(--border)",
            borderRadius:999,fontFamily:"var(--font-sans)",fontSize:12,fontWeight:600,cursor:"pointer",whiteSpace:"nowrap"
          }}>
            {s.id==="all" ? "★ All" : <>
              <span style={{width:20,height:20,borderRadius:6,background:s.color,color:"#fff",display:"inline-flex",alignItems:"center",justifyContent:"center"}}>
                <SourceIcon source={s.glyph} size={12}/>
              </span>
              {s.label}
            </>}
          </button>
        ))}
      </div>
      <div style={{flex:1,overflow:"auto"}}>
        <div style={{padding:"6px 20px 8px"}}><div className="t-label-sm" style={{color:"var(--on-surface-muted)"}}>TODAY · 4</div></div>
        {FEED.slice(0,4).map((a,i) => (
          <div key={i} style={{cursor:"pointer",background: i===1 ? "var(--surface-variant)" : "transparent"}}>
            <FeedCard a={a} bookmarked={i===2}/>
          </div>
        ))}
      </div>
    </div>
    {/* Detail pane (webview) */}
    <div style={{flex:1,padding:"22px",overflow:"auto"}}>
      <div style={{display:"flex",alignItems:"center",gap:10,marginBottom:14}}>
        <span style={{width:22,height:22,borderRadius:6,background:"#ff6600",display:"inline-flex",alignItems:"center",justifyContent:"center",color:"#fff"}}><SourceIcon source="hackernews" size={14}/></span>
        <span className="t-label-md" style={{color:"var(--on-surface-muted)",fontFamily:"var(--font-mono)"}}>news.ycombinator.com</span>
        <span style={{flex:1}}/>
        <button className="app-bar-icon"><Icon name="bookmark" size={18}/></button>
        <button className="app-bar-icon"><Icon name="share" size={18}/></button>
        <button className="app-bar-icon"><Icon name="external" size={18}/></button>
      </div>
      <div className="t-display-sm" style={{color:"var(--on-bg)",letterSpacing:"-.025em",maxWidth:560}}>Show HN: I built a self-hostable Linear alternative in Rust</div>
      <div style={{display:"flex",gap:12,marginTop:10,fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface-muted)"}}>
        <span>482 points</span><span>·</span><span>by zaks_</span><span>·</span><span>1h ago</span><span>·</span><span>213 comments</span>
      </div>
      <div style={{height:1,background:"var(--border-subtle)",margin:"22px 0"}}/>
      {[1,2,3,4,5,6,7,8].map(i => (
        <div key={i} style={{display:"flex",flexDirection:"column",gap:8,marginBottom:16}}>
          <div style={{height:10,borderRadius:5,background:"var(--surface-variant)",width:90 - i*3 + "%"}}/>
          <div style={{height:10,borderRadius:5,background:"var(--surface-variant)",width:80 - i*4 + "%"}}/>
          <div style={{height:10,borderRadius:5,background:"var(--surface-variant)",width:70 - i*3 + "%"}}/>
        </div>
      ))}
    </div>
  </div>
);

/* ===================== TABLET — BOOKMARKS ===================== */
const TabletBookmarks = ({ theme = "light" }) => (
  <div className={"theme-" + theme} style={{width:1180,height:820,background:"var(--bg)",borderRadius:24,overflow:"hidden",display:"flex",border:"1px solid var(--border)"}}>
    <div style={{width:84,padding:"24px 12px",display:"flex",flexDirection:"column",gap:8,alignItems:"center",borderRight:"1px solid var(--border-subtle)"}}>
      <div style={{width:36,height:36,borderRadius:10,background:"var(--brand-primary)",position:"relative",marginBottom:18}}>
        <div style={{position:"absolute",inset:"8px 10px 8px 8px",background:"var(--neutral-950)",clipPath:"polygon(0 0, 60% 0, 100% 50%, 60% 100%, 0 100%, 40% 50%)"}}/>
      </div>
      {[{ico:"today",l:"Today"},{ico:"bookmark",l:"Saved",a:true},{ico:"settings",l:"Settings"}].map(i => (
        <button key={i.l} style={{width:60,padding:"10px 0",borderRadius:12,border:0,background:i.a?"var(--surface-variant)":"transparent",color:i.a?"var(--brand-primary)":"var(--on-surface-muted)",display:"flex",flexDirection:"column",alignItems:"center",gap:4,cursor:"pointer",fontFamily:"var(--font-sans)",fontSize:11,fontWeight:500}}>
          <Icon name={i.ico} size={22}/> {i.l}
        </button>
      ))}
    </div>
    <div style={{width:440,borderRight:"1px solid var(--border-subtle)",display:"flex",flexDirection:"column"}}>
      <div style={{padding:"22px 22px 8px"}}>
        <div className="t-headline-md" style={{color:"var(--on-bg)"}}>Bookmarks</div>
        <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:2}}>{BOOKMARKS.length} saved · {BOOKMARKS.filter(b=>!b.read).length} unread</div>
      </div>
      <div style={{padding:"6px 20px 12px"}}>
        <div style={{height:38,padding:"0 14px",borderRadius:10,background:"var(--surface-variant)",display:"flex",alignItems:"center",gap:8}}>
          <Icon name="search" size={16} color="var(--on-surface-muted)"/>
          <span className="t-body-md" style={{color:"var(--on-surface-muted)"}}>Search bookmarks…</span>
        </div>
      </div>
      <div style={{flex:1,overflow:"auto"}}>
        {BOOKMARKS.map((b,i) => {
          const src = SOURCES.find(s=>s.id===b.source);
          return (
            <div key={i} style={{margin:"0 14px 6px",padding:"14px 14px",borderRadius:14,background: i===0 ? "var(--surface-variant)" : "var(--surface)",border:"1px solid var(--border-subtle)",display:"flex",gap:12,opacity: b.read ? .7 : 1}}>
              <span style={{width:30,height:30,borderRadius:8,background:src.color,display:"inline-flex",alignItems:"center",justifyContent:"center",color:"#fff",flexShrink:0}}>
                <SourceIcon source={src.glyph} size={16}/>
              </span>
              <div style={{flex:1,minWidth:0}}>
                <div className="t-title-md" style={{color:"var(--on-bg)"}}>{b.title}</div>
                <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",marginTop:2}}>{src.label} · {b.time}</div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
    <div style={{flex:1,display:"flex",alignItems:"center",justifyContent:"center",padding:"40px"}}>
      <div style={{textAlign:"center",maxWidth:380}}>
        <div style={{width:64,height:64,borderRadius:18,background:"var(--surface-variant)",display:"inline-flex",alignItems:"center",justifyContent:"center",marginBottom:16}}>
          <Icon name="bookmark" size={26} color="var(--on-surface-muted)" strokeWidth={1.4}/>
        </div>
        <div className="t-headline-sm" style={{color:"var(--on-bg)"}}>Select a bookmark to read</div>
        <div className="t-body-md" style={{color:"var(--on-surface-muted)",marginTop:6}}>Pick any item from the list. The reader opens here.</div>
      </div>
    </div>
  </div>
);

/* ===================== EXPORT ===================== */
Object.assign(window, {
  SplashScreen, SetupProfile, SetupTopics, SetupSources, OnboardingDone,
  TodayScreen, FocusedFeed, WebViewScreen,
  BookmarksScreen, BookmarksEmpty, BookmarksSearch,
  SettingsMaster, SettingsTopics, SettingsSources, SettingsAppearance, SettingsAbout,
  LoadingState, ErrorState, EmptyMatch, PullToRefresh, LongPressSheet,
  TabletToday, TabletBookmarks
});
