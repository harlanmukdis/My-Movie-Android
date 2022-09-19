package com.harlanmukdis.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harlanmukdis.core.data.source.local.entity.AccountEntity
import com.harlanmukdis.core.data.source.local.entity.GenreEntity
import com.harlanmukdis.core.data.source.local.entity.MovieEntity

@Database(entities = [
    GenreEntity::class,
    MovieEntity::class
                     ], version = 1, exportSchema = false)
abstract class MyDataBase: RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun movieDao(): MovieDao
}