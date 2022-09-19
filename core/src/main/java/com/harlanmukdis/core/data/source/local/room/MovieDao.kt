package com.harlanmukdis.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harlanmukdis.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiscoverMovies(discovers: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    fun getDiscoverMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movie")
    fun deleteDiscoverMovies()
}