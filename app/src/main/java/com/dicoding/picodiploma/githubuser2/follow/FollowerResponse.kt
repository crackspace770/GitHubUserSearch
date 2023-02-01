package com.dicoding.picodiploma.githubuser2.follow

import com.google.gson.annotations.SerializedName



data class FollowerResponseItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,


)
