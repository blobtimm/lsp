package com.blobtimm.lsp

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchPresenter {

    class Container(root: View, val listener: Listener) {
        val resultsLists = root.findViewById(R.id.result_list) as RecyclerView
        val resultsRecyclerViewAdapter: SearchRecyclerViewAdapter
        val noResults: View = root.findViewById(R.id.no_results)
        val searchText: EditText = root.findViewById(R.id.search_text_input)
        val beginSearch: Button = root.findViewById(R.id.begin_search)
        val loadingSpinner: View = root.findViewById(R.id.loading_spinner)
        init {
            resultsLists.layoutManager = LinearLayoutManager(resultsLists.context)
            resultsRecyclerViewAdapter = SearchRecyclerViewAdapter(SearchLogic.emptyVm().results, listener)
            resultsLists.adapter = resultsRecyclerViewAdapter
        }
    }

    interface Listener: SearchRecyclerViewAdapter.Listener {
        fun onSearch(query: String)
    }

    companion object {

        fun toggleLoading(isLoading: Boolean, c: Container) {
            c.loadingSpinner.visibility = if (isLoading) { View.VISIBLE } else { View.GONE }
        }

        fun present(vm: SearchPresenterViewModel, c: Container) {
            c.resultsRecyclerViewAdapter.updateList(vm.results)

            c.noResults.visibility = if (vm.showEmptyState) { View.VISIBLE } else { View.GONE }
            c.resultsLists.visibility = if (vm.showEmptyState) { View.GONE } else { View.VISIBLE }

            c.beginSearch.setOnClickListener {
                c.listener.onSearch(c.searchText.text.toString())
            }
        }

    }

}