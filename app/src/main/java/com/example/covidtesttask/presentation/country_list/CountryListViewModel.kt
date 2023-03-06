package com.example.covidtesttask.presentation.country_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidtesttask.common.Resource
import com.example.covidtesttask.domain.model.Summary
import com.example.covidtesttask.domain.usecase.GetSummaryUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CountryListViewModel @Inject constructor(
    private val getSummaryUseCase: GetSummaryUseCase
) : ViewModel() {

    private val _uiState: MutableState<CountryListState> = mutableStateOf(CountryListState())
    val uiState: State<CountryListState> = _uiState

    init {
        getSummary()
    }

    fun refreshData() {
        getSummary()
    }

    fun search() {

    }

    private fun getSummary() {
        getSummaryUseCase().onEach { resource ->
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
        }.launchIn(viewModelScope)
    }

    private fun updateData(summary: Summary) {
        _uiState.value = CountryListState.fromSummary(summary)
    }

    private fun performSearch(searchQuery: String) {

    }
}