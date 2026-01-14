package com.zrcoding.hackertab.domain.models

enum class Source(
    val id: String,
    val label: String,
    val type: String,
    val link: String,
    val analyticsTag: String,
    val supportsFilters: Boolean
) {
    DEVTO(
        id = "devto",
        label = "DevTo",
        type = "supported",
        link = "https://dev.to/",
        analyticsTag = "devto",
        supportsFilters = true
    ),
    FREE_CODE_CAMP(
        id = "freecodecamp",
        label = "FreeCodeCamp",
        type = "supported",
        link = "https://freecodecamp.com/news",
        analyticsTag = "freecodecamp",
        supportsFilters = true
    ),
    GITHUB(
        id = "github",
        label = "Github repositories",
        type = "supported",
        link = "https://github.com/",
        analyticsTag = "github",
        supportsFilters = true
    ),
    HACKER_NEWS(
        id = "hackernews",
        label = "Hackernews",
        type = "supported",
        link = "https://news.ycombinator.com/",
        analyticsTag = "hackernews",
        supportsFilters = false
    ),
    HACKER_NOON(
        id = "hackernoon",
        label = "Hackernoon",
        type = "supported",
        link = "https://hackernoon.com/",
        analyticsTag = "hackernoon",
        supportsFilters = true
    ),
    HASH_NODE(
        id = "hashnode",
        label = "Hashnode",
        type = "supported",
        link = "https://hashnode.com/",
        analyticsTag = "hashnode",
        supportsFilters = true
    ),
    INDIE_HACKERS(
        id = "indiehackers",
        label = "IndieHackers",
        type = "supported",
        link = "https://indiehackers.com/",
        analyticsTag = "indiehackers",
        supportsFilters = false
    ),
    LOBSTERS(
        id = "lobsters",
        label = "Lobsters",
        type = "supported",
        link = "https://lobste.rs/",
        analyticsTag = "lobsters",
        supportsFilters = false
    ),
    MEDIUM(
        id = "medium",
        label = "Medium",
        type = "supported",
        link = "https://medium.com/",
        analyticsTag = "medium",
        supportsFilters = true
    ),
    REDDIT(
        id = "reddit",
        label = "Reddit",
        type = "supported",
        link = "https://reddit.com/",
        analyticsTag = "reddit",
        supportsFilters = true
    ),
    PRODUCTHUNT(
        id = "producthunt",
        label = "Product Hunt",
        type = "supported",
        link = "https://producthunt.com/",
        analyticsTag = "producthunt",
        supportsFilters = false
    ),
    CONFERENCES(
        id = "conferences",
        label = "Upcoming events",
        type = "supported",
        link = "https://confs.tech/",
        analyticsTag = "events",
        supportsFilters = true
    );

    companion object {
        fun fromId(id: String): Source? = entries.find { it.id == id }
    }
}