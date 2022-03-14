package com.example.myapp.account.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.account.data.UserModel
import com.example.myapp.account.network.AccountRepository
import com.example.myapp.account.viewmodel.AccountViewModel
import com.example.myapp.blog.data.PostModel
import com.example.myapp.blog.ui.PostItemDecoration
import com.example.myapp.blog.ui.PostsListAdapter
import com.example.myapp.databinding.FragmentAccountBinding
import com.example.myapp.databinding.FragmentBlogBinding
import com.example.myapp.network.BASE_URL
import com.example.myapp.utils.AuthenticationUtil
import com.squareup.picasso.Picasso

class AccountFragment : Fragment() {
    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: FragmentAccountBinding
    private lateinit var adapter: PostsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val token = AuthenticationUtil.getToken(requireContext())

        binding = FragmentAccountBinding.bind(view)

        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        if (token != null) {
            viewModel.getProfile(token)
            viewModel.accountLiveData.observe(viewLifecycleOwner, {
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

            viewModel.getProfilePosts(token)
            viewModel.blogLiveData.observe(viewLifecycleOwner, {
                if (it != null) {
                    val data = it.data
                    if (data != null) {
                        onPostsResponse(data)
                    } else {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                } else
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            })
        }

        return view
    }

    private fun onResponse(data: UserModel) = with(binding) {
        val photo = data.photo

        profileUsername.text = data.username

        "${data.first_name} ${data.last_name}".also { value -> profileFullName.text = value }

        if (photo is String) {
            Picasso.get().load(BASE_URL + photo).into(profilePhoto)
        }

        profileInformation.visibility = View.VISIBLE
        profileLoader.visibility = View.INVISIBLE
    }

    private fun onPostsResponse(data: List<PostModel>) = with(binding) {
        initializePostsRecyclerView()
        adapter.submitList(data)
    }

    private fun initializePostsRecyclerView() = with(binding) {
        adapter = PostsListAdapter()
        postsList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postsList.adapter = adapter
        postsList.addItemDecoration(PostItemDecoration(100))
    }
}