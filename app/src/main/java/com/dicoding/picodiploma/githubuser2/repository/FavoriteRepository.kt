package com.dicoding.picodiploma.githubuser2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.database.FavoriteDao
import com.dicoding.picodiploma.githubuser2.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService


class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoriteDao
    private val executorService: ExecutorService = java.util.concurrent.Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoritesDao.getAllFavorite()

    fun insert(favorite: Favorite) {
        executorService.execute {
            mFavoritesDao.insert(favorite)
        }
    }

    fun delete(favorite: Favorite) {
        executorService.execute {
            mFavoritesDao.delete(favorite)
        }
    }

    fun update(favorite: Favorite) {
        executorService.execute {
            mFavoritesDao.delete(favorite)
        }
    }

}