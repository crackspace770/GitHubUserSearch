package com.dicoding.picodiploma.githubuser2.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.repository.FavoriteRepository

class FavoriteUpdateViewModel(application: Application): ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }


    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

}