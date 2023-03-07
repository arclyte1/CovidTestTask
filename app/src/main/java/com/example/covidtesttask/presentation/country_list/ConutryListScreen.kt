package com.example.covidtesttask.presentation.country_list

import android.icu.text.CompactDecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.covidtesttask.presentation.country_list.model.CountryItem
import com.murgupluoglu.flagkit.FlagKit
import com.example.covidtesttask.R
import com.example.covidtesttask.presentation.Screen
import com.example.covidtesttask.presentation.country_list.components.CountryCard
import com.example.covidtesttask.presentation.country_list.components.SearchField

@Composable
fun CountryListScreen(
    navController: NavController,
    viewModel: CountryListViewModel = hiltViewModel()
) {
    val viewState = viewModel.uiState
    Box {
        // TODO: last updated
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 80.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
        ) {
            items(viewState.value.countries) { countryItem ->
                CountryCard(
                    country = countryItem,
                    modifier = Modifier.padding(8.dp).clickable {
                        navController.navigate(
                            Screen.CountryDetailsScreen.route + "/${countryItem.code}"
                        )
                    }
                )
            }
        }
        SearchField(
            searchText = viewState.value.searchQuery,
            performSearch = viewModel::search,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                .background(Color.White, RoundedCornerShape(50))
        )
    }
}