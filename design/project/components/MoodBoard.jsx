/* MoodBoard.jsx — Mood board + visual direction defense. */

const MoodCard = ({ tag, title, why, swatches, children }) => (
  <div style={{
    background:"var(--surface)",
    border:"1px solid var(--border-subtle)",
    borderRadius:18,
    overflow:"hidden",
    display:"flex",flexDirection:"column",
    height:"100%",
  }}>
    <div style={{aspectRatio:"4/3",position:"relative",overflow:"hidden",background:"var(--surface-variant)"}}>
      {children}
    </div>
    <div style={{padding:"16px 18px 18px"}}>
      <div style={{display:"flex",alignItems:"center",gap:8,marginBottom:8}}>
        <span style={{fontFamily:"var(--font-mono)",fontSize:10,fontWeight:600,letterSpacing:".1em",padding:"3px 7px",borderRadius:5,background:"var(--surface-variant)",color:"var(--brand-primary)"}}>{tag}</span>
        {swatches && swatches.map((c,i) => <span key={i} style={{width:14,height:14,borderRadius:4,background:c,border:"1px solid rgba(0,0,0,.06)"}}/>)}
      </div>
      <div className="t-title-lg" style={{color:"var(--on-bg)"}}>{title}</div>
      <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:6,lineHeight:1.5}}>{why}</div>
    </div>
  </div>
);

const MoodBoard = () => (
  <div style={{padding:"40px 56px 60px",maxWidth:1640,margin:"0 auto"}}>
    <div style={{display:"flex",justifyContent:"space-between",alignItems:"flex-end",marginBottom:36}}>
      <div>
        <div style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".15em",color:"var(--brand-primary)",marginBottom:8}}>01 · MOOD BOARD</div>
        <div className="t-display-md" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>Modern, technical, quietly premium.</div>
        <div className="t-body-lg" style={{color:"var(--on-surface-muted)",marginTop:14,maxWidth:720}}>
          A developer-first reader. Calm surfaces, monospace where it matters, one assertive accent. Every decision below maps directly to a token, component, or screen later in the system.
        </div>
      </div>
    </div>

    <div style={{display:"grid",gridTemplateColumns:"repeat(4, 1fr)",gap:16,marginBottom:32}}>

      {/* 01 Linear */}
      <MoodCard tag="01 · CLARITY" title="Linear" swatches={["#5e6ad2","#f7f8f8","#0a0a0a"]}
        why="Calm system fonts, generous whitespace, subtle elevation. A model for letting content lead while chrome disappears.">
        <div style={{position:"absolute",inset:0,padding:24,background:"linear-gradient(180deg,#f7f8f8,#fff)",display:"flex",flexDirection:"column",gap:8}}>
          <div style={{display:"flex",alignItems:"center",gap:6,marginBottom:8}}>
            <span style={{width:18,height:18,borderRadius:5,background:"#5e6ad2"}}/>
            <span style={{fontFamily:"var(--font-sans)",fontWeight:600,fontSize:13,color:"#0a0a0a"}}>Linear</span>
          </div>
          {[1,2,3,4,5].map(i => (
            <div key={i} style={{display:"flex",alignItems:"center",gap:8,padding:"6px 8px",borderRadius:6,background:i===2?"#eef0fc":"transparent"}}>
              <span style={{width:8,height:8,borderRadius:99,background:["#5e6ad2","#f59e0b","#10b981","#ef4444","#a78bfa"][i-1]}}/>
              <div style={{height:6,flex:1,borderRadius:3,background:"#e5e7eb"}}/>
              <span style={{fontFamily:"var(--font-mono)",fontSize:9,color:"#9ca3af"}}>EN-{1200+i}</span>
            </div>
          ))}
        </div>
      </MoodCard>

      {/* 02 Reeder */}
      <MoodCard tag="02 · DENSITY" title="Reeder / NetNewsWire" swatches={["#ff6b35","#1c1c1e","#f2f2f7"]}
        why="Information-dense lists where every pixel earns its keep. The rhythm we want for the Today feed — tight rows, no decoration debt.">
        <div style={{position:"absolute",inset:0,padding:18,background:"#1c1c1e",display:"flex",flexDirection:"column",gap:6}}>
          {["Hacker News","The Verge","Ars Technica","Daring Fireball","404 Media"].map((s,i) => (
            <div key={s} style={{display:"flex",alignItems:"flex-start",gap:8,padding:"6px 8px",borderRadius:4,background:i===1?"rgba(255,107,53,.18)":"transparent"}}>
              <span style={{width:6,height:6,borderRadius:99,background:"#ff6b35",marginTop:5,opacity: i<2?1:.3}}/>
              <div style={{flex:1}}>
                <div style={{height:5,width: 80-i*5+"%",borderRadius:3,background:"#e5e5e7",marginBottom:4}}/>
                <div style={{height:4,width: 60-i*4+"%",borderRadius:3,background:"#48484a"}}/>
              </div>
              <span style={{fontFamily:"var(--font-mono)",fontSize:8,color:"#8e8e93"}}>{i+1}h</span>
            </div>
          ))}
        </div>
      </MoodCard>

      {/* 03 Raycast */}
      <MoodCard tag="03 · INTENT" title="Raycast" swatches={["#ff6363","#0a0a0a","#fafafa"]}
        why="Small, confident animations and a sense of intent on every keystroke. Models our motion floor: brief, purposeful, never decorative.">
        <div style={{position:"absolute",inset:0,padding:0,background:"#0a0a0a",display:"flex",flexDirection:"column"}}>
          <div style={{padding:"16px 18px",display:"flex",alignItems:"center",gap:10,borderBottom:"1px solid #1f1f1f"}}>
            <div style={{width:18,height:18,borderRadius:5,background:"#ff6363"}}/>
            <div style={{height:8,flex:1,borderRadius:4,background:"#1f1f1f"}}/>
            <span style={{fontFamily:"var(--font-mono)",fontSize:9,color:"#666",letterSpacing:".05em"}}>⌘K</span>
          </div>
          <div style={{flex:1,padding:"10px 14px",display:"flex",flexDirection:"column",gap:4}}>
            {["Open feed","Toggle theme","Bookmark current","Switch source","Search saved"].map((l,i) => (
              <div key={l} style={{display:"flex",alignItems:"center",gap:8,padding:"6px 10px",borderRadius:6,background:i===0?"#ff6363":"transparent"}}>
                <span style={{width:10,height:10,borderRadius:2,background:i===0?"#fff":"#48484a"}}/>
                <span style={{fontFamily:"var(--font-sans)",fontSize:11,color:i===0?"#fff":"#8e8e93",fontWeight:500}}>{l}</span>
              </div>
            ))}
          </div>
        </div>
      </MoodCard>

      {/* 04 Vercel */}
      <MoodCard tag="04 · MINIMALISM" title="Vercel dashboard" swatches={["#0a0a0a","#fafafa","#0070f3"]}
        why="One strong accent, generous neutral surface, monospace receipts. We adopt the Geist pair and the discipline of one accent + one surface tone.">
        <div style={{position:"absolute",inset:0,padding:24,background:"#fafafa",display:"flex",flexDirection:"column",gap:14}}>
          <div style={{display:"flex",alignItems:"center",gap:6}}>
            <span style={{width:14,height:14,background:"#0a0a0a",transform:"rotate(45deg)"}}/>
            <span style={{fontFamily:"var(--font-sans)",fontWeight:600,fontSize:13,color:"#0a0a0a",letterSpacing:"-.02em"}}>vercel</span>
          </div>
          <div style={{display:"flex",gap:10,marginTop:6}}>
            <div style={{flex:1,padding:12,borderRadius:8,background:"#fff",border:"1px solid #eaeaea"}}>
              <div style={{fontFamily:"var(--font-mono)",fontSize:8,color:"#666",letterSpacing:".05em"}}>DEPLOYS</div>
              <div style={{fontFamily:"var(--font-sans)",fontWeight:600,fontSize:18,color:"#0a0a0a",marginTop:4,letterSpacing:"-.02em"}}>1,247</div>
            </div>
            <div style={{flex:1,padding:12,borderRadius:8,background:"#fff",border:"1px solid #eaeaea"}}>
              <div style={{fontFamily:"var(--font-mono)",fontSize:8,color:"#666",letterSpacing:".05em"}}>EDGE REQ</div>
              <div style={{fontFamily:"var(--font-sans)",fontWeight:600,fontSize:18,color:"#0a0a0a",marginTop:4,letterSpacing:"-.02em"}}>32M</div>
            </div>
          </div>
          <div style={{flex:1,padding:12,borderRadius:8,background:"#fff",border:"1px solid #eaeaea"}}>
            <div style={{fontFamily:"var(--font-mono)",fontSize:9,color:"#666"}}>● Live</div>
            <div style={{display:"flex",alignItems:"flex-end",gap:2,height:34,marginTop:8}}>
              {[8,14,12,20,16,24,18,28,22,30,26,16].map((h,i) => <div key={i} style={{flex:1,height:h,background:"#0a0a0a",borderRadius:1}}/>)}
            </div>
          </div>
        </div>
      </MoodCard>

      {/* 05 Apollo */}
      <MoodCard tag="05 · CARDS" title="Apollo for Reddit" swatches={["#ff4500","#1c1c1e","#fafafa"]}
        why="Each subreddit feels like itself. Validates per-source card identities — same shell, different soul. We borrow the rhythm; we don't borrow the chrome.">
        <div style={{position:"absolute",inset:0,padding:14,background:"#0a0a0a",display:"flex",flexDirection:"column",gap:6}}>
          {[{c:"#ff4500",t:"r/programming"},{c:"#a97bff",t:"r/kotlin"},{c:"#dea584",t:"r/rust"}].map((s,i) => (
            <div key={s.t} style={{padding:"10px 12px",borderRadius:8,background:"#1c1c1e",border:`1px solid ${s.c}30`,borderLeft:`3px solid ${s.c}`}}>
              <div style={{display:"flex",alignItems:"center",gap:6,marginBottom:5}}>
                <span style={{width:12,height:12,borderRadius:99,background:s.c}}/>
                <span style={{fontFamily:"var(--font-mono)",fontSize:9,color:"#8e8e93"}}>{s.t}</span>
              </div>
              <div style={{height:6,width:90-i*8+"%",borderRadius:3,background:"#e5e5e7"}}/>
            </div>
          ))}
        </div>
      </MoodCard>

      {/* 06 Type */}
      <MoodCard tag="06 · TYPE PAIR" title="Geist Sans + Geist Mono" swatches={["#0a0a0a","#fafafa","#7bffaa"]}
        why="The Vercel pair: a humanist sans with real weights for hierarchy, a calibrated mono for repos, code, conference dates. Disciplined alternative to Inter.">
        <div style={{position:"absolute",inset:0,padding:"24px 26px",background:"var(--surface)",display:"flex",flexDirection:"column",justifyContent:"space-between"}}>
          <div>
            <div style={{fontFamily:"var(--font-sans)",fontSize:48,fontWeight:600,letterSpacing:"-.045em",color:"var(--on-bg)",lineHeight:1}}>Aa</div>
            <div style={{fontFamily:"var(--font-sans)",fontSize:11,color:"var(--on-surface-muted)",marginTop:6,letterSpacing:".05em"}}>GEIST SANS · 400 / 500 / 600 / 700</div>
          </div>
          <div style={{height:1,background:"var(--border-subtle)"}}/>
          <div>
            <div style={{fontFamily:"var(--font-mono)",fontSize:32,fontWeight:600,letterSpacing:"-.04em",color:"var(--brand-primary)"}}>{`{ }`}</div>
            <div style={{fontFamily:"var(--font-mono)",fontSize:10,color:"var(--on-surface-muted)",marginTop:4,letterSpacing:".05em"}}>GEIST MONO · 500 / 600</div>
          </div>
        </div>
      </MoodCard>

      {/* 07 Accent */}
      <MoodCard tag="07 · ACCENT" title="Terminal green — oklch(78% .19 145)" swatches={["#7bffaa","#0a0a0a","#fafafa"]}
        why="Pivots from generic GitHub-blue. Reads as cursor / signal / fresh — the same language a developer's terminal speaks. Doesn't collide with any source brand.">
        <div style={{position:"absolute",inset:0,background:"var(--neutral-950)",padding:24,display:"flex",flexDirection:"column",gap:8,fontFamily:"var(--font-mono)"}}>
          <div style={{fontSize:11,color:"#6b7280",letterSpacing:".05em"}}>$ hackertab today</div>
          <div style={{fontSize:11,color:"#fafafa"}}>fetching <span style={{color:"oklch(78% .19 145)"}}>6 sources</span>…</div>
          <div style={{fontSize:11,color:"oklch(78% .19 145)"}}>✓ 43 new items</div>
          <div style={{fontSize:11,color:"#6b7280"}}>—</div>
          <div style={{fontSize:11,color:"#fafafa"}}>github.com/JetBrains/<span style={{color:"oklch(78% .19 145)"}}>compose-multiplatform</span></div>
          <div style={{fontSize:11,color:"#6b7280"}}>2h ago · 17.2k ★</div>
          <div style={{flex:1}}/>
          <div style={{display:"flex",alignItems:"center",gap:6}}>
            <span style={{width:10,height:18,background:"oklch(78% .19 145)",animation:"blink 1s steps(2) infinite"}}/>
          </div>
        </div>
      </MoodCard>

      {/* 08 Surfaces */}
      <MoodCard tag="08 · SURFACES" title="Tonal layers, no borders by default" swatches={["#fafafa","#f4f4f4","#eaeaea"]}
        why="Stop using 1dp 30%-opacity borders as the default container. Lift containers with tonal surface tints + true elevation. Borders only when they communicate boundary.">
        <div style={{position:"absolute",inset:0,background:"var(--neutral-50)",padding:30,display:"flex",alignItems:"center",justifyContent:"center"}}>
          <div style={{position:"relative",width:"100%",height:"100%"}}>
            <div style={{position:"absolute",inset:"0 14% 14% 0",borderRadius:14,background:"var(--neutral-100)"}}/>
            <div style={{position:"absolute",inset:"7% 7% 7% 7%",borderRadius:14,background:"var(--neutral-50)",border:"1px solid var(--neutral-200)",boxShadow:"0 4px 16px rgba(0,0,0,.04)"}}/>
            <div style={{position:"absolute",inset:"14% 0 0 14%",borderRadius:14,background:"#fff",boxShadow:"0 8px 28px rgba(0,0,0,.08)"}}/>
          </div>
        </div>
      </MoodCard>

      {/* 09 Dark contrast */}
      <MoodCard tag="09 · DARK MODE" title="Real night, not navy-twilight" swatches={["#040504","#0a0c0a","#161816"]}
        why="Today's dark is ChineseBlack — too purple, too soft. We push to true black with a 1% green tint and a 4-stop tonal ramp. Works on OLED; doesn't mute the accent.">
        <div style={{position:"absolute",inset:0,background:"oklch(7% .005 145)",padding:18,display:"flex",flexDirection:"column",gap:6}}>
          <div style={{padding:14,borderRadius:14,background:"oklch(13% .006 145)",border:"1px solid oklch(22% .008 145)"}}>
            <div style={{display:"flex",alignItems:"center",gap:6,marginBottom:8}}>
              <span style={{width:14,height:14,borderRadius:4,background:"#181717"}}/>
              <span style={{fontFamily:"var(--font-mono)",fontSize:9,color:"oklch(78% .19 145)"}}>GitHub · 2h</span>
            </div>
            <div style={{height:6,width:"75%",borderRadius:3,background:"#fafafa",marginBottom:4}}/>
            <div style={{height:5,width:"55%",borderRadius:3,background:"oklch(50% .01 95)"}}/>
          </div>
          <div style={{padding:14,borderRadius:14,background:"oklch(13% .006 145)",border:"1px solid oklch(22% .008 145)"}}>
            <div style={{display:"flex",alignItems:"center",gap:6,marginBottom:8}}>
              <span style={{width:14,height:14,borderRadius:4,background:"#ff6600"}}/>
              <span style={{fontFamily:"var(--font-mono)",fontSize:9,color:"oklch(78% .19 145)"}}>Hacker News · 1h</span>
            </div>
            <div style={{height:6,width:"82%",borderRadius:3,background:"#fafafa",marginBottom:4}}/>
            <div style={{height:5,width:"45%",borderRadius:3,background:"oklch(50% .01 95)"}}/>
          </div>
        </div>
      </MoodCard>

      {/* 10 Motion */}
      <MoodCard tag="10 · MOTION" title="Spring physics, ≤300ms" swatches={["#7bffaa"]}
        why="Source pill springs between items. Cards scale 0.98 on press. Bookmark pulses. Nothing decorative, nothing over 500ms. Honors prefers-reduced-motion.">
        <div style={{position:"absolute",inset:0,background:"var(--neutral-50)",padding:30,display:"flex",flexDirection:"column",justifyContent:"center",gap:14}}>
          <div style={{display:"flex",gap:6,height:34}}>
            {["All","GitHub","HN","Reddit"].map((s,i) => (
              <div key={s} style={{padding:"0 14px",height:34,borderRadius:99,display:"flex",alignItems:"center",fontFamily:"var(--font-sans)",fontSize:11,fontWeight:600,
                background: i===1 ? "oklch(78% .19 145)" : "transparent",
                color: i===1 ? "var(--neutral-950)" : "var(--neutral-700)",
                border: i===1 ? "0" : "1px solid var(--neutral-200)",
                boxShadow: i===1 ? "0 4px 16px rgba(123,255,170,.3)" : "none",
              }}>{s}</div>
            ))}
          </div>
          <svg viewBox="0 0 200 30" style={{width:"100%",height:30}}>
            <path d="M 0,28 Q 30,28 50,12 T 100,4 T 150,4 T 200,4" stroke="oklch(78% .19 145)" strokeWidth="2" fill="none"/>
            <circle cx="50" cy="12" r="3" fill="oklch(78% .19 145)"/>
            <circle cx="100" cy="4" r="3" fill="oklch(78% .19 145)"/>
            <circle cx="150" cy="4" r="3" fill="oklch(78% .19 145)"/>
            <circle cx="200" cy="4" r="3" fill="oklch(78% .19 145)"/>
          </svg>
          <div style={{fontFamily:"var(--font-mono)",fontSize:10,color:"var(--on-surface-muted)",letterSpacing:".05em"}}>SPRING(380, 29) · 240ms</div>
        </div>
      </MoodCard>
    </div>

    {/* Decisions strip */}
    <div style={{display:"grid",gridTemplateColumns:"repeat(5, 1fr)",gap:14,padding:22,borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)"}}>
      {[
        {h:"Brand accent", v:"Terminal green", k:"oklch(78% .19 145)", d:"Pivot from #0366D6 — feels signal, not corporate."},
        {h:"Type pair", v:"Geist Sans + Mono", k:"variable, 400–700", d:"Replace Nunito. Sharper, more current, code-friendly."},
        {h:"Navigation", v:"Bottom tab bar", k:"Today · Saved · Settings", d:"Drawer is gone. NavRail on tablets."},
        {h:"Source switcher", v:"Persistent SourceRail", k:"horizontal pills, sticky", d:"Replaces the dropdown title — always one tap."},
        {h:"Motion", v:"Spring physics", k:"≤300ms, no loops", d:"Brief, physical, never decorative."},
      ].map(d => (
        <div key={d.h}>
          <div style={{fontFamily:"var(--font-mono)",fontSize:10,letterSpacing:".1em",color:"var(--on-surface-muted)",marginBottom:8}}>{d.h.toUpperCase()}</div>
          <div className="t-title-md" style={{color:"var(--on-bg)"}}>{d.v}</div>
          <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)",marginTop:3}}>{d.k}</div>
          <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:6,lineHeight:1.45}}>{d.d}</div>
        </div>
      ))}
    </div>
  </div>
);

window.MoodBoard = MoodBoard;
