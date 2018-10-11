package com.blobtimm.lsp.search.system

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.blobtimm.lsp.core.AvmLifecycle
import com.blobtimm.lsp.core.GithubRepoRepository
import com.blobtimm.lsp.core.LspDatabase
import com.blobtimm.lsp.core.RetrofitApi
import com.blobtimm.lsp.search.logic.GenericDialog
import com.blobtimm.lsp.search.logic.LoadingStatus
import com.blobtimm.lsp.search.logic.SearchLogic
import com.blobtimm.lsp.search.presenter.SearchPresenterViewModel
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class SearchAvm(application: Application): AndroidViewModel(application), AvmLifecycle<SearchAvmData> {

    private val logic: SearchLogic
    private var lifecycle: SearchAvmData? = null
    private val presentMLD = MutableLiveData<SearchPresenterViewModel>()
    private val loadingMLD = MutableLiveData<LoadingStatus>()
    private val dialogMLD = MutableLiveData<GenericDialog>()
    private val dismissKeyboardMLD = MutableLiveData<Unit>()

    init {

        val db = Room.databaseBuilder(application,
                LspDatabase::class.java, "lsp-db")
                .build()

        val retrofit = RetrofitApi.create()

        val githubRepo = GithubRepoRepository(db.repoDao(), retrofit)

        val listener = object : SearchLogic.Listener {
            override fun present(vm: SearchPresenterViewModel) {
                presentMLD.postValue(vm)
            }

            override fun loadingStatus(status: LoadingStatus) {
                loadingMLD.postValue(status)
            }

            override fun showGenericDialog(info: GenericDialog) {
                dialogMLD.postValue(info)
            }

            override fun dismissKeyboard() {
                dismissKeyboardMLD.postValue(Unit)
            }
        }
        logic = SearchLogic(listener, githubRepo)
    }

    /*** OBSERVABLES ***/
    fun present(): LiveData<SearchPresenterViewModel> = presentMLD
    fun showDialog(): LiveData<GenericDialog> = dialogMLD
    fun loadingStatus(): LiveData<LoadingStatus> = loadingMLD
    fun dismissKeyboard(): LiveData<Unit> = dismissKeyboardMLD

    /*** ACTIONABLES ***/
    fun setup(search: SearchAvmData?) {
        GlobalScope.launch(Dispatchers.IO) {
            logic.setup(search)
        }
    }
    fun search(query: String) {
        GlobalScope.launch(Dispatchers.IO) {
            logic.search(query)
        }
        lifecycle = SearchAvmData(query)
    }

    /*** LIFECYCLE ***/
    override fun onSaveInstanceState(): SearchAvmData? = lifecycle
    override fun onRestore(avmData: SearchAvmData) = setup(avmData)
}