package com.example.covidtesttask.presentation

sealed class Screen(val route: String) {
    object CountryListScreen: Screen("country_list_screen")
    object CountryDetailsScreen: Screen("country_details_screen")
}
