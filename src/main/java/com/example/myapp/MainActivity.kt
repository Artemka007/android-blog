package com.example.myapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.blog.ui.BlogFragment
import com.example.myapp.fragments.HomeFragment
import com.example.myapp.account.components.login.ui.LoginFragment
import com.example.myapp.account.components.logout.LogoutFragment
import com.example.myapp.account.data.UserModel
import com.example.myapp.account.ui.AccountFragment
import com.example.myapp.account.viewmodel.AccountViewModel
import com.example.myapp.utils.AuthenticationUtil
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var accountViewModel: AccountViewModel

    private lateinit var pref: SharedPreferences

    var userIsAuthenticated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val token = AuthenticationUtil.getToken(this)

        navView = findViewById(R.id.nav_view)
        pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        userIsAuthenticated = token != null

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setNavigation(drawerLayout)
        getProfile(token)

        onFragmentChange()

        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks(), true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeContent(fragment: Fragment): Unit {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragments_view, fragment, null)
            .commit()
    }

    private fun onFragmentChange() {
        navView.menu.findItem(R.id.nav_profile_login).isVisible = !userIsAuthenticated
        navView.menu.findItem(R.id.nav_profile_register).isVisible = !userIsAuthenticated
        navView.menu.findItem(R.id.nav_profile).isVisible = userIsAuthenticated
    }

    private fun onAccountResponse(data: UserModel) {
        val editor = pref.edit()
        editor.putInt("id", data.id)
        editor.putString("username", data.getFullName())
        editor.apply()
    }

    private fun getProfile(token: String?) {
        if (token is String) {
            accountViewModel.getProfile(token)
            accountViewModel.accountLiveData.observe(this, {
                if (it != null) {
                    val data = it.data
                    if (data != null) {
                        onAccountResponse(data)
                    } else {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                } else
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setNavigation(drawerLayout: DrawerLayout) {
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> this.changeContent(HomeFragment())
                R.id.nav_blog -> this.changeContent(BlogFragment())
                R.id.nav_contacts -> Toast.makeText(applicationContext, "Contacts", Toast.LENGTH_SHORT).show()
                R.id.nav_profile -> this.changeContent(AccountFragment())
                R.id.nav_profile_logout -> this.changeContent(LogoutFragment())
                R.id.nav_profile_login -> this.changeContent(LoginFragment())
                R.id.nav_profile_register -> Toast.makeText(applicationContext, "Register", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}

class FragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks() {
    private var userIsAuthenticated: Boolean = false

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)

        userIsAuthenticated = AuthenticationUtil.getToken(v.context) != null

        val navView = v.rootView.findViewById<NavigationView>(R.id.nav_view)

        navView.menu.findItem(R.id.nav_profile_login).isVisible = !userIsAuthenticated
        navView.menu.findItem(R.id.nav_profile_register).isVisible = !userIsAuthenticated
        navView.menu.findItem(R.id.nav_profile).isVisible = userIsAuthenticated
        navView.menu.findItem(R.id.nav_profile_logout).isVisible = userIsAuthenticated
    }
}
