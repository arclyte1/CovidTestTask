package com.example.covidtesttask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.covidtesttask.presentation.country_details.CountryDetailsScreen
import com.example.covidtesttask.presentation.country_list.CountryListScreen
import com.example.covidtesttask.presentation.ui.theme.CovidTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CovidTestTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CountryListScreen.route
                    ) {
                        composable(
                            route = Screen.CountryListScreen.route
                        ) {
                            CountryListScreen(navController)
                        }
                        composable(
                            route = Screen.CountryDetailsScreen.route + "/{countryCode}",
                            arguments = listOf(
                                navArgument("countryCode") { type = NavType.StringType }
                            )
                        ) {
                            CountryDetailsScreen()
                        }
                    }
                }
            }
        }
    }
}