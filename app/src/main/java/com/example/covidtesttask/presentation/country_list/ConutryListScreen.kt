package com.example.covidtesttask.presentation.country_list

import android.icu.text.CompactDecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.covidtesttask.presentation.country_list.model.CountryItem
import com.murgupluoglu.flagkit.FlagKit
import com.example.covidtesttask.R

@Composable
fun CountryListScreen(
    viewModel: CountryListViewModel = hiltViewModel()
) {
    val viewState = remember { viewModel.uiState }
    Box {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 80.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
        ) {
            items(viewState.value.countries) { countryItem ->
                CountryCard(
                    country = countryItem,
                    modifier = Modifier.padding(8.dp)
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

@Composable
fun SearchField(
    searchText: String,
    performSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { text ->
            performSearch(text)
        },
        shape = RoundedCornerShape(50),
        placeholder = {
            Text(text = "Search...", color = Color.Gray)
        },
        modifier = modifier
    )
}

@Composable
fun CountryCard(
    country: CountryItem,
    modifier: Modifier = Modifier
) {
    var drawableId = FlagKit.getResId(
        context = LocalContext.current,
        flagName = country.code
    )
    if (drawableId == 0) drawableId = R.drawable.unknown_flag
    Card(
        modifier = modifier
    ) {
        Column {
            Image(
                painter = painterResource(
                    id = drawableId
                ),
                contentDescription = "Flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = country.displayName,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
            CountryCardStatistic(
                title = "Confirmed",
                total = country.totalConfirmed,
                new = country.newConfirmed,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            CountryCardStatistic(
                title = "Recovered",
                total = country.totalRecovered,
                new = country.newRecovered,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            CountryCardStatistic(
                title = "Deaths",
                total = country.totalDeaths,
                new = country.newDeaths,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun CountryCardStatistic(
    title: String,
    total: Int,
    new: Int,
    modifier: Modifier = Modifier,
) {
    val numberFormat = CompactDecimalFormat.getInstance(
        LocalConfiguration.current.locales[0],
        CompactDecimalFormat.CompactStyle.SHORT
    )
    Row(
        modifier = modifier
    ) {
        Text(text = title, fontSize = 14.sp)
        Text(
            text = "${numberFormat.format(total)} (+${numberFormat.format(new)})",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            fontSize = 14.sp
        )
    }
}