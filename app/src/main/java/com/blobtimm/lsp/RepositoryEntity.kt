package com.blobtimm.lsp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
data class RepositoryEntity (
    @PrimaryKey
    val id: Int,

    val name: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    val description: String,

    val url: String,

    val avatar: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String,

    @ColumnInfo(name = "open_issues_count")
    val openIssuesCount: Int
)