/* Icons.jsx — Inline SVG icon set, Material Symbols Rounded vibe.
   All icons are 24x24 default, currentColor stroke, 1.6 stroke width.
   Source brand icons are filled monochromatic glyphs at 16/24px.
*/
const Icon = ({ name, size = 24, color = "currentColor", strokeWidth = 1.7, style }) => {
  const props = { width: size, height: size, viewBox: "0 0 24 24", fill: "none", stroke: color, strokeWidth, strokeLinecap: "round", strokeLinejoin: "round", style };
  switch (name) {
    case "menu": return <svg {...props}><path d="M4 7h16M4 12h16M4 17h10"/></svg>;
    case "search": return <svg {...props}><circle cx="11" cy="11" r="7"/><path d="m20 20-3.5-3.5"/></svg>;
    case "close": return <svg {...props}><path d="M6 6l12 12M18 6 6 18"/></svg>;
    case "back": return <svg {...props}><path d="M15 6l-6 6 6 6"/></svg>;
    case "forward": return <svg {...props}><path d="M9 6l6 6-6 6"/></svg>;
    case "arrow-right": return <svg {...props}><path d="M5 12h14M13 6l6 6-6 6"/></svg>;
    case "arrow-up": return <svg {...props}><path d="M12 19V5M6 11l6-6 6 6"/></svg>;
    case "arrow-down": return <svg {...props}><path d="M12 5v14M6 13l6 6 6-6"/></svg>;
    case "chevron-down": return <svg {...props}><path d="m6 9 6 6 6-6"/></svg>;
    case "chevron-right": return <svg {...props}><path d="m9 6 6 6-6 6"/></svg>;
    case "chevron-up": return <svg {...props}><path d="m6 15 6-6 6 6"/></svg>;
    case "refresh": return <svg {...props}><path d="M3 12a9 9 0 0 1 15.5-6.3L21 8"/><path d="M21 3v5h-5"/><path d="M21 12a9 9 0 0 1-15.5 6.3L3 16"/><path d="M3 21v-5h5"/></svg>;
    case "bookmark": return <svg {...props}><path d="M6 3h12v18l-6-4-6 4z"/></svg>;
    case "bookmark-fill": return <svg {...props} fill={color} stroke="none"><path d="M6 3a1 1 0 0 0-1 1v17a.5.5 0 0 0 .79.4L12 17.2l6.21 4.2A.5.5 0 0 0 19 21V4a1 1 0 0 0-1-1z"/></svg>;
    case "share": return <svg {...props}><path d="M12 3v13"/><path d="M7 8l5-5 5 5"/><path d="M5 14v5a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2v-5"/></svg>;
    case "more": return <svg {...props}><circle cx="5" cy="12" r="1.4" fill={color}/><circle cx="12" cy="12" r="1.4" fill={color}/><circle cx="19" cy="12" r="1.4" fill={color}/></svg>;
    case "more-v": return <svg {...props}><circle cx="12" cy="5" r="1.4" fill={color}/><circle cx="12" cy="12" r="1.4" fill={color}/><circle cx="12" cy="19" r="1.4" fill={color}/></svg>;
    case "today": return <svg {...props}><rect x="3.5" y="5" width="17" height="15" rx="2.5"/><path d="M8 3v4M16 3v4M3.5 10h17"/></svg>;
    case "bookmarks-tab": return <svg {...props}><path d="M6 3h12v18l-6-4-6 4z"/></svg>;
    case "settings": return <svg {...props}><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.7 1.7 0 0 0 .3 1.8l.1.1a2 2 0 1 1-2.8 2.8l-.1-.1a1.7 1.7 0 0 0-1.8-.3 1.7 1.7 0 0 0-1 1.5V21a2 2 0 1 1-4 0v-.1A1.7 1.7 0 0 0 9 19.4a1.7 1.7 0 0 0-1.8.3l-.1.1a2 2 0 1 1-2.8-2.8l.1-.1a1.7 1.7 0 0 0 .3-1.8 1.7 1.7 0 0 0-1.5-1H3a2 2 0 1 1 0-4h.1A1.7 1.7 0 0 0 4.6 9a1.7 1.7 0 0 0-.3-1.8l-.1-.1a2 2 0 1 1 2.8-2.8l.1.1a1.7 1.7 0 0 0 1.8.3H9a1.7 1.7 0 0 0 1-1.5V3a2 2 0 1 1 4 0v.1a1.7 1.7 0 0 0 1 1.5 1.7 1.7 0 0 0 1.8-.3l.1-.1a2 2 0 1 1 2.8 2.8l-.1.1a1.7 1.7 0 0 0-.3 1.8V9a1.7 1.7 0 0 0 1.5 1H21a2 2 0 1 1 0 4h-.1a1.7 1.7 0 0 0-1.5 1z"/></svg>;
    case "user": return <svg {...props}><circle cx="12" cy="8" r="4"/><path d="M4 21a8 8 0 0 1 16 0"/></svg>;
    case "moon": return <svg {...props}><path d="M21 12.8A9 9 0 1 1 11.2 3a7 7 0 0 0 9.8 9.8z"/></svg>;
    case "sun": return <svg {...props}><circle cx="12" cy="12" r="4"/><path d="M12 2v2M12 20v2M4.9 4.9l1.4 1.4M17.7 17.7l1.4 1.4M2 12h2M20 12h2M4.9 19.1l1.4-1.4M17.7 6.3l1.4-1.4"/></svg>;
    case "globe": return <svg {...props}><circle cx="12" cy="12" r="9"/><path d="M3 12h18M12 3a14 14 0 0 1 0 18M12 3a14 14 0 0 0 0 18"/></svg>;
    case "calendar": return <svg {...props}><rect x="3.5" y="5" width="17" height="15" rx="2.5"/><path d="M8 3v4M16 3v4M3.5 10h17"/></svg>;
    case "tag": return <svg {...props}><path d="M3 12V4a1 1 0 0 1 1-1h8l9 9-9 9z"/><circle cx="8" cy="8" r="1.5"/></svg>;
    case "star": return <svg {...props}><path d="m12 3 2.7 5.6 6.3.9-4.5 4.4 1 6.1L12 17.8 6.5 20l1-6.1L3 9.5l6.3-.9z"/></svg>;
    case "fork": return <svg {...props}><circle cx="6" cy="5" r="2"/><circle cx="18" cy="5" r="2"/><circle cx="12" cy="19" r="2"/><path d="M6 7v3a3 3 0 0 0 3 3h6a3 3 0 0 0 3-3V7M12 13v4"/></svg>;
    case "comment": return <svg {...props}><path d="M21 12a8 8 0 0 1-11.6 7.1L4 21l1.9-5.4A8 8 0 1 1 21 12z"/></svg>;
    case "flame": return <svg {...props}><path d="M12 3s4 4 4 8a4 4 0 0 1-8 0c0-1 .5-2 1-2.5 0 1.5 1 2.5 2 2.5 0-2-1.5-3.5-1.5-5.5C9.5 4 12 3 12 3z"/><path d="M7 13a5 5 0 0 0 10 0"/></svg>;
    case "heart": return <svg {...props}><path d="M20.8 7.6a5 5 0 0 0-8.8-3.3 5 5 0 1 0-7 7l7 7 7-7a5 5 0 0 0 1.8-3.7z"/></svg>;
    case "info": return <svg {...props}><circle cx="12" cy="12" r="9"/><path d="M12 11v5M12 8h.01"/></svg>;
    case "check": return <svg {...props}><path d="m5 12 4 4 10-10"/></svg>;
    case "wifi-off": return <svg {...props}><path d="M2 8.8A18 18 0 0 1 22 8.8"/><path d="M5 13a13 13 0 0 1 14 0"/><path d="M8.5 16.5a8 8 0 0 1 7 0"/><path d="M12 20h.01"/><path d="M2 2l20 20"/></svg>;
    case "trash": return <svg {...props}><path d="M4 7h16M9 7V4h6v3M6 7l1 13a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2l1-13"/></svg>;
    case "external": return <svg {...props}><path d="M14 4h6v6"/><path d="M20 4 10 14"/><path d="M20 14v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V5a1 1 0 0 1 1-1h5"/></svg>;
    case "filter": return <svg {...props}><path d="M3 5h18l-7 9v6l-4-2v-4z"/></svg>;
    case "sparkle": return <svg {...props}><path d="M12 3v6M12 15v6M3 12h6M15 12h6M5.6 5.6l4.2 4.2M14.2 14.2l4.2 4.2M5.6 18.4l4.2-4.2M14.2 9.8l4.2-4.2"/></svg>;
    case "list": return <svg {...props}><path d="M8 6h13M8 12h13M8 18h13M3 6h.01M3 12h.01M3 18h.01"/></svg>;
    case "grid": return <svg {...props}><rect x="3" y="3" width="7" height="7" rx="1.5"/><rect x="14" y="3" width="7" height="7" rx="1.5"/><rect x="3" y="14" width="7" height="7" rx="1.5"/><rect x="14" y="14" width="7" height="7" rx="1.5"/></svg>;
    case "mail": return <svg {...props}><rect x="3" y="5" width="18" height="14" rx="2"/><path d="m4 7 8 6 8-6"/></svg>;
    case "code": return <svg {...props}><path d="m8 6-6 6 6 6M16 6l6 6-6 6"/></svg>;
    case "dot": return <svg {...props} fill={color} stroke="none"><circle cx="12" cy="12" r="4"/></svg>;
    default: return null;
  }
};

/* === Source brand glyphs (16-24px) === */
const SourceIcon = ({ source, size = 20 }) => {
  const s = { width: size, height: size, display: "inline-block" };
  switch (source) {
    case "github":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><path d="M12 0a12 12 0 0 0-3.8 23.4c.6.1.8-.3.8-.6v-2.1c-3.3.7-4-1.6-4-1.6-.6-1.4-1.4-1.8-1.4-1.8-1.1-.7.1-.7.1-.7 1.2.1 1.9 1.3 1.9 1.3 1.1 1.9 2.9 1.3 3.6 1 .1-.8.4-1.3.8-1.6-2.7-.3-5.5-1.3-5.5-5.9 0-1.3.5-2.4 1.2-3.2-.1-.3-.5-1.5.1-3.2 0 0 1-.3 3.3 1.2a11.5 11.5 0 0 1 6 0c2.3-1.5 3.3-1.2 3.3-1.2.7 1.7.2 2.9.1 3.2.7.8 1.2 1.9 1.2 3.2 0 4.6-2.8 5.6-5.5 5.9.4.4.8 1.1.8 2.2v3.3c0 .3.2.7.8.6A12 12 0 0 0 12 0z"/></svg>;
    case "hackernews":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><rect width="24" height="24" rx="3"/><path fill="#fff" d="m11 13.5-3.5-7H9l2 4.5 2-4.5h1.5L11 13.5V18h-1z"/></svg>;
    case "reddit":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><circle cx="12" cy="12" r="11"/><circle fill="#fff" cx="8.5" cy="13" r="1.2"/><circle fill="#fff" cx="15.5" cy="13" r="1.2"/><path fill="none" stroke="#fff" strokeWidth="1.3" strokeLinecap="round" d="M9 16c.8.7 2 1 3 1s2.2-.3 3-1"/><circle fill="#fff" cx="18.5" cy="9" r="1.6"/><path stroke="#fff" strokeWidth="1.3" fill="none" d="M18 8 13 6"/></svg>;
    case "producthunt":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><circle cx="12" cy="12" r="11"/><path fill="#fff" d="M9 6h4.5a3.5 3.5 0 0 1 0 7H10v5H9zm1 1.5v4h3.4a2 2 0 0 0 0-4z"/></svg>;
    case "devto":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><rect width="24" height="24" rx="3"/><text x="12" y="16" fill="#fff" fontFamily="monospace" fontWeight="700" fontSize="9" textAnchor="middle">DEV</text></svg>;
    case "lobsters":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><rect width="24" height="24" rx="3"/><text x="12" y="16" fill="#fff" fontFamily="serif" fontWeight="700" fontSize="11" textAnchor="middle">L</text></svg>;
    case "hashnode":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><path d="M2.5 8.5 8.5 2.5a4 4 0 0 1 7 0L21.5 8.5a4 4 0 0 1 0 7l-6 6a4 4 0 0 1-7 0l-6-6a4 4 0 0 1 0-7z"/><circle fill="#fff" cx="12" cy="12" r="3.5"/></svg>;
    case "freecodecamp":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><circle cx="12" cy="12" r="11"/><text x="12" y="15.5" fill="#fff" fontFamily="monospace" fontWeight="700" fontSize="11" textAnchor="middle">{'</>'}</text></svg>;
    case "indiehackers":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><rect width="24" height="24" rx="3"/><path stroke="#fff" strokeWidth="2" fill="none" d="M7 18V6m3 12V10m3 8V8m3 10v-6"/></svg>;
    case "medium":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><rect width="24" height="24" rx="3"/><g fill="#fff"><circle cx="7" cy="12" r="3.5"/><ellipse cx="14.5" cy="12" rx="2" ry="3.5"/><path d="M18.5 8.5h.7v7h-.7z"/></g></svg>;
    case "hackernoon":
      return <svg style={s} viewBox="0 0 24 24" fill="#000"><rect width="24" height="24" rx="3" fill="#00fe00"/><text x="12" y="16" fill="#000" fontFamily="monospace" fontWeight="900" fontSize="10" textAnchor="middle">H</text></svg>;
    case "conferences":
      return <svg style={s} viewBox="0 0 24 24" fill="currentColor"><rect width="24" height="24" rx="3"/><g fill="#fff"><rect x="6" y="7" width="12" height="11" rx="1.5"/><rect fill="currentColor" x="7.5" y="9" width="9" height="2"/><rect x="8" y="4" width="1.5" height="4" rx="0.5"/><rect x="14.5" y="4" width="1.5" height="4" rx="0.5"/></g></svg>;
    default: return null;
  }
};

window.Icon = Icon;
window.SourceIcon = SourceIcon;
