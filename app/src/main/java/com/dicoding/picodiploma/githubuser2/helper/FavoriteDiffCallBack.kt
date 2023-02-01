package com.dicoding.picodiploma.githubuser2.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.picodiploma.githubuser2.database.Favorite

class FavoriteDiffCallBack(private val mOldFavoriteList: List<Favorite>,
                           private val mNewFavoriteList: List<Favorite>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].login == mNewFavoriteList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavoriteList[oldItemPosition]
        val newEmployee = mNewFavoriteList[newItemPosition]
        return oldEmployee.login == newEmployee.login
                && oldEmployee.avatar == newEmployee.avatar
                && oldEmployee.isFavorite == newEmployee.isFavorite
    }




}