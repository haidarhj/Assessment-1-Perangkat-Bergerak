package org.d3if0113.timer

import MainFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        // Create an instance of your fragment
        val fragment = MainFragment()

        // Get the FragmentManager
        val fragmentManager = supportFragmentManager

        // Begin a new FragmentTransaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the container with the fragment
        fragmentTransaction.replace(R.id.fragment_container, fragment)

        // Commit the transaction
        fragmentTransaction.commit()
    }
}
