package com.harlanmukdis.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val movies: String
)
