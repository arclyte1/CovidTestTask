package com.example.covidtesttask.presentation.country_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.covidtesttask.R
import com.example.covidtesttask.presentation.Screen
import com.example.covidtesttask.presentation.country_list.components.CountryCard
import com.example.covidtesttask.presentation.country_list.components.SearchField
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryListScreen(
    navController: NavController,
    viewModel: CountryListViewModel = hiltViewModel()
) {
    val viewState by viewModel.uiState
    val dateFormatter = SimpleDateFormat("MMM dd", LocalConfiguration.current.locales[0])
    val pullRefreshState = rememberPullRefreshState(viewState.refreshing, { viewModel.refreshData() })
    Column {
        SearchField(
            searchText = viewState.searchQuery,
            performSearch = viewModel::search,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        viewState.lastUpdated?.let {
            Text(
                text = stringResource(id = R.string.last_update, dateFormatter.format(it)),
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
        }
        Box(modifier = Modifier
            .pullRefresh(pullRefreshState)
            .zIndex(-1f)
            .fillMaxWidth()) {
            if (viewState.countries.isEmpty() && !viewState.refreshing) {
                Text(
                    text = stringResource(id = R.string.nothing_found),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 32.dp),
                    fontSize = 24.sp,
                    color = Color.Gray
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
            ) {
                items(viewState.countries) { countryItem ->
                    CountryCard(
                        country = countryItem,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.CountryDetailsScreen.route + "/${countryItem.code}"
                                )
                            }
                    )
                }
            }
            PullRefreshIndicator(
                viewState.refreshing,
                pullRefreshState, Modifier.align(Alignment.TopCenter),
            )
        }
    }
}