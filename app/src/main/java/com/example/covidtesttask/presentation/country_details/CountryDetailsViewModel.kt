package com.example.covidtesttask.presentation.country_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidtesttask.common.Resource
import com.example.covidtesttask.domain.usecase.GetCountryDetailsUseCase
import com.example.covidtesttask.presentation.country_details.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCountryDetailsUseCase: GetCountryDetailsUseCase
) : ViewModel() {

    private val countryCode: String = checkNotNull(savedStateHandle["countryCode"])

    private val _uiState = mutableStateOf(CountryDetailsState(
        country = Country(countryCode = countryCode)
    ))
    val uiState: State<CountryDetailsState> = _uiState

    init {
        updateData()
    }

    fun updateData() {
        getCountryDetailsUseCase(countryCode).onEach { resource ->
            withContext(viewModelScope.coroutineContext) {
                when(resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            refreshing = true
                        )
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            refreshing = false,
                            country = Country.fromCountryDetails(resource.data!!)
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            refreshing = false
                        )
                    }
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}