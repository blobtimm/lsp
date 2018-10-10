package com.blobtimm.lsp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultRowPresenter {

    class Container(root: View, val listener: Listener): RecyclerView.ViewHolder(root) {
        val title: TextView = root.findViewById(R.id.title)
        val description: TextView = root.findViewById(R.id.description)
        val openIssues: TextView = root.findViewById(R.id.open_issues_count)
    }

    interface Listener {
        // add click events here or other user interaction callbacks
    }

    companion object {

        fun present(vm: SearchPresenterViewModel.Result, c: Container) {
            c.title.text = vm.title
            c.description.text = vm.description
            c.openIssues.text = vm.openIssues
        }

    }

}