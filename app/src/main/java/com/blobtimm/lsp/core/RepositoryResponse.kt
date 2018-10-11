package com.blobtimm.lsp.core

import com.google.gson.annotations.SerializedName

data class RepositoryResponse (
        @SerializedName("total_count")
        val totalCount: Int?,
        val items: List<Repo>?
) {
    data class Repo (
            val id: Int?,
            val name: String?,
            @SerializedName("full_name")
            val fullName: String?,
            val description: String,
            @SerializedName("html_url")
            val url: String?,
            @SerializedName("open_issues_count")
            val openIssuesCount: Int?,
            @SerializedName("created_at")
            val createdAt: String?,
            @SerializedName("stargazers_count")
            val starTotal: Int?,
            val owner: Owner?
    )
    data class Owner (
            @SerializedName("avatar_url")
            val avatarUrl: String?
    )
}