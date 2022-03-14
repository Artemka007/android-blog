package com.example.myapp.account.components.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.R
import com.example.myapp.account.network.AccountRepository
import com.example.myapp.account.components.login.data.LoginModel
import com.example.myapp.account.components.login.viewmodel.LoginViewModel
import com.example.myapp.account.ui.AccountFragment
import com.example.myapp.utils.AuthenticationUtil
import java.io.File

class LoginFragment: Fragment() {
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val button = view.findViewById<Button>(R.id.login_btn)
        button.setOnClickListener { onSubmit() }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        usernameField = view.findViewById(R.id.login_username)
        passwordField = view.findViewById(R.id.login_password)

        val token = AuthenticationUtil.getToken(requireContext())
        if (token is String) redirect()

        return view
    }

    private fun onSubmit() {
        val username = usernameField.text.toString()
        val password = passwordField.text.toString()
        val loginModel = LoginModel(username, password)

        viewModel.loginUser(loginModel)

        viewModel.loginModelLiveData.observe(this.viewLifecycleOwner, {
            if (it != null) {
                val token = it.data.token
                if (token is String)
                    onResponse(token)
            } else {
                Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun onResponse(token: String) {
        val file = File(requireContext().filesDir, "token.txt")
        val isFileCreated: Boolean = file.createNewFile()
        file.writeText(token)
        redirect()
    }

    private fun redirect() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragments_view, AccountFragment(), null)
            .commit()
    }
}