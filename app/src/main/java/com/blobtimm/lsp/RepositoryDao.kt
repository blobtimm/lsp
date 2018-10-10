package com.blobtimm.lsp

import androidx.room.*

@Dao
interface RepositoryDao {

    @get:Query("SELECT * FROM repo")
    val all: List<RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: List<RepositoryEntity>)

    @Delete
    fun delete(user: RepositoryEntity)

    @Query("SELECT * FROM repo WHERE name LIKE :search OR full_name LIKE :search OR description LIKE :search OR query LIKE :search")
    fun search(search: String): List<RepositoryEntity>

}