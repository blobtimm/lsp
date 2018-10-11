package com.blobtimm.lsp.search.logic

import com.blobtimm.lsp.*
import com.blobtimm.lsp.core.GithubRepoRepository
import com.blobtimm.lsp.core.RepositoryEntity
import com.blobtimm.lsp.core.getString
import com.blobtimm.lsp.search.presenter.SearchPresenterViewModel
import com.blobtimm.lsp.search.system.SearchAvmData

class SearchLogic(private val listener: Listener, private val githubRepo: GithubRepoRepository) {

    init {
        val ghListener = object : GithubRepoRepository.Listener {
            override fun onResult(list: List<RepositoryEntity>) {

                val vm = SearchPresenterViewModel(
                        pageTitle = getString(R.string.search_title_results, list.size),
                        showEmptyState = list.isEmpty(),
                        results = list.map {
                            SearchPresenterViewModel.Result(
                                    title = it.name,
                                    avatar = it.avatar,
                                    openIssues = getString(R.string.open_issues_count, it.openIssuesCount),
                                    description = it.description
                            )
                        }
                )

                listener.present(vm)
                listener.dismissKeyboard()
            }

            override fun onError(message: String) {
                listener.loadingStatus(LoadingStatus(false))
                listener.showGenericDialog(GenericDialog(message, false))
            }

            override fun isLoading(isLoading: Boolean) {
                listener.loadingStatus(LoadingStatus(isLoading))
            }
        }
        githubRepo.addListener(ghListener)
    }

    interface Listener {
        fun present(vm: SearchPresenterViewModel)
        fun loadingStatus(status: LoadingStatus)
        fun showGenericDialog(info: GenericDialog)
        fun dismissKeyboard()
    }

    fun search(query: String) {
        githubRepo.search(query)
    }

    fun setup(payload: SearchAvmData?) {
        val query = payload?.lastSearchedPhrase
        if (query != null) {
            githubRepo.search(query)
        } else {
            listener.present(emptyVm())
            listener.loadingStatus(LoadingStatus(false))
        }
    }

    companion object {
        fun emptyVm() = SearchPresenterViewModel(
                pageTitle = getString(R.string.search_title),
                results = listOf(),
                showEmptyState = false
        )
    }
}