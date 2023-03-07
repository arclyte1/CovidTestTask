package com.example.covidtesttask.presentation.country_details

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    LazyColumn {
        item {
            //Image(painter = , contentDescription = )
        }
    }
}