package com.dicoding.picodiploma.githubuser2.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.profile.UserProfile
import com.dicoding.picodiploma.githubuser2.follow.FollowingFragment.Companion.TAG
import com.dicoding.picodiploma.githubuser2.databinding.UserListBinding
import com.dicoding.picodiploma.githubuser2.helper.FavoriteDiffCallBack
import com.dicoding.picodiploma.githubuser2.profile.ProfileActivity
import java.util.ArrayList

class UserAdapter (private val listUser: ArrayList<UserProfile>)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val userAdapter = ArrayList<UserProfile>()
    private val listFavorites = ArrayList<Favorite>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(users:List<UserProfile>){
        userAdapter.clear()
        userAdapter.addAll(users)
    }

    fun setListFavorites(listFavorites: List<Favorite>) {
        val diffCallBack = FavoriteDiffCallBack(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun getItemCount(): Int = listUser.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(var binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserProfile) {
            itemView.setOnClickListener {
                val intent = Intent(it.context, ProfileActivity::class.java)
                val favorite = Favorite()
                intent.putExtra(ProfileActivity.EXTRA_USER, user)
                intent.putExtra(ProfileActivity.EXTRA_FAVORITE, favorite)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (name, avatar) = listUser[position]
        Glide.with(viewHolder.itemView.context)
            .load(avatar)
            .into(viewHolder.binding.imgAvatar)
        viewHolder.binding.tvLogin.text = name
        Log.e(TAG, "onBindViewHolder: login")
        viewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition]) }

    }



    interface OnItemClickCallback {
        fun onItemClicked(data: UserProfile)
    }
}