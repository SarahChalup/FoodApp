  package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

  class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val bottonNavigation = findViewById<BottomNavigationView>(R.id.Btn_buttonNav)
        val navController = Navigation.findNavController(this, R.id.fg_fragment)

        NavigationUI.setupWithNavController(bottonNavigation, navController)
    }
}