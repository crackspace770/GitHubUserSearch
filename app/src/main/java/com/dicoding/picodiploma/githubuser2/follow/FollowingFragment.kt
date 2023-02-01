package com.dicoding.picodiploma.githubuser2.follow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubuser2.profile.ProfileActivity
import com.dicoding.picodiploma.githubuser2.profile.ProfileViewModel
import com.dicoding.picodiploma.githubuser2.adapter.UserAdapter
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.profile.UserProfile
import com.dicoding.picodiploma.githubuser2.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {

    private lateinit var detailViewModel: ProfileViewModel
    private lateinit var binding: FragmentFollowingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = arguments?.getString(ProfileActivity.EXTRA_USER)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ProfileViewModel::class.java)

        if (user != null) {
            detailViewModel.findFollowing(user)
        }
        detailViewModel.listFollowing.observe(viewLifecycleOwner, {
                listFollowing -> setFollowingData(listFollowing)
            showLoading(false)
        })
        detailViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.setHasFixedSize(true)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFollowingData(listFollowing: List<FollowingResponseItem>) {
        val listUser: ArrayList<UserProfile> = ArrayList()
        showLoading(true)
        for (user in listFollowing) {
            val userList = UserProfile(user.login, user.avatarUrl)
            listUser.add(userList)
        }
        val adapter = UserAdapter(listUser)
        binding.rvFollowing.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserProfile) {
                showSelectedUser(data, favorite = Favorite())
            }
        })
    }

    private fun showSelectedUser(user: UserProfile, favorite: Favorite) {
        val moveWithObjectIntent = Intent(activity, ProfileActivity::class.java)
        moveWithObjectIntent.putExtra(ProfileActivity.EXTRA_USER, user)
        moveWithObjectIntent.putExtra(ProfileActivity.EXTRA_FAVORITE, favorite)
        startActivity(moveWithObjectIntent)
    }

    companion object {
        var TAG = "FollowingFragment"
        const val EXTRA_USER = "extra_user"
        @JvmStatic
        fun newInstance(string: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USER, string)
                }
            }
    }
}