package com.example.gotravel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.gotravel.common.model.User
import com.example.gotravel.data.AppPreferences
import com.example.gotravel.data.DefaultUserRepository
import com.example.gotravel.data.GoTravelDatabase
import com.example.gotravel.presentation.auth.create_account.CreateAccountViewModel
import com.example.gotravel.presentation.auth.create_account.CreateAccountViewModelFactory
import com.example.gotravel.presentation.auth.login.LoginViewModel
import com.example.gotravel.presentation.auth.login.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var createAccountViewModel: CreateAccountViewModel
    private lateinit var createAccountViewModelFactory: CreateAccountViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setUpBottomNavMenu(navController)

        setUpViewModel()
    }

    private fun setUpBottomNavMenu(navController: NavController) {
        bottom_navigation?.let {
            NavigationUI.setupWithNavController(it, navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.login,
                R.id.createAccountFragment,
                R.id.changePasswordFragment,
                R.id.aboutMeFragment,
                R.id.tripDetailsFragment,
                R.id.seeTripPlanFragment,
                R.id.readReviewsFragment,
                R.id.askAQuestionFragment,
                R.id.paymentFragment,
                R.id.messageFragment,
                R.id.bookNowFragment -> hideBottomNavigationBar()
                else -> showBottomNavigationBar()
            }
        }
    }

    private fun setUpViewModel() {
        val sharedPreferences =
            applicationContext.getSharedPreferences(
                "com.example.gotravel.pref",
                Context.MODE_PRIVATE
            )
        val application = this.application
        val dataSource = GoTravelDatabase.invoke(application).userDao()
        val appPreferences = AppPreferences(sharedPreferences)
        val repository = DefaultUserRepository(dataSource, appPreferences)

        viewModelFactory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        createAccountViewModelFactory = CreateAccountViewModelFactory(repository)
        createAccountViewModel = ViewModelProvider(this, createAccountViewModelFactory).get(CreateAccountViewModel::class.java)
        createAccountViewModel.createUser("admin", "admin", "admin@gmail.com", "admin", "Male", "Jan 01, 1990", "admin")

        viewModel.authenticatedUser.observe(this, Observer {
            hideOrShowMenuOptions(it)
        })
    }

    private fun setUpActionBar(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navigated = NavigationUI.onNavDestinationSelected(item, navController)
        return navigated || super.onOptionsItemSelected(item)
    }

    private fun hideBottomNavigationBar() {
        bottom_navigation.visibility = View.GONE
    }

    private fun showBottomNavigationBar() {
        bottom_navigation.visibility = View.VISIBLE
    }


    private fun hideOrShowMenuOptions(authenticatedUser: User?) {

        val menu = bottom_navigation.menu
        if (authenticatedUser != null) {
            menu.findItem(R.id.my_profile).isVisible = true
            menu.findItem(R.id.saved_trips).isVisible = true
        } else {
            menu.findItem(R.id.my_profile).isVisible = false
            menu.findItem(R.id.saved_trips).isVisible = false
        }
    }
}