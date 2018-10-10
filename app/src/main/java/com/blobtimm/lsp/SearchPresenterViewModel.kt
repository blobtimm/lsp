package com.blobtimm.lsp

data class SearchPresenterViewModel(
        val pageTitle: String,
        val showEmptyState: Boolean,
        val results: List<Result>
) {
    data class Result(
            val title: String,
            val avatar: String
    )
}