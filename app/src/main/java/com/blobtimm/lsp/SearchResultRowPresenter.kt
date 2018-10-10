package com.blobtimm.lsp

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SearchResultRowPresenter {

    class Container(root: View, val listener: Listener): RecyclerView.ViewHolder(root) {

    }

    interface Listener {

    }

    companion object {

        fun present(vm: SearchPresenterViewModel.Result, c: Container) {

        }

    }

}