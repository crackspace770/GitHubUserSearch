package com.dicoding.picodiploma.githubuser2.favorite

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.databinding.ActivityFavoriteBinding
import com.dicoding.picodiploma.githubuser2.profile.ProfileActivity
import com.dicoding.picodiploma.githubuser2.profile.UserProfile

class FavoriteActivity:AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Favorite"

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        favoriteViewModel.getAllFavorites().observe(this) { favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavorites(favoriteList)
            }
        }

        adapter = FavoriteAdapter()

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserProfile) {
                showSelectedUser(data, favorite = Favorite())
            }
        })

    }

    private fun showSelectedUser(user: UserProfile, favorite: Favorite) {
        val moveWithObjectIntent = Intent(this@FavoriteActivity, ProfileActivity::class.java)
        moveWithObjectIntent.putExtra(ProfileActivity.EXTRA_USER, user)
        moveWithObjectIntent.putExtra(ProfileActivity.EXTRA_FAVORITE, favorite)
        startActivity(moveWithObjectIntent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

}