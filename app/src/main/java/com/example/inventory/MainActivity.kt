
package com.example.inventory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.inventory.model.ServiceViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    init{
        val getApi: ViewModel = ServiceViewModel()
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(this, navController)
    }

    /*override fun onStart() {
        super.onStart()
        val viewModel= ServiceViewModel()
        viewModel.getAllUser()
    }*/
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
