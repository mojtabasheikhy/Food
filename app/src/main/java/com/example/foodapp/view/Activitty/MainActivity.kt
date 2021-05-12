package com.example.foodapp.view.Activitty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var nav_controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Foodapp)
        setContentView(R.layout.activity_main)
        navcontroller()
    }

    fun navcontroller() {
        nav_controller = findNavController(R.id.main_nav_host)
        var appbar_configurration =
            AppBarConfiguration(setOf(R.id.recipes_frg, R.id.favorite_recipes_frg, R.id.joke_frg))
        bottomNavigationView.setupWithNavController(nav_controller)
        setupActionBarWithNavController(nav_controller, appbar_configurration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return nav_controller.navigateUp() || super.onSupportNavigateUp()

    }




}