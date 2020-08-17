package com.example.gotravel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setUpBottomNavMenu(navController)
        //   setUpActionBar(navController)

    }

    private fun setUpBottomNavMenu(navController: NavController) {
        bottom_navigation?.let {
            NavigationUI.setupWithNavController(it, navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment,
                R.id.createAccountFragment,
                R.id.tripDetailsFragment,
                R.id.seeTripPlanFragment,
                R.id.readReviewsFragment,
                R.id.askAQuestionFragment,
                R.id.bookNowFragment -> hideBottomNavigationBar()
                else -> showBottomNavigationBar()
            }
        }
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


    /*override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(drawer)
    } */

}