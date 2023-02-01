package com.dicoding.picodiploma.githubuser2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.githubuser2.follow.FollowerFragment
import com.dicoding.picodiploma.githubuser2.follow.FollowingFragment

class PagerAdapter(activity: AppCompatActivity, private val login: String): FragmentStateAdapter(activity)  {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.newInstance(login)
            1 -> fragment = FollowerFragment.newInstance(login)
        }
        return fragment as Fragment
    }

}