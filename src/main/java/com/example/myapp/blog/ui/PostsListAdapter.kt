package com.example.myapp.blog.ui

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.blog.data.PostModel
import com.example.myapp.databinding.PostBinding
import java.text.DateFormat
import java.util.*

class PostsListAdapter: ListAdapter<PostModel, PostsListAdapter.ItemHolder>(ItemComparator()) {
    class ItemHolder (private val binding: PostBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var pref: SharedPreferences

        fun bind(post: PostModel) = with(binding) {
            pref = binding.root.context.getSharedPreferences("user", Context.MODE_PRIVATE)

            val currentUserId = pref.getInt("id", -1)

            postUser.text = post.user.getFullName()
            postBody.text = post.body
            postDate.text = post.created_time
            likesCount.text = post.likes.size.toString()
            postLikes.background =
                if (post.likes.find { it.id == currentUserId } != null)
                    binding.root.context.getDrawable(R.drawable.ic_fill_heart_24)
                else
                    binding.root.context.getDrawable(R.drawable.ic_heart_24)
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(PostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<PostModel>() {
        override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }
}