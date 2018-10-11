package com.blobtimm.lsp.core

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient

class RetrofitApi {

    interface GitHubClient {
        @GET("/search/repositories")
        fun searchRepositories(@Query("q") query: String): Call<RepositoryResponse>
    }

    companion object {

        val instance: GitHubClient by lazy { create() }

        fun create(): GitHubClient {
            val r = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())

            val okhttp = OkHttpClient.Builder()
                    .build()

            val retrofit = r.client(okhttp).build()

            return retrofit.create(GitHubClient::class.java)
        }
    }
}