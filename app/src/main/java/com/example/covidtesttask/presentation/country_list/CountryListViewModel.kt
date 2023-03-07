package com.example.covidtesttask.presentation.country_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidtesttask.common.Resource
import com.example.covidtesttask.domain.model.CountrySummary
import com.example.covidtesttask.domain.model.Summary
import com.example.covidtesttask.domain.usecase.GetSummaryUseCase
import com.example.covidtesttask.presentation.country_list.model.CountryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getSummaryUseCase: GetSummaryUseCase
) : ViewModel() {

    private var countryList: List<CountrySummary> = emptyList()

    private val _uiState: MutableState<CountryListState> = mutableStateOf(CountryListState())
    val uiState: State<CountryListState> = _uiState

    init {
        getSummary()
    }

    fun refreshData() {
        getSummary()
    }

    fun search(searchQuery: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = searchQuery,
            countries = performSearch(searchQuery).map { CountryItem.fromCountry(it) }
        )
    }

    private fun getSummary() {
        getSummaryUseCase().onEach { resource ->
            withContext(viewModelScope.coroutineContext) {
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            lastUpdated = null,
                            countries = emptyList(),
                        )
                    }
                    is Resource.Success -> {
                        updateData(resource.data!!)
                    }
                    is Resource.Error -> {
                        // TODO: display error
                    }
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun updateData(summary: Summary) {
        countryList = summary.countries
        _uiState.value = _uiState.value.copy(
            lastUpdated = summary.lastUpdated,
            countries = performSearch(_uiState.value.searchQuery).map { CountryItem.fromCountry(it) }
        )
    }

    private fun performSearch(searchQuery: String): List<CountrySummary> {
        return countryList.filter {
            it.name.lowercase().contains(searchQuery.lowercase())
                    || it.code.lowercase().contains(searchQuery.lowercase())
        }
    }
}