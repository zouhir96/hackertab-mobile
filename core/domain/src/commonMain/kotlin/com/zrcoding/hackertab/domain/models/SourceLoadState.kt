package com.zrcoding.hackertab.domain.models

/**
 * Per-source load progress for the aggregated "All" feed.
 *
 * Wave 5L (aggregator polish) — replaces the silent "drop failed sources" UX
 * with explicit per-source state surfaced to the UI so partially loaded feeds
 * can render a skeleton up top and inline error captions per failed source.
 */
sealed interface SourceLoadState {
    /** Initial state — source has not been requested yet. */
    data object Idle : SourceLoadState

    /** Request in flight (or queued behind the concurrency cap). */
    data object Loading : SourceLoadState

    /** Source returned [articleCount] items successfully. */
    data class Loaded(val articleCount: Int) : SourceLoadState

    /** Source request failed with [error]; UI surfaces inline caption. */
    data class Failed(val error: NetworkErrors) : SourceLoadState
}
