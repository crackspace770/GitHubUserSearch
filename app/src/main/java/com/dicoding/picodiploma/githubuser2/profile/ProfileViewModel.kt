package com.dicoding.picodiploma.githubuser2.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuser2.API.ApiConfig
import com.dicoding.picodiploma.githubuser2.follow.FollowerResponseItem
import com.dicoding.picodiploma.githubuser2.follow.FollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {
    private val _detailUser = MutableLiveData<ProfileResponse>()
    val detailUser: LiveData<ProfileResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<FollowerResponseItem>>()
    val listFollowers: LiveData<List<FollowerResponseItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<FollowingResponseItem>>()
    val listFollowing: LiveData<List<FollowingResponseItem>> = _listFollowing

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText


    fun findUserDetail(detailQuery: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(detailQuery)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailUser.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _snackbarText.value = "Loading failed, please check your connection!"
            }
        })
    }

    fun findFollowers(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<List<FollowerResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowerResponseItem>>,
                response: Response<List<FollowerResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowerResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _snackbarText.value = "Loading failed, please check your connection!"
            }
        })
    }


    fun findFollowing(user: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    companion object {
        private const val TAG = "ProfileViewModel"
        private const val EXTRA_USER = "extra_user"
    }
}