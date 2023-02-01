package com.dicoding.picodiploma.githubuser2.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.githubuser2.database.Favorite
import com.dicoding.picodiploma.githubuser2.databinding.UserListBinding
import com.dicoding.picodiploma.githubuser2.helper.FavoriteDiffCallBack
import com.dicoding.picodiploma.githubuser2.profile.ProfileActivity
import com.dicoding.picodiploma.githubuser2.profile.UserProfile

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavorites = ArrayList<Favorite>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: UserProfile)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    //menampilkan recyclerView
    inner class FavoriteViewHolder(private val binding: UserListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite:Favorite) {
            if (favorite.isFavorite == true) {
                binding.tvLogin.text = favorite.login
                Glide.with(itemView.context)
                    .load(favorite.avatar)
                    .into(binding.imgAvatar)
                itemView.setOnClickListener {
                    val intent = Intent(it.context, ProfileActivity::class.java)
                    val user = UserProfile(favorite.login, favorite.avatar)
                    intent.putExtra(ProfileActivity.EXTRA_FAVORITE, favorite)
                    intent.putExtra(ProfileActivity.EXTRA_USER, user)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    //mendapatkan data favorites
    fun setListFavorites(listFavorites: List<Favorite>) {
        val diffCallBack = FavoriteDiffCallBack(this.listFavorites, listFavorites)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorites)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }
}