package com.example.covidtesttask.presentation.country_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.covidtesttask.R
import com.example.covidtesttask.presentation.country_details.components.CasesRow
import com.murgupluoglu.flagkit.FlagKit
import java.text.SimpleDateFormat

@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    val viewState = viewModel.uiState
    val dateFormatter = SimpleDateFormat("MMM dd", LocalConfiguration.current.locales[0])
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
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }
        }
        items(viewState.value.country.history) { cases ->
            if (cases.date != null) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = dateFormatter.format(cases.date),
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                        color = Color.Gray
                    )
                    CasesRow(
                        title = stringResource(id = R.string.confirmed),
                        total = cases.totalConfirmed,
                        new = cases.newConfirmed
                    )
                    CasesRow(
                        title = stringResource(id = R.string.recovered),
                        total = cases.totalRecovered,
                        new = cases.newRecovered
                    )
                    CasesRow(
                        title = stringResource(id = R.string.deaths),
                        total = cases.totalDeaths,
                        new = cases.newDeaths
                    )
                    Divider(modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                }
            }
        }
    }
}