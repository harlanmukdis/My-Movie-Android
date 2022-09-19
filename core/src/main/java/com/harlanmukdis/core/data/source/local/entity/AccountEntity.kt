package com.harlanmukdis.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "userID")
    val userID: String,
    @ColumnInfo(name = "firstName")
    val firstName: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "fullName")
    val fullName: String,
    @ColumnInfo(name = "email")
    val email: String
)
