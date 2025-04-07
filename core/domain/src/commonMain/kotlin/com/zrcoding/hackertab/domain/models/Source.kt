package com.zrcoding.hackertab.domain.models

enum class Source(
    val id: String,
    val label: String,
    val type: String,
    val link: String,
    val analyticsTag: String
) {
    GITHUB(
        id = "github",
        label = "Github repositories",
        type = "supported",
        link = "https://github.com/",
        analyticsTag = "github"
    ),
    HACKER_NEWS(
        id = "hackernews",
        label = "Hackernews",
        type = "supported",
        link = "https://news.ycombinator.com/",
        analyticsTag = "hackernews"
    ),
    CONFERENCES(
        id = "conferences",
        label = "Upcoming events",
        type = "supported",
        link = "https://confs.tech/",
        analyticsTag = "events"
    ),
    DEVTO(
        id = "devto",
        label = "DevTo",
        type = "supported",
        link = "https://dev.to/",
        analyticsTag = "devto"
    ),
    PRODUCTHUNT(
        id = "producthunt",
        label = "Product Hunt",
        type = "supported",
        link = "https://producthunt.com/",
        analyticsTag = "producthunt"
    ),
    REDDIT(
        id = "reddit",
        label = "Reddit",
        type = "supported",
        link = "https://reddit.com/",
        analyticsTag = "reddit"
    ),
    LOBSTERS(
        id = "lobsters",
        label = "Lobsters",
        type = "supported",
        link = "https://lobste.rs/",
        analyticsTag = "lobsters"
    ),
    HASH_NODE(
        id = "hashnode",
        label = "Hashnode",
        type = "supported",
        link = "https://hashnode.com/",
        analyticsTag = "hashnode"
    ),
    FREE_CODE_CAMP(
        id = "freecodecamp",
        label = "FreeCodeCamp",
        type = "supported",
        link = "https://freecodecamp.com/news",
        analyticsTag = "freecodecamp"
    ),
    INDIE_HACKERS(
        id = "indiehackers",
        label = "IndieHackers",
        type = "supported",
        link = "https://indiehackers.com/",
        analyticsTag = "indiehackers"
    ),
    MEDIUM(
        id = "medium",
        label = "Medium",
        type = "supported",
        link = "https://medium.com/",
        analyticsTag = "medium"
    ),
}