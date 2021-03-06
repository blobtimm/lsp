package com.blobtimm.lsp.search.system

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.Type
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import com.blobtimm.lsp.R
import com.blobtimm.lsp.core.AvmLifecycleHandler
import com.blobtimm.lsp.search.presenter.SearchPresenter


class MainActivity : AvmLifecycleHandler<SearchAvmData, SearchAvm>() {


    private lateinit var presenterViews: SearchPresenter.Container
    private lateinit var avm: SearchAvm

    override fun avm(): SearchAvm = avm
    override fun avmDataClassType(): Type = SearchAvmData::class.java

    override fun onCreate() {
        setContentView(R.layout.activity_main)

        val root: View = findViewById(R.id.root)
        presenterViews  = SearchPresenter.Container(root, presenterCallbacks)

        avm = ViewModelProviders.of(this).get(SearchAvm::class.java)

        avm.loadingStatus().observe(this, Observer {
            SearchPresenter.toggleLoading(it.isLoading, presenterViews)
        })

        avm.present().observe(this, Observer {
            supportActionBar?.title = it.pageTitle
            SearchPresenter.present(it, presenterViews)
        })

        avm.showDialog().observe(this, Observer {
            showDialog(it.message, it.okWillFinish)
        })

        avm.dismissKeyboard().observe(this, Observer {
            dismissKeyboard()
        })

        avm.setup(null)
    }

    private fun showDialog(message: String, okWillFinish: Boolean) {
        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                DialogInterface.OnClickListener {
                    dialog, which ->
                    dialog.dismiss()
                    if (okWillFinish) {
                        finish()
                    }
                })
        alertDialog.show()
    }

    fun dismissKeyboard() {
        val windowToken = getCurrentFocus()
        if (windowToken != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken.windowToken, 0)
        }
    }

    val presenterCallbacks = object : SearchPresenter.Listener {
        override fun onSearch(query: String) {
            avm.search(query)
        }
    }

}
