package com.harlanmukdis.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harlanmukdis.core.data.source.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM genre")
    fun getGenres(): Flow<List<GenreEntity>>

    @Query("DELETE FROM genre")
    fun deleteGenres()
}