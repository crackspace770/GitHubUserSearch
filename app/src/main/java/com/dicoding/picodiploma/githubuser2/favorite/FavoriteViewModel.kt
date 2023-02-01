package com.dicoding.picodiploma.githubuser2.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()
}