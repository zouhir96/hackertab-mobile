/* Sample data for the prototype — realistic dev-news content. */

window.SOURCES = [
  { id: "all",          label: "All",          color: "var(--brand-primary)",  supportsFilters: true,  glyph: null },
  { id: "github",       label: "GitHub",       color: "#181717",               supportsFilters: true,  glyph: "github" },
  { id: "hackernews",   label: "Hacker News",  color: "#ff6600",               supportsFilters: false, glyph: "hackernews" },
  { id: "devto",        label: "Dev.to",       color: "#0a0a0a",               supportsFilters: true,  glyph: "devto" },
  { id: "reddit",       label: "Reddit",       color: "#ff4500",               supportsFilters: true,  glyph: "reddit" },
  { id: "producthunt",  label: "Product Hunt", color: "#da552f",               supportsFilters: false, glyph: "producthunt" },
  { id: "lobsters",     label: "Lobsters",     color: "#ac130d",               supportsFilters: false, glyph: "lobsters" },
  { id: "hashnode",     label: "Hashnode",     color: "#2962ff",               supportsFilters: true,  glyph: "hashnode" },
  { id: "freecodecamp", label: "freeCodeCamp", color: "#0a0a23",               supportsFilters: true,  glyph: "freecodecamp" },
  { id: "indiehackers", label: "Indie Hackers",color: "#0e2439",               supportsFilters: false, glyph: "indiehackers" },
  { id: "medium",       label: "Medium",       color: "#00ab6c",               supportsFilters: true,  glyph: "medium" },
  { id: "hackernoon",   label: "HackerNoon",   color: "#00b14f",               supportsFilters: true,  glyph: "hackernoon" },
  { id: "conferences",  label: "Conferences",  color: "#6e56cf",               supportsFilters: false, glyph: "conferences" },
];

window.TOPICS = [
  { id: "kotlin", label: "Kotlin", color: "#a97bff" },
  { id: "android", label: "Android", color: "#3ddc84" },
  { id: "compose", label: "Jetpack Compose", color: "#3ddc84" },
  { id: "rust", label: "Rust", color: "#dea584" },
  { id: "go", label: "Go", color: "#00add8" },
  { id: "typescript", label: "TypeScript", color: "#3178c6" },
  { id: "react", label: "React", color: "#61dafb" },
  { id: "ai", label: "AI / ML", color: "#f5af0a" },
  { id: "swift", label: "Swift", color: "#f05138" },
];

window.PROFILES = [
  { id: "mobile",   title: "Mobile",      sub: "Engineer", icon: "📱" },
  { id: "frontend", title: "Frontend",    sub: "Engineer", icon: "🖥️" },
  { id: "backend",  title: "Backend",     sub: "Engineer", icon: "⚙️"  },
  { id: "fullstack",title: "Full Stack",  sub: "Engineer", icon: "🧩" },
  { id: "devops",   title: "DevOps",      sub: "Engineer", icon: "🛠️" },
  { id: "data",     title: "Data",        sub: "Engineer", icon: "📊" },
  { id: "security", title: "Security",    sub: "Engineer", icon: "🛡️" },
  { id: "ml",       title: "ML",          sub: "Engineer", icon: "🧠" },
  { id: "other",    title: "Other",       sub: "",         icon: "✦"  },
];

/* === Articles per source, with realistic shape per card variant === */
window.FEED = [
  // Today section
  { source: "github",  topic: "kotlin", time: "2h ago",
    owner: "JetBrains", repo: "compose-multiplatform",
    description: "Compose Multiplatform 1.9.3 — performance pass, native iOS scrolling, accessibility tree fixes.",
    language: "Kotlin", langColor: "#a97bff", stars: "17.2k", forks: "1.4k", fresh: true },
  { source: "hackernews", time: "1h ago",
    title: "Show HN: I built a self-hostable Linear alternative in Rust",
    points: 482, comments: 213, fresh: true },
  { source: "producthunt", time: "5h ago",
    title: "Bolt.new for mobile",
    tagline: "AI mobile-app builder that ships to TestFlight in one tap.",
    upvotes: 814, comments: 142,
    thumb: "M" },
  { source: "github", topic: "rust",
    owner: "rust-lang", repo: "rust",
    description: "Rust 1.84 release notes — async closures stabilized, strict provenance.",
    language: "Rust", langColor: "#dea584", stars: "98.3k", forks: "12.5k", time: "6h ago" },
  // Yesterday
  { source: "devto", time: "1d ago",
    title: "Stop reaching for useEffect — a new mental model for React data flow",
    reactions: 421, comments: 38,
    tags: ["react", "javascript", "webdev"] },
  { source: "conferences",
    title: "KotlinConf 2026",
    location: "Copenhagen, Denmark",
    dateLabel: "May 13 – 15", day: "13", month: "MAY",
    tags: ["kotlin", "multiplatform"], online: false, time: "1d ago" },
  { source: "reddit", time: "1d ago",
    title: "Why is everyone migrating away from Next.js this month?",
    score: 1248, comments: 612, subreddit: "r/webdev" },
  { source: "lobsters", time: "1d ago",
    title: "Reverse-engineering the Apple Silicon GPU command stream",
    score: 187, comments: 24 },
  // Earlier this week
  { source: "hashnode", time: "3d ago",
    title: "I rewrote our type-checker in Zig and it's 40× faster",
    reactions: 92, comments: 18,
    tags: ["zig", "compilers"] },
  { source: "medium", time: "4d ago",
    title: "The end of the framework wars: a calmer outlook for 2026",
    claps: 2400, comments: 64 },
  { source: "indiehackers", time: "5d ago",
    title: "From $0 to $14k MRR in 90 days — what actually moved the needle",
    score: 318, comments: 47 },
  { source: "freecodecamp", time: "6d ago",
    title: "Build a real-time multiplayer game with Bun and Liveview",
    tags: ["bun", "elixir"] },
];

window.BOOKMARKS = [
  { source: "github", title: "vercel/ai-sdk — Stream protocol v3", time: "2h ago", read: false },
  { source: "hackernews", title: "Show HN: I built a self-hostable Linear alternative in Rust", time: "1d ago", read: false },
  { source: "devto", title: "Stop reaching for useEffect — a new mental model for React", time: "2d ago", read: true },
  { source: "conferences", title: "KotlinConf 2026 — Copenhagen", time: "3d ago", read: true },
  { source: "lobsters", title: "Reverse-engineering the Apple Silicon GPU command stream", time: "4d ago", read: true },
  { source: "medium", title: "The end of the framework wars", time: "1w ago", read: true },
];
