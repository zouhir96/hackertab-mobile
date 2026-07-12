/* Tokens.jsx — Color, Type, Spacing, Radius, Elevation, Motion (light + dark). */

const Section = ({ num, title, kicker, children }) => (
  <div style={{marginBottom:48}}>
    <div style={{display:"flex",alignItems:"baseline",gap:14,marginBottom:18}}>
      <span style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".15em",color:"var(--brand-primary)"}}>{num}</span>
      <div className="t-headline-md" style={{color:"var(--on-bg)",letterSpacing:"-.02em"}}>{title}</div>
      {kicker && <div className="t-body-sm" style={{color:"var(--on-surface-muted)"}}>{kicker}</div>}
    </div>
    {children}
  </div>
);

const SwatchTile = ({ name, token, value, fg = "auto", note }) => {
  const text = fg === "auto" ? "var(--on-bg)" : fg;
  return (
    <div style={{borderRadius:14,overflow:"hidden",border:"1px solid var(--border-subtle)",background:"var(--surface)"}}>
      <div style={{height:80,background: value, position:"relative"}}>
        <span style={{position:"absolute",right:10,top:10,fontFamily:"var(--font-mono)",fontSize:10,padding:"2px 6px",borderRadius:4,background:"rgba(0,0,0,.5)",color:"#fff"}}>{value}</span>
      </div>
      <div style={{padding:"10px 12px"}}>
        <div className="t-title-sm" style={{color:"var(--on-bg)"}}>{name}</div>
        <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)",marginTop:2}}>{token}</div>
        {note && <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:4}}>{note}</div>}
      </div>
    </div>
  );
};

const ThemeBlock = ({ mode = "light" }) => {
  const tokens = mode === "light" ? [
    {n:"Background", t:"colorBackground", v:"oklch(97% .004 95)"},
    {n:"BG Elevated", t:"colorBackgroundElevated", v:"oklch(99% .003 95)"},
    {n:"Surface", t:"colorSurface", v:"oklch(99% .003 95)"},
    {n:"Surface Variant", t:"colorSurfaceVariant", v:"oklch(94% .005 95)"},
    {n:"On Background", t:"colorOnBackground", v:"oklch(14% .006 145)"},
    {n:"On Surface Muted", t:"colorOnSurfaceMuted", v:"oklch(50% .010 95)"},
    {n:"Border", t:"colorBorder", v:"oklch(89% .006 95)"},
    {n:"Border Subtle", t:"colorBorderSubtle", v:"oklch(94% .005 95)"},
  ] : [
    {n:"Background", t:"colorBackground", v:"oklch(9% .005 145)"},
    {n:"BG Elevated", t:"colorBackgroundElevated", v:"oklch(14% .006 145)"},
    {n:"Surface", t:"colorSurface", v:"oklch(13% .006 145)"},
    {n:"Surface Variant", t:"colorSurfaceVariant", v:"oklch(17% .007 145)"},
    {n:"On Background", t:"colorOnBackground", v:"oklch(97% .004 95)"},
    {n:"On Surface Muted", t:"colorOnSurfaceMuted", v:"oklch(65% .010 95)"},
    {n:"Border", t:"colorBorder", v:"oklch(22% .008 145)"},
    {n:"Border Subtle", t:"colorBorderSubtle", v:"oklch(17% .007 145)"},
  ];
  return (
    <div className={"theme-" + mode} style={{padding:"22px",borderRadius:18,background:"var(--bg)",border:"1px solid var(--border)"}}>
      <div style={{display:"flex",alignItems:"center",gap:8,marginBottom:14}}>
        <div style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".1em",color:"var(--brand-primary)"}}>{mode === "light" ? "LIGHT" : "DARK"}</div>
        <div className="t-title-md" style={{color:"var(--on-bg)"}}>{mode === "light" ? "Day mode — warm paper" : "Night mode — true black"}</div>
      </div>
      <div style={{display:"grid",gridTemplateColumns:"repeat(4, 1fr)",gap:10}}>
        {tokens.map(t => <SwatchTile key={t.t} name={t.n} token={t.t} value={t.v}/>)}
      </div>
    </div>
  );
};

const Tokens = () => {
  const brand = [
    {n:"Brand 50",  t:"brand-50",  v:"oklch(96% .04 145)"},
    {n:"Brand 100", t:"brand-100", v:"oklch(92% .08 145)"},
    {n:"Brand 200", t:"brand-200", v:"oklch(88% .12 145)"},
    {n:"Brand 300", t:"brand-300", v:"oklch(83% .16 145)"},
    {n:"Brand 400", t:"brand-400", v:"oklch(78% .19 145)", note:"Primary"},
    {n:"Brand 500", t:"brand-500", v:"oklch(72% .19 145)", note:"Pressed"},
    {n:"Brand 600", t:"brand-600", v:"oklch(60% .17 145)"},
    {n:"Brand 700", t:"brand-700", v:"oklch(45% .14 145)"},
    {n:"Brand 800", t:"brand-800", v:"oklch(30% .10 145)"},
    {n:"Brand 900", t:"brand-900", v:"oklch(20% .06 145)"},
  ];
  const semantic = [
    {n:"Success", t:"colorAccentSuccess", v:"oklch(72% .18 150)"},
    {n:"Warning", t:"colorAccentWarning", v:"oklch(78% .15 75)"},
    {n:"Error",   t:"colorAccentError",   v:"oklch(65% .20 25)"},
    {n:"Info",    t:"colorAccentInfo",    v:"oklch(70% .13 240)"},
    {n:"Positive (HN/Reddit)", t:"colorContentPositive", v:"oklch(72% .18 150)"},
  ];
  const sources = [
    {n:"GitHub",       t:"colorSourceGitHub",       v:"#181717"},
    {n:"Hacker News",  t:"colorSourceHackerNews",   v:"#ff6600"},
    {n:"Reddit",       t:"colorSourceReddit",       v:"#ff4500"},
    {n:"Product Hunt", t:"colorSourceProductHunt",  v:"#da552f"},
    {n:"Dev.to",       t:"colorSourceDevTo",        v:"#0a0a0a"},
    {n:"Lobsters",     t:"colorSourceLobsters",     v:"#ac130d"},
    {n:"Hashnode",     t:"colorSourceHashnode",     v:"#2962ff"},
    {n:"freeCodeCamp", t:"colorSourceFreeCodeCamp", v:"#0a0a23"},
    {n:"Indie Hackers",t:"colorSourceIndieHackers", v:"#0e2439"},
    {n:"Medium",       t:"colorSourceMedium",       v:"#00ab6c"},
    {n:"HackerNoon",   t:"colorSourceHackerNoon",   v:"#00fe00"},
    {n:"Conferences",  t:"colorSourceConferences",  v:"#6e56cf"},
  ];
  const langs = [
    {n:"JavaScript",v:"#f7df1e"},{n:"TypeScript",v:"#3178c6"},{n:"Python",v:"#3572a5"},{n:"Rust",v:"#dea584"},
    {n:"Go",v:"#00add8"},{n:"Kotlin",v:"#a97bff"},{n:"Java",v:"#ed8b00"},{n:"Swift",v:"#f05138"},
    {n:"C++",v:"#f34b7d"},{n:"C#",v:"#178600"},{n:"Ruby",v:"#701516"},{n:"PHP",v:"#4f5d95"},
    {n:"Dart",v:"#00b4ab"},{n:"HTML",v:"#e34c26"},{n:"CSS",v:"#563d7c"},{n:"Shell",v:"#89e051"},
    {n:"Elixir",v:"#6e4a7e"},{n:"Haskell",v:"#5e5086"},{n:"Scala",v:"#c22d40"},{n:"Clojure",v:"#db5855"},
    {n:"Vue",v:"#41b883"},{n:"Svelte",v:"#ff3e00"},
  ];
  const space = [
    {n:"2",v:2},{n:"4",v:4},{n:"6",v:6},{n:"8",v:8},{n:"12",v:12},{n:"16",v:16},
    {n:"20",v:20},{n:"24",v:24},{n:"32",v:32},{n:"40",v:40},{n:"48",v:48},{n:"64",v:64}
  ];
  const radius = [{n:"Sm",v:6},{n:"Md",v:10},{n:"Lg",v:14},{n:"Xl",v:20},{n:"Full",v:99}];
  const type = [
    {n:"Display Lg",cls:"t-display-lg",t:"56 / 1.04 / -.04em / 600"},
    {n:"Display Md",cls:"t-display-md",t:"44 / 1.06 / -.035em / 600"},
    {n:"Display Sm",cls:"t-display-sm",t:"34 / 1.08 / -.03em / 600"},
    {n:"Headline Lg",cls:"t-headline-lg",t:"28 / 1.15 / -.02em / 600"},
    {n:"Headline Md",cls:"t-headline-md",t:"22 / 1.2 / -.015em / 600"},
    {n:"Headline Sm",cls:"t-headline-sm",t:"18 / 1.25 / -.01em / 600"},
    {n:"Title Lg",cls:"t-title-lg",t:"17 / 1.3 / 600"},
    {n:"Title Md",cls:"t-title-md",t:"15 / 1.35 / 600"},
    {n:"Body Lg",cls:"t-body-lg",t:"16 / 1.45 / 400"},
    {n:"Body Md",cls:"t-body-md",t:"14 / 1.45 / 400"},
    {n:"Body Sm",cls:"t-body-sm",t:"13 / 1.4 / 400"},
    {n:"Label Sm",cls:"t-label-sm",t:"11 / .02em / 500 · uppercase"},
    {n:"Code Md",cls:"t-code-md",t:"13 mono / 500"},
    {n:"Code Sm",cls:"t-code-sm",t:"11 mono / 500"},
  ];

  return (
    <div style={{padding:"40px 56px 60px",maxWidth:1640,margin:"0 auto"}}>
      <div style={{marginBottom:36}}>
        <div style={{fontFamily:"var(--font-mono)",fontSize:11,letterSpacing:".15em",color:"var(--brand-primary)",marginBottom:8}}>02 · TOKEN SHEET</div>
        <div className="t-display-md" style={{color:"var(--on-bg)",letterSpacing:"-.035em"}}>One source of truth. Both themes.</div>
        <div className="t-body-lg" style={{color:"var(--on-surface-muted)",marginTop:14,maxWidth:780}}>
          Semantic, named, Compose-translatable. Engineering reads <span style={{fontFamily:"var(--font-mono)",color:"var(--on-bg)"}}>HackertabTheme.colors.surface</span>, not a hex. Light is warm paper; dark is real night.
        </div>
      </div>

      <Section num="2.1" title="Brand ramp" kicker="Accent: terminal green. Pressed = brand 500.">
        <div style={{display:"grid",gridTemplateColumns:"repeat(10, 1fr)",gap:8}}>
          {brand.map(b => <SwatchTile key={b.t} name={b.n} token={b.t} value={b.v} note={b.note}/>)}
        </div>
      </Section>

      <Section num="2.2" title="Themes" kicker="Day = warm paper white. Night = true black with 1% green tint.">
        <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:18}}>
          <ThemeBlock mode="light"/>
          <ThemeBlock mode="dark"/>
        </div>
      </Section>

      <Section num="2.3" title="Semantic" kicker="Status, content polarity, and signal colors.">
        <div style={{display:"grid",gridTemplateColumns:"repeat(5, 1fr)",gap:10}}>
          {semantic.map(s => <SwatchTile key={s.t} name={s.n} token={s.t} value={s.v}/>)}
        </div>
      </Section>

      <Section num="2.4" title="Source brand colors" kicker="Identity preserved. Used as 1× anchor per card.">
        <div style={{display:"grid",gridTemplateColumns:"repeat(6, 1fr)",gap:10}}>
          {sources.map(s => <SwatchTile key={s.t} name={s.n} token={s.t} value={s.v}/>)}
        </div>
      </Section>

      <Section num="2.5" title="Language tags" kicker="22 languages. Mapping preserved; values lifted from canonical GitHub-Linguist palette.">
        <div style={{display:"grid",gridTemplateColumns:"repeat(11, 1fr)",gap:8}}>
          {langs.map(l => (
            <div key={l.n} style={{display:"flex",flexDirection:"column",alignItems:"center",gap:6,padding:8,borderRadius:10,background:"var(--surface)",border:"1px solid var(--border-subtle)"}}>
              <span style={{width:32,height:32,borderRadius:99,background:l.v}}/>
              <div style={{fontFamily:"var(--font-mono)",fontSize:10,color:"var(--on-bg)"}}>{l.n}</div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:9,color:"var(--on-surface-muted)"}}>{l.v}</div>
            </div>
          ))}
        </div>
      </Section>

      <Section num="2.6" title="Type ramp — Geist Sans + Geist Mono">
        <div style={{borderRadius:18,background:"var(--surface)",border:"1px solid var(--border-subtle)",overflow:"hidden"}}>
          {type.map((t,i) => (
            <div key={t.n} style={{display:"flex",alignItems:"center",padding:"16px 22px",borderTop: i===0 ? "0" : "1px solid var(--border-subtle)"}}>
              <div style={{flex:"0 0 220px",fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)",letterSpacing:".05em"}}>{t.n.toUpperCase()}</div>
              <div className={t.cls} style={{flex:1,color:"var(--on-bg)"}}>{t.cls.includes("code") ? "function fetchToday() { return sources.map(s => s.feed) }" : "The quick brown fox jumps over the lazy 0123."}</div>
              <div style={{flex:"0 0 280px",fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)",textAlign:"right"}}>{t.t}</div>
            </div>
          ))}
        </div>
      </Section>

      <Section num="2.7" title="Spacing scale" kicker="4-base scale + 2/6 micro steps. Every margin/padding cites a token.">
        <div style={{display:"grid",gridTemplateColumns:"repeat(12, 1fr)",gap:8}}>
          {space.map(s => (
            <div key={s.n} style={{padding:"14px 10px",borderRadius:10,background:"var(--surface)",border:"1px solid var(--border-subtle)",display:"flex",flexDirection:"column",alignItems:"center",gap:8}}>
              <div style={{height:64,width:"100%",display:"flex",alignItems:"flex-end"}}>
                <div style={{height: s.v, width:"100%", background:"var(--brand-primary)", borderRadius:2}}/>
              </div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-bg)"}}>{s.v}</div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:10,color:"var(--on-surface-muted)"}}>spacing{s.n}</div>
            </div>
          ))}
        </div>
      </Section>

      <Section num="2.8" title="Radius scale" kicker="Inputs sm. Cards lg. Sheets xl. Pills full.">
        <div style={{display:"grid",gridTemplateColumns:"repeat(5, 1fr)",gap:14}}>
          {radius.map(r => (
            <div key={r.n} style={{padding:18,borderRadius:14,background:"var(--surface)",border:"1px solid var(--border-subtle)",display:"flex",flexDirection:"column",alignItems:"center",gap:10}}>
              <div style={{width:90,height:60,background:"var(--brand-primary)",borderRadius:r.v}}/>
              <div className="t-title-sm" style={{color:"var(--on-bg)"}}>{r.n}</div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--on-surface-muted)"}}>radius{r.n} · {r.v === 99 ? "circle" : r.v + "px"}</div>
            </div>
          ))}
        </div>
      </Section>

      <Section num="2.9" title="Elevation — tonal, not shadow">
        <div style={{display:"grid",gridTemplateColumns:"repeat(6, 1fr)",gap:14}}>
          {[0,1,2,3,4,5].map(e => (
            <div key={e} style={{padding:18,borderRadius:14,background:e===0?"transparent":"var(--surface)",border:"1px solid var(--border-subtle)",textAlign:"center"}}>
              <div style={{
                width:"100%",aspectRatio:"3/2",borderRadius:10,
                background:`oklch(${100 - e*1.5}% .003 95)`,
                border: e===0 ? "1px dashed var(--border)" : "1px solid var(--border-subtle)",
                boxShadow: e>=3 ? `0 ${e*2}px ${e*5}px rgba(20,20,18,.${e*2})` : "none",
              }}/>
              <div className="t-title-sm" style={{color:"var(--on-bg)",marginTop:10}}>elevation{e}</div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:10,color:"var(--on-surface-muted)",marginTop:2}}>+{e} tonal</div>
            </div>
          ))}
        </div>
      </Section>

      <Section num="2.10" title="Motion tokens" kicker="Spring-first. Everything ≤ 500ms. Reduced motion → instant.">
        <div style={{display:"grid",gridTemplateColumns:"repeat(4, 1fr)",gap:14}}>
          {[
            {n:"durationInstant",v:"100ms",d:"Pressed states, micro-confirms."},
            {n:"durationFast",v:"200ms",d:"Crossfades, chip selections, toasts."},
            {n:"durationStandard",v:"300ms",d:"Sheet open, source switch, drawer."},
            {n:"durationSlow",v:"500ms",d:"Reserved — celebration only."},
          ].map(m => (
            <div key={m.n} style={{padding:18,borderRadius:14,background:"var(--surface)",border:"1px solid var(--border-subtle)"}}>
              <div className="t-title-md" style={{color:"var(--on-bg)"}}>{m.v}</div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)",marginTop:3}}>{m.n}</div>
              <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:8}}>{m.d}</div>
            </div>
          ))}
        </div>
        <div style={{display:"grid",gridTemplateColumns:"repeat(3, 1fr)",gap:14,marginTop:14}}>
          {[
            {n:"easingStandard",v:"cubic-bezier(.2,0,0,1)",d:"Default for layout/UI moves."},
            {n:"easingEmphasized",v:"cubic-bezier(.3,0,0,1)",d:"Source-rail pill, sheet open."},
            {n:"easingDecelerated",v:"cubic-bezier(0,0,.2,1)",d:"Skeleton fade-in, list entry."},
          ].map(m => (
            <div key={m.n} style={{padding:18,borderRadius:14,background:"var(--surface)",border:"1px solid var(--border-subtle)"}}>
              <div className="t-title-sm" style={{color:"var(--on-bg)"}}>{m.n}</div>
              <div style={{fontFamily:"var(--font-mono)",fontSize:11,color:"var(--brand-primary)",marginTop:4}}>{m.v}</div>
              <div className="t-body-sm" style={{color:"var(--on-surface-muted)",marginTop:8}}>{m.d}</div>
            </div>
          ))}
        </div>
      </Section>
    </div>
  );
};

window.Tokens = Tokens;
