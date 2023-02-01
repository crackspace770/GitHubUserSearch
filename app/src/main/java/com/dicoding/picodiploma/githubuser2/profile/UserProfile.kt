package com.dicoding.picodiploma.githubuser2.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    var login: String? = null,
    var avatar: String? = null,
    var isFavorite: Boolean? = null
): Parcelable