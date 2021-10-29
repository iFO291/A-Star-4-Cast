package com.example.aStar4cast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.aStar4cast.databinding.ActivityMainBinding
import com.example.aStar4cast.ui.weather.WeatherFragment
import com.example.aStar4cast.utils.Communicator
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), Communicator {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_fav, R.id.nav_weather, R.id.nav_info
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun passData(listItem: String) {
        val bundle = Bundle()
        bundle.putString("cityItem", listItem)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentWeather = WeatherFragment()
        fragmentWeather.arguments = bundle
        transaction.replace(R.id.nav_host_fragment_activity_main, fragmentWeather)
        transaction.commit()
    }

    override fun passBool(checkWF: Boolean) {
        val bundle = Bundle()
        bundle.putBoolean("checkWeatherFragment", checkWF)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentWeather = WeatherFragment()
        fragmentWeather.arguments = bundle
        transaction.replace(R.id.nav_host_fragment_activity_main, fragmentWeather)
        transaction.commit()
    }
}