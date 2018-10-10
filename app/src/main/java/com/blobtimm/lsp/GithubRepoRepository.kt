package com.blobtimm.lsp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepoRepository(private val dao: RepositoryDao, private val api: RetrofitApi.GitHubClient) {

    private val listeners = mutableListOf<Listener>()

    interface Listener {
        fun onResult(list: List<RepositoryEntity>)
        fun onError(message: String)
        fun isLoading(isLoading: Boolean)
    }

    fun addListener(l: Listener) {
        listeners.add(l)
    }

    fun removeListener(l: Listener) {
        listeners.remove(l)
    }

    fun search(query: String){

        listeners.forEach { it.isLoading(true) }

        val response = api.searchRepositories(query).execute()

        if (response.isSuccessful) {
            val entities = mutableListOf<RepositoryEntity>()

            response?.body()?.items?.forEach {
                val e = RepositoryEntity(
                        id = it.id ?: 0,
                        name = it.name ?: "",
                        fullName = it.fullName ?: "",
                        description = it.description ?: "",
                        url = it.url ?: "",
                        avatar = it.owner?.avatarUrl ?: "",
                        createdAt = it.createdAt ?: "",
                        openIssuesCount = it.openIssuesCount ?: 0
                )
                entities.add(e)
            }

            dao.insertAll(entities)

            val results = dao.search("%$query%")
            listeners.forEach { it.isLoading(false) }
            if (results.isEmpty()) {
                listeners.forEach { it.onResult(listOf()) }
            } else {
                listeners.forEach { it.onResult(results) }
            }
        } else {

            if (response.message() != null) {
                listeners.forEach { it.onError(response.message()) }
            } else {
                val results = dao.search(query)
                listeners.forEach { it.isLoading(false) }
                if (results.isEmpty()) {
                    listeners.forEach { it.onResult(listOf()) }
                } else {
                    listeners.forEach { it.onResult(results) }
                }
            }
        }
    }



}
