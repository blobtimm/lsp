package com.blobtimm.lsp

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room


@Database(entities = arrayOf(RepositoryEntity::class), version = 1)
abstract class LspDatabase : RoomDatabase() {
    abstract fun repoDao(): RepositoryDao
}