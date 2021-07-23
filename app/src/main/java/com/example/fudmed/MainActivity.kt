package com.example.fudmed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.fudmed.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // to find the nav controller from the nav host fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // for bottom navigation

        binding.bottomNavigation.setupWithNavController(navController)

        //for appbar
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                    supportActionBar?.hide()
                }
                R.id.homeFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                R.id.appointmentsFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                R.id.healthUpdatesFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                    supportActionBar?.show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}