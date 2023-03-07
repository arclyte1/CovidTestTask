package com.example.covidtesttask.presentation.country_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.covidtesttask.R
import com.murgupluoglu.flagkit.FlagKit

@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    val viewState = viewModel.uiState
    LazyColumn {
        item {
            var drawableId = FlagKit.getResId(
                context = LocalContext.current,
                flagName = viewState.value.country.countryCode
            )
            if (drawableId == 0) drawableId = R.drawable.unknown_flag
            Image(
                painter = painterResource(
                    id = drawableId
                ),
                contentDescription = "Flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
        viewState.value.country.name?.let { name ->
            item {
                Text(name)
            }
        }
    }
}