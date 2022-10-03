package com.akhmadaldi.githubuserappfinal.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhmadaldi.githubuserappfinal.data.response.ItemItems
import com.akhmadaldi.githubuserappfinal.databinding.ListUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private val list = ArrayList<ItemItems>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<ItemItems>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userResponse: ItemItems){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(userResponse)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(userResponse.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(user)
                username.text = userResponse.login
                id.text = userResponse.id.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemItems)
    }
}