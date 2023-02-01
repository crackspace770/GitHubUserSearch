package com.dicoding.picodiploma.githubuser2.main


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser2.R
import com.dicoding.picodiploma.githubuser2.adapter.UserAdapter
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.databinding.ActivityMainBinding
import com.dicoding.picodiploma.githubuser2.favorite.FavoriteActivity
import com.dicoding.picodiploma.githubuser2.favorite.FavoriteViewModel
import com.dicoding.picodiploma.githubuser2.favorite.FavoriteViewModelFactory
import com.dicoding.picodiploma.githubuser2.profile.ProfileActivity
import com.dicoding.picodiploma.githubuser2.profile.ProfileViewModel
import com.dicoding.picodiploma.githubuser2.profile.UserProfile
import com.dicoding.picodiploma.githubuser2.setting.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var detailViewModel: ProfileViewModel
    private lateinit var adapter: UserAdapter

    private var title : String = "List User"
    private var favorite: Favorite? = Favorite()
    private val listUser: ArrayList<UserProfile> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        //viewModel Favorite
        val favoriteViewModel = obtainViewModel(this@MainActivity)

        //binding recylerview
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.listUser.observe(this) { searchResult ->
            setSearchData(searchResult)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoriteViewModel.getAllFavorites().observe(this) { favoriteList ->
            if (favoriteList != null) {
                adapter = UserAdapter(listUser)
                adapter.setListFavorites(favoriteList)
            }
        }

        //aksi setelah button diclick
        binding.btnSend.setOnClickListener { view ->
            mainViewModel.findUser(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

    //bila meng-click user
    private fun showSelectedUser(user: UserProfile, favorite: Favorite) {
       Toast.makeText(this, "You Choose " + user.login, Toast.LENGTH_SHORT).show()

        val moveWithObjectIntent = Intent(this@MainActivity, ProfileActivity::class.java)

        moveWithObjectIntent.putExtra(ProfileActivity.EXTRA_FAVORITE, favorite)
        moveWithObjectIntent.putExtra(ProfileActivity.EXTRA_USER, user)

        startActivity(moveWithObjectIntent)
        detailViewModel = ProfileViewModel()
    }

    //data search
    private fun setSearchData(searchResult: List<SearchItem>) {
        val listUser: ArrayList<UserProfile> = ArrayList()
        for (user in searchResult) {
            val userList = UserProfile(user.login, user.avatarUrl, favorite?.isFavorite)
            listUser.add(userList)
        }
        val adapter = UserAdapter(listUser)
        binding.rvUsers.adapter = adapter
        binding.edReview.setText("")
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserProfile) {
                showSelectedUser(data, favorite = Favorite())
            }
        })
    }

    //progress bar
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun setActionBarTitle(title : String) {
        supportActionBar?.title = title

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //memilih menu di option bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingsMenu -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.favoritesMenu -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }


}