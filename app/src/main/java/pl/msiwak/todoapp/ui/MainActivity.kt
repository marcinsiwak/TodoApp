package pl.msiwak.todoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import pl.msiwak.todoapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = navHost.navController
    }
}