package com.example.myapp.blog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.account.network.AccountRepository
import com.example.myapp.blog.data.PostModel
import com.example.myapp.blog.viewmodel.BlogViewModel
import com.example.myapp.databinding.FragmentBlogBinding
import com.example.myapp.utils.AuthenticationUtil

class BlogFragment : Fragment() {
    private lateinit var viewModel: BlogViewModel
    private lateinit var adapter: PostsListAdapter
    private lateinit var binding: FragmentBlogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blog, container, false)
        val token = AuthenticationUtil.getToken(requireContext())

        binding = FragmentBlogBinding.bind(view)

        viewModel = ViewModelProvider(this)[BlogViewModel::class.java]

        if (token != null) {
            viewModel.getAllPosts(token)
            viewModel.blogLiveData.observe(viewLifecycleOwner, {
                if (it != null) {
                    val data = it.data
                    if (data != null) {
                        onResponse(data)
                    } else {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                } else
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            })
        }

        return view
    }

    private fun onResponse(data: List<PostModel>) = with(binding) {
        initializeRecyclerView()
        adapter.submitList(data)
        postsLoader.visibility = ProgressBar.INVISIBLE
    }

    private fun initializeRecyclerView() = with(binding) {
        adapter = PostsListAdapter()
        postsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postsList.adapter = adapter
        postsList.addItemDecoration(PostItemDecoration(100))
    }
}