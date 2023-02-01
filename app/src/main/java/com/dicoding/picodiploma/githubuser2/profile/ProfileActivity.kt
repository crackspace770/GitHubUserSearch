package com.dicoding.picodiploma.githubuser2.profile

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.githubuser2.adapter.PagerAdapter
import com.dicoding.picodiploma.githubuser2.R
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.follow.FollowerFragment
import com.dicoding.picodiploma.githubuser2.follow.FollowingFragment
import com.dicoding.picodiploma.githubuser2.databinding.UserProfileBinding
import com.dicoding.picodiploma.githubuser2.favorite.FavoriteUpdateViewModel
import com.dicoding.picodiploma.githubuser2.favorite.FavoriteViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator


class ProfileActivity: AppCompatActivity() {

    private lateinit var binding: UserProfileBinding
    private lateinit var detailViewModel: ProfileViewModel
    private lateinit var favoriteUpdateViewModel: FavoriteUpdateViewModel

    private var favorite: Favorite? = Favorite()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar

        actionBar?.title = "User Profile"

        val user = intent.getParcelableExtra<UserProfile>(EXTRA_USER) as UserProfile

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ProfileViewModel::class.java)
        user.login?.let { detailViewModel.findUserDetail(it) }


        detailViewModel.detailUser.observe(this) { detailUser ->
            setDetailData(detailUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.snackbarText.observe(this) {
            Snackbar.make(
                window.decorView.rootView,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }

        binding.tvLogin.text = user.login
        Glide.with(this@ProfileActivity)
            .load(user.avatar)
            .into(binding.imgAvatar)

        //follower fragment
        val pagerAdapter = PagerAdapter(this, user.login.toString())
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        favoriteUpdateViewModel = obtainViewModel(this@ProfileActivity)
    }


    //menampilkan option menu/ bar menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)

        return true
    }

    //apa yang terjadi saat item option bar di-click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val user = intent.getParcelableExtra<UserProfile>(EXTRA_USER) as UserProfile
        favorite = intent.getParcelableExtra(EXTRA_FAVORITE)

        val avatar = user.avatar

        var check: Boolean? = favorite?.isFavorite
        var checkAva: String? = favorite?.avatar
        var checkUse: String? = favorite?.login
        var checkFavUser: Boolean? = user.isFavorite

        Log.e("DetailActivityCheck", "isFavorite: $check and $checkFavUser, ava: $checkAva, user: $checkUse")


        when (item.itemId) {
            R.id.addFavorite -> {
                    favorite.let { favorite ->
                        favorite?.login = user.login!!
                        favorite?.avatar = avatar
                        favorite?.isFavorite = true
                    }
                    favoriteUpdateViewModel.insert(favorite as Favorite)
                    user.isFavorite = true
                    Toast.makeText(this, "Added to favorite!", Toast.LENGTH_SHORT).show()
            }

        }
        when (item.itemId) {
            R.id.dltFavorite -> {
                    favorite.let { favorite ->
                        favorite?.isFavorite = false
                    }
                    favoriteUpdateViewModel.delete(favorite as Favorite)
                    user.isFavorite = false
                    Toast.makeText(this, "Deleted from favorite", Toast.LENGTH_SHORT).show()

                return true
            }

            else -> return true
        }
    }

    //Menampilkan profile detail
    private fun setDetailData(detailUser: ProfileResponse) {
        binding.tvFollowings.text = detailUser.following.toString()
        binding.tvFollowers.text = detailUser.followers.toString()

        if (detailUser.name == null) {
            binding.tvFullname.text = detailUser.login
        } else {
            binding.tvFullname.text = detailUser.name
        }
        binding.tvLocation.text = detailUser.location
        binding.tvCompany.text = detailUser.company
        binding.tvRepository.text = detailUser.reposUrl

        val mFollowingFragment = FollowingFragment()
        val mFollowersFragment = FollowerFragment()
        val mBundle = Bundle()
        val mBundle2 = Bundle()
        mBundle.putString(FollowerFragment.EXTRA_USER, detailUser.login)
        mBundle2.putString(FollowingFragment.EXTRA_USER, detailUser.login)
        mFollowingFragment.arguments = mBundle
        mFollowersFragment.arguments = mBundle2

        Log.e(ContentValues.TAG, "showSelectedUser: ${detailUser.login}")
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUpdateViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUpdateViewModel::class.java]
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FAVORITE = "extra_favorite"
        @StringRes
        private  val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_follower
        )
    }
}





