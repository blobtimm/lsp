package com.blobtimm.lsp.core

import androidx.room.RoomDatabase
import androidx.room.Database


@Database(entities = arrayOf(RepositoryEntity::class), version = 1)
abstract class LspDatabase : RoomDatabase() {
    abstract fun repoDao(): RepositoryDao
}