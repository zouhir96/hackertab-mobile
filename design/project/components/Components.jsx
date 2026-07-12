/* Components.jsx — Hackertab v4 component library.
   Each component is built mobile-first, expects CSS tokens to be present,
   and exports to window for cross-script access.
*/

/* ===================== STATUS BAR ===================== */
const StatusBar = ({ time = "9:41" }) => (
  <div className="status-bar">
    <span>{time}</span>
    <span className="right">
      <svg width="18" height="11" viewBox="0 0 18 11" fill="currentColor"><path d="M1 8h2v2H1zM5 6h2v4H5zM9 4h2v6H9zM13 2h2v8h-2z"/></svg>
      <svg width="16" height="11" viewBox="0 0 16 11" fill="currentColor"><path d="M8 2.5a8.6 8.6 0 0 1 5.6 2.1.5.5 0 0 0 .7-.1l1.2-1.5a.5.5 0 0 0-.1-.7 11.5 11.5 0 0 0-14.8 0 .5.5 0 0 0-.1.7L1.7 4.5a.5.5 0 0 0 .7.1A8.6 8.6 0 0 1 8 2.5zm0 3.4a5.2 5.2 0 0 1 3.4 1.3.5.5 0 0 0 .7 0L13.3 6a.5.5 0 0 0 0-.7 7.6 7.6 0 0 0-10.6 0 .5.5 0 0 0 0 .7l1.2 1.2a.5.5 0 0 0 .7 0A5.2 5.2 0 0 1 8 5.9zM8 9a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/></svg>
      <svg width="26" height="12" viewBox="0 0 26 12" fill="none"><rect x="0.5" y="0.5" width="22" height="11" rx="3" stroke="currentColor" opacity=".4"/><rect x="2" y="2" width="17" height="8" rx="1.5" fill="currentColor"/><rect x="23" y="4" width="2" height="4" rx="1" fill="currentColor" opacity=".4"/></svg>
    </span>
  </div>
);

/* ===================== HOME INDICATOR ===================== */
const HomeIndicator = () => (
  <div style={{position:"absolute",bottom:6,left:"50%",transform:"translateX(-50%)",width:134,height:5,borderRadius:3,background:"var(--on-bg)",opacity:.85,zIndex:20,pointerEvents:"none"}}/>
);

/* ===================== APP BAR ===================== */
const AppBar = ({ title, leading, trailing, wordmark, subtitle }) => (
  <div className="app-bar">
    <div style={{display:"flex",alignItems:"center",gap:12}}>
      {leading}
      {wordmark ? (
        <div className="wordmark">
          <span className="logo-dot"/>
          <span>hackertab</span>
        </div>
      ) : title ? (
        <div>
          <div className="t-title-lg" style={{color:"var(--on-bg)"}}>{title}</div>
          {subtitle && <div className="t-label-md" style={{color:"var(--on-surface-muted)",marginTop:1}}>{subtitle}</div>}
        </div>
      ) : null}
    </div>
    <div style={{display:"flex",alignItems:"center",gap:4}}>{trailing}</div>
  </div>
);

/* ===================== SOURCE RAIL ===================== */
/* Sticky horizontal source switcher. Active = filled brand pill,
   inactive = ghost. The "All" pseudo-source comes first, then the
   user's enabled sources.
*/
const SourceRail = ({ sources, activeId, onSelect }) => (
  <div style={{
    display:"flex",gap:6,overflowX:"auto",
    padding:"6px 16px 12px",
    scrollbarWidth:"none",
  }}>
    {sources.map(s => {
      const active = s.id === activeId;
      return (
        <button key={s.id} onClick={() => onSelect && onSelect(s.id)} style={{
          flex: "0 0 auto",
          height: 38,
          padding: s.id === "all" ? "0 14px" : "0 12px 0 8px",
          display:"flex",alignItems:"center",gap:8,
          background: active ? "var(--brand-primary)" : "transparent",
          color: active ? "var(--brand-on-primary)" : "var(--on-surface)",
          border: active ? "0" : "1px solid var(--border)",
          borderRadius: 999,
          fontFamily:"var(--font-sans)",fontSize:13,fontWeight:600,
          letterSpacing:"-0.005em",
          cursor:"pointer",whiteSpace:"nowrap",
        }}>
          {s.id === "all"
            ? <span style={{fontFamily:"var(--font-mono)",fontWeight:600,letterSpacing:"-.04em"}}>★ All</span>
            : <>
                <span style={{
                  width: 22, height: 22, borderRadius: 6,
                  background: active ? "rgba(0,0,0,.12)" : s.color,
                  color: active ? "var(--brand-on-primary)" : "#fff",
                  display:"inline-flex",alignItems:"center",justifyContent:"center"
                }}>
                  <SourceIcon source={s.glyph} size={14}/>
                </span>
                <span>{s.label}</span>
              </>
          }
        </button>
      );
    })}
  </div>
);

/* ===================== TOPIC CHIP STRIP ===================== */
const TopicChipStrip = ({ topics, activeId, onSelect, showAdd = true }) => (
  <div style={{
    display:"flex",gap:6,overflowX:"auto",
    padding:"0 16px 12px",
    scrollbarWidth:"none",
  }}>
    {topics.map(t => {
      const active = t.id === activeId;
      return (
        <button key={t.id} onClick={() => onSelect && onSelect(t.id)} style={{
          flex:"0 0 auto",height:30,padding:"0 12px",
          display:"flex",alignItems:"center",gap:6,
          background: active ? "var(--on-bg)" : "transparent",
          color: active ? "var(--bg)" : "var(--on-surface-muted)",
          border: active ? "0" : "1px solid var(--border-subtle)",
          borderRadius:999,
          fontFamily:"var(--font-mono)",fontSize:12,fontWeight:500,
          cursor:"pointer",whiteSpace:"nowrap",
          letterSpacing:"-0.01em",
        }}>
          <span style={{
            width:6,height:6,borderRadius:99,
            background: active ? "var(--bg)" : t.color
          }}/>
          {t.label}
        </button>
      );
    })}
    {showAdd && (
      <button style={{
        flex:"0 0 auto",height:30,width:30,
        display:"inline-flex",alignItems:"center",justifyContent:"center",
        background:"transparent",color:"var(--on-surface-muted)",
        border:"1px dashed var(--border)",borderRadius:999,cursor:"pointer",
      }}>
        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M12 5v14M5 12h14"/></svg>
      </button>
    )}
  </div>
);

/* ===================== DAY SECTION HEADER ===================== */
const SectionHeader = ({ label, count }) => (
  <div style={{
    display:"flex",alignItems:"baseline",justifyContent:"space-between",
    padding:"22px 20px 10px",
  }}>
    <div className="t-headline-sm" style={{color:"var(--on-bg)"}}>{label}</div>
    {count != null && (
      <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",letterSpacing:".05em"}}>{count} ITEMS</div>
    )}
  </div>
);

/* ===================== CARD CHROME ===================== */
const CardShell = ({ children, source, fresh, read, onLongPress }) => (
  <div style={{
    margin:"0 14px 8px",
    padding:"14px 16px 14px 18px",
    background:"var(--surface)",
    borderRadius: 16,
    border:"1px solid var(--border-subtle)",
    position:"relative",
    opacity: read ? 0.65 : 1,
    transition:"opacity 200ms",
    overflow:"hidden",
  }}>
    {fresh && <div style={{
      position:"absolute",left:0,top:14,bottom:14,width:3,
      borderRadius:"0 3px 3px 0",
      background:"var(--brand-primary)",
    }}/>}
    {children}
  </div>
);

const SourceTag = ({ source, label, color, time }) => (
  <div style={{display:"flex",alignItems:"center",gap:8,marginBottom:8}}>
    <span style={{
      width:18,height:18,borderRadius:5,
      background: color,
      display:"inline-flex",alignItems:"center",justifyContent:"center",color:"#fff"
    }}>
      <SourceIcon source={source} size={12}/>
    </span>
    <span style={{fontFamily:"var(--font-mono)",fontSize:11,fontWeight:600,color:"var(--on-bg)",letterSpacing:".02em"}}>{label}</span>
    <span style={{width:3,height:3,borderRadius:99,background:"var(--on-surface-faint)"}}/>
    <span style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)"}}>{time}</span>
  </div>
);

/* Card actions (kebab + bookmark) — small, ghosted */
const CardActions = ({ bookmarked, onBookmark, onMore, compact }) => (
  <div style={{display:"flex",gap:2,marginLeft:"auto"}}>
    <button onClick={onBookmark} aria-label="Bookmark" style={{
      width:30,height:30,border:0,background:"transparent",cursor:"pointer",
      color: bookmarked ? "var(--brand-primary)" : "var(--on-surface-muted)",
      display:"inline-flex",alignItems:"center",justifyContent:"center",borderRadius:8,
    }}>
      <Icon name={bookmarked ? "bookmark-fill" : "bookmark"} size={18}/>
    </button>
    <button aria-label="More" style={{
      width:30,height:30,border:0,background:"transparent",cursor:"pointer",
      color:"var(--on-surface-muted)",
      display:"inline-flex",alignItems:"center",justifyContent:"center",borderRadius:8,
    }}>
      <Icon name="more" size={18}/>
    </button>
  </div>
);

/* ===================== ARTICLE CARDS ===================== */

/* Generic article (HN, devto, hashnode, lobsters, freecodecamp, medium, indiehackers, hackernoon, reddit) */
const ArticleCard = ({ a, bookmarked }) => {
  const src = SOURCES.find(s => s.id === a.source);
  const meta = renderMeta(a);
  return (
    <CardShell source={a.source} fresh={a.fresh} read={a.read}>
      <SourceTag source={a.source} label={src.label} color={src.color} time={a.time}/>
      <div className="t-title-lg" style={{color:"var(--on-bg)",letterSpacing:"-0.01em"}}>{a.title}</div>
      {a.tagline && <div className="t-body-md" style={{color:"var(--on-surface-muted)",marginTop:4}}>{a.tagline}</div>}
      {a.tags && (
        <div style={{display:"flex",flexWrap:"wrap",gap:6,marginTop:10}}>
          {a.tags.map(t => (
            <span key={t} style={{
              fontFamily:"var(--font-mono)",fontSize:11,
              color:"var(--on-surface-muted)",
              padding:"2px 7px",borderRadius:6,
              background:"var(--surface-variant)",
            }}>#{t}</span>
          ))}
        </div>
      )}
      <div style={{display:"flex",alignItems:"center",gap:14,marginTop:12}}>
        {meta}
        <CardActions bookmarked={bookmarked}/>
      </div>
    </CardShell>
  );
};

const renderMeta = (a) => {
  const styles = {fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface-muted)",display:"inline-flex",alignItems:"center",gap:5};
  switch (a.source) {
    case "hackernews":   return <><span style={styles}><span style={{width:6,height:6,borderRadius:99,background:"#ff6600"}}/>{a.points}</span><span style={styles}><Icon name="comment" size={13}/>{a.comments}</span></>;
    case "reddit":       return <><span style={styles}><span style={{width:6,height:6,borderRadius:99,background:"#ff4500"}}/>{a.score}</span><span style={styles}><Icon name="comment" size={13}/>{a.comments}</span><span style={styles}>{a.subreddit}</span></>;
    case "lobsters":     return <><span style={styles}><Icon name="arrow-up" size={13}/>{a.score}</span><span style={styles}><Icon name="comment" size={13}/>{a.comments}</span></>;
    case "devto":        return <><span style={styles}><Icon name="heart" size={13}/>{a.reactions}</span><span style={styles}><Icon name="comment" size={13}/>{a.comments}</span></>;
    case "hashnode":     return <><span style={styles}><Icon name="heart" size={13}/>{a.reactions}</span><span style={styles}><Icon name="comment" size={13}/>{a.comments}</span></>;
    case "medium":       return <><span style={styles}>👏 {a.claps}</span><span style={styles}><Icon name="comment" size={13}/>{a.comments}</span></>;
    case "indiehackers": return <><span style={styles}><span style={{width:6,height:6,borderRadius:99,background:"#0e2439"}}/>{a.score}</span><span style={styles}><Icon name="comment" size={13}/>{a.comments}</span></>;
    case "freecodecamp":
    case "hackernoon":   return null;
    default: return null;
  }
};

/* GitHub Repo Card */
const RepoCard = ({ a, bookmarked }) => (
  <CardShell source="github" fresh={a.fresh} read={a.read}>
    <SourceTag source="github" label="GitHub" color="#181717" time={a.time}/>
    <div style={{display:"flex",alignItems:"baseline",gap:0}}>
      <span style={{
        fontFamily:"var(--font-mono)",fontSize:17,fontWeight:600,
        color:"var(--on-surface-muted)",letterSpacing:"-.01em"
      }}>{a.owner}<span style={{margin:"0 2px"}}>/</span></span>
      <span style={{
        fontFamily:"var(--font-mono)",fontSize:17,fontWeight:700,
        color:"var(--brand-primary)",letterSpacing:"-.01em"
      }}>{a.repo}</span>
    </div>
    <div className="t-body-md" style={{color:"var(--on-surface-muted)",marginTop:6}}>{a.description}</div>
    <div style={{display:"flex",alignItems:"center",gap:14,marginTop:12}}>
      <span style={{display:"inline-flex",alignItems:"center",gap:6,fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-bg)"}}>
        <span style={{width:8,height:8,borderRadius:99,background:a.langColor}}/>
        {a.language}
      </span>
      <span style={{display:"inline-flex",alignItems:"center",gap:5,fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface-muted)"}}><Icon name="star" size={13}/>{a.stars}</span>
      <span style={{display:"inline-flex",alignItems:"center",gap:5,fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface-muted)"}}><Icon name="fork" size={13}/>{a.forks}</span>
      <CardActions bookmarked={bookmarked}/>
    </div>
  </CardShell>
);

/* ProductHunt Launch Card */
const LaunchCard = ({ a, bookmarked }) => (
  <CardShell source="producthunt" fresh={a.fresh}>
    <SourceTag source="producthunt" label="Product Hunt" color="#da552f" time={a.time}/>
    <div style={{display:"flex",gap:12}}>
      <div style={{
        width:60,height:60,borderRadius:14,
        background:"linear-gradient(135deg, #ff7a59, #da552f)",
        color:"#fff",display:"inline-flex",alignItems:"center",justifyContent:"center",
        fontFamily:"var(--font-sans)",fontWeight:700,fontSize:28,
        flexShrink:0,
      }}>{a.thumb}</div>
      <div style={{flex:1,minWidth:0}}>
        <div className="t-title-lg" style={{color:"var(--on-bg)"}}>{a.title}</div>
        <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:2}}>{a.tagline}</div>
      </div>
      <div style={{
        width:48,padding:"4px 0",
        display:"flex",flexDirection:"column",alignItems:"center",justifyContent:"center",
        border:"1px solid var(--border)",
        borderRadius:12,flexShrink:0,
      }}>
        <Icon name="arrow-up" size={16} color="var(--brand-primary)"/>
        <div style={{fontFamily:"var(--font-mono)",fontWeight:700,fontSize:14,color:"var(--on-bg)",marginTop:1}}>{a.upvotes}</div>
      </div>
    </div>
    <div style={{display:"flex",alignItems:"center",gap:14,marginTop:12,paddingTop:10,borderTop:"1px solid var(--border-subtle)"}}>
      <span style={{display:"inline-flex",alignItems:"center",gap:5,fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface-muted)"}}><Icon name="comment" size={13}/>{a.comments}</span>
      <CardActions bookmarked={bookmarked}/>
    </div>
  </CardShell>
);

/* Conference Card */
const ConferenceCard = ({ a, bookmarked }) => (
  <CardShell source="conferences" fresh={a.fresh}>
    <SourceTag source="conferences" label="Conferences" color="#6e56cf" time={a.time}/>
    <div style={{display:"flex",gap:14}}>
      <div style={{
        width:62,flexShrink:0,
        border:"1px solid var(--border)",borderRadius:12,
        overflow:"hidden",textAlign:"center",
      }}>
        <div style={{padding:"3px 0",fontFamily:"var(--font-mono)",fontSize:10,fontWeight:700,letterSpacing:".1em",background:"#6e56cf",color:"#fff"}}>{a.month}</div>
        <div style={{padding:"6px 0 8px",fontFamily:"var(--font-sans)",fontSize:24,fontWeight:600,color:"var(--on-bg)"}}>{a.day}</div>
      </div>
      <div style={{flex:1,minWidth:0}}>
        <div className="t-title-lg" style={{color:"var(--on-bg)"}}>{a.title}</div>
        <div style={{display:"flex",alignItems:"center",gap:6,marginTop:4,fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface-muted)"}}>
          {a.online ? <Icon name="globe" size={13}/> : <Icon name="calendar" size={13}/>}
          {a.location}
        </div>
        <div style={{display:"flex",alignItems:"center",gap:6,marginTop:3,fontFamily:"var(--font-mono)",fontSize:12,color:"var(--on-surface-muted)"}}>
          <Icon name="calendar" size={13}/>{a.dateLabel}
        </div>
      </div>
    </div>
    {a.tags && (
      <div style={{display:"flex",flexWrap:"wrap",gap:6,marginTop:10}}>
        {a.tags.map(t => (
          <span key={t} style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",padding:"2px 7px",borderRadius:6,background:"var(--surface-variant)"}}>#{t}</span>
        ))}
      </div>
    )}
    <div style={{display:"flex",alignItems:"center",justifyContent:"flex-end",marginTop:6}}>
      <CardActions bookmarked={bookmarked}/>
    </div>
  </CardShell>
);

/* ===================== DISPATCH ===================== */
const FeedCard = ({ a, bookmarked }) => {
  switch (a.source) {
    case "github":      return <RepoCard a={a} bookmarked={bookmarked}/>;
    case "producthunt": return <LaunchCard a={a} bookmarked={bookmarked}/>;
    case "conferences": return <ConferenceCard a={a} bookmarked={bookmarked}/>;
    default:            return <ArticleCard a={a} bookmarked={bookmarked}/>;
  }
};

/* ===================== TAB BAR ===================== */
const TabBar = ({ active = "today" }) => (
  <div className="tab-bar">
    <button className={"tab-item" + (active==="today" ? " active":"")}>
      <span className="ico"><Icon name="today" size={22}/></span>
      Today
    </button>
    <button className={"tab-item" + (active==="bookmarks" ? " active":"")}>
      <span className="ico"><Icon name="bookmark" size={22}/></span>
      Saved
    </button>
    <button className={"tab-item" + (active==="settings" ? " active":"")}>
      <span className="ico"><Icon name="settings" size={22}/></span>
      Settings
    </button>
  </div>
);

/* ===================== EXPORT ===================== */
Object.assign(window, {
  StatusBar, HomeIndicator, AppBar, SourceRail, TopicChipStrip, SectionHeader,
  CardShell, SourceTag, CardActions, ArticleCard, RepoCard, LaunchCard, ConferenceCard, FeedCard, TabBar
});
