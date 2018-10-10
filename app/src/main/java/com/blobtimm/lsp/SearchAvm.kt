package com.blobtimm.lsp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class SearchAvm(application: Application): AndroidViewModel(application), AvmLifecycle<SearchAvmData> {

    private val logic: SearchLogic
    private var lifecycle: SearchAvmData? = null
    private val presentMLD = MutableLiveData<SearchPresenterViewModel>()
    private val loadingMLD = MutableLiveData<LoadingStatus>()
    private val dialogMLD = MutableLiveData<GenericDialog>()

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
        }
        logic = SearchLogic(listener, githubRepo)
    }

    /*** OBSERVABLES ***/
    fun present(): LiveData<SearchPresenterViewModel> = presentMLD
    fun showDialog(): LiveData<GenericDialog> = dialogMLD
    fun loadingStatus(): LiveData<LoadingStatus> = loadingMLD

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
    }

    /*** LIFECYCLE ***/
    override fun onSaveInstanceState(): SearchAvmData? = lifecycle
    override fun onRestore(avmData: SearchAvmData) = setup(avmData)
}