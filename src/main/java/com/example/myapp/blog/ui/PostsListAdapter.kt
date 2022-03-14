package com.example.myapp.blog.ui

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.blog.data.PostModel
import com.example.myapp.blog.viewmodel.BlogViewModel
import com.example.myapp.databinding.PostBinding
import com.example.myapp.utils.AuthenticationUtil
import java.text.DateFormat
import java.util.*

class PostsListAdapter: ListAdapter<PostModel, PostsListAdapter.ItemHolder>(ItemComparator()) {
    class ItemHolder (private val binding: PostBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var pref: SharedPreferences
        private lateinit var viewModel: BlogViewModel

        fun bind(post: PostModel) = with(binding) {
            val currentUserId = getUserId()

            postUser.text = post.user.getFullName()
            postBody.text = post.body
            postDate.text = post.created_time
            likesCount.text = post.likes.size.toString()
            postLikes.setOnClickListener { like(post.id) }

            setLikes(post.likes.size, post.likes.find { it.id == currentUserId } != null)
        }

        private fun like(id: Int) {
            val token = AuthenticationUtil.getToken(itemView.context)
            val viewModelOwner = itemView.findViewTreeViewModelStoreOwner()

            if (token != null && viewModelOwner != null) {
                viewModel = ViewModelProvider(viewModelOwner)[BlogViewModel::class.java]
                viewModel.likePost(token, id)
                viewModel.postLiveData.observe(itemView.context as LifecycleOwner, { it ->
                    if (it?.data != null) {
                        val currentUserId = getUserId()
                        setLikes(it.data.likes.size, it.data.likes.find { it.id == currentUserId } != null)
                    }
                })
            }
        }

        private fun getUserId(): Int {
            pref = binding.root.context.getSharedPreferences("user", Context.MODE_PRIVATE)
            return pref.getInt("id", -1)
        }

        private fun setLikes(count: Int, liked: Boolean) = with(binding) {
            likesCount.text = count.toString()
            postLikes.background =
                if (liked)
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