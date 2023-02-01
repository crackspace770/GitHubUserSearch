package com.dicoding.picodiploma.githubuser2.API

import com.dicoding.picodiploma.githubuser2.follow.FollowerResponseItem
import com.dicoding.picodiploma.githubuser2.follow.FollowingResponseItem
import com.dicoding.picodiploma.githubuser2.profile.ProfileResponse
import com.dicoding.picodiploma.githubuser2.main.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("users/{login}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("login") login: String
    ): Call<ProfileResponse>


    @GET("search/users?")
    @Headers("Authorization: token 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")
    fun getSearch(
        @Query("q") login: String
    ): Call<SearchResponse>

    @GET("users/{login}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<FollowingResponseItem>>

    @GET("users/{login}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<FollowerResponseItem>>

}