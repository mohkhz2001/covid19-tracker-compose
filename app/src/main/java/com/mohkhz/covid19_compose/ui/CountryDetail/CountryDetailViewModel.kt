package com.mohkhz.covid19_compose.ui.CountryDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohkhz.covid19_compose.data.model.Country.CountryCovid
import com.mohkhz.covid19_compose.data.repo.Repository
import com.mohkhz.covid19_compose.util.Resource
import com.mohkhz.covid19_compose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var countryName: String? = null

    private val _countryData = MutableStateFlow<CountryCovid?>(null)
    val countryData: StateFlow<CountryCovid?> = _countryData

    init {
        val countryName = savedStateHandle.get<String>("countryName")!!
        Log.d("countryName", " is $countryName")
        this.countryName = countryName
    }

    fun onEvent(event: CountryDetailEvent) {
        when (event) {

        }
    }

    fun getCountryData() {

        viewModelScope.launch {
            var response = repository.getCountryData(countryName!!)

            when (response) {
                is Resource.Error -> {
                    Log.d("Homeview model", "location : ${response.message}")
                }
                is Resource.Success -> {
                    val data = response.data

                    if (data != null) {
                        _countryData.value = data
                    }

                }
            }

        }

    }


}