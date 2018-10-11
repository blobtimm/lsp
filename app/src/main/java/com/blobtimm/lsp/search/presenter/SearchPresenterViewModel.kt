package com.blobtimm.lsp.search.presenter

data class SearchPresenterViewModel(
        val pageTitle: String,
        val showEmptyState: Boolean,
        val results: List<Result>
) {
    data class Result(
            val title: String,
            val avatar: String,
            val description:String,
            val openIssues: String
    )
}