package com.blobtimm.lsp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SearchRecyclerViewAdapter(
        rvm: List<SearchPresenterViewModel.Result>,
        val listener: Listener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var currentList: List<SearchPresenterViewModel.Result>

    init {
        currentList = rvm
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val root = layoutInflater.inflate(R.layout.search_repo_result_row, parent, false)
        return SearchResultRowPresenter.Container(root, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList.get(position)
        SearchResultRowPresenter.present(item, holder as SearchResultRowPresenter.Container)
    }

    override fun getItemCount(): Int = currentList.size

    class SearchDiffUtils(val oldList: List<SearchPresenterViewModel.Result>, val newList: List<SearchPresenterViewModel.Result>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.title == newItem.title
        }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }

    fun updateList(newList: List<SearchPresenterViewModel.Result>) {
        val diffResult = DiffUtil.calculateDiff(SearchDiffUtils(currentList, newList), false)
        currentList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface Listener: SearchResultRowPresenter.Listener
}
