package com.dicoding.picodiploma.githubuser2.database

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Parcelize
data class Favorite (

    @PrimaryKey
    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "avatar")
    var avatar: String? = null,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false

): Parcelable