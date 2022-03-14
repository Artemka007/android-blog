package com.example.myapp.account.components.logout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapp.R
import com.example.myapp.account.components.login.ui.LoginFragment
import java.io.File

class LogoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logout, container, false)

        val file = File(view.context.filesDir, "token.txt")
        file.delete()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragments_view, LoginFragment(), null)
            .commit()

        return view
    }
}