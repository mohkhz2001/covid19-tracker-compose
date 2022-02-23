package com.mohkhz.covid19_compose.ui.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohkhz.covid19_compose.data.db.FavoriteItem
import com.mohkhz.covid19_compose.data.model.Covid.TotalStatistics
import com.mohkhz.covid19_compose.data.repo.Repository
import com.mohkhz.covid19_compose.util.DispatcherProvider
import com.mohkhz.covid19_compose.util.Resource
import com.mohkhz.covid19_compose.util.Routes
import com.mohkhz.covid19_compose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: Repository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val favoriteCities = repo.getCityList()

    private val _uiEvent = Channel<UiEvent> { }
    val uiEvent = _uiEvent.receiveAsFlow()

    sealed class LocationEvent {
        class Success(val resultText: String) : LocationEvent()
        class Failure(val errorText: String) : LocationEvent()
        object Loading : LocationEvent()
        object Empty : LocationEvent()
    }

    sealed class GlobalDataEvent {
        class Success(val resultText: String) : GlobalDataEvent()
        class Failure(val errorText: String) : GlobalDataEvent()
        object Loading : GlobalDataEvent()
        object Empty : GlobalDataEvent()
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.OnAddNewFavorite -> {
                    sendUiEvent(UiEvent.Navigate(Routes.ADD_FAVORITE_SCREEN))
                }
                is HomeEvent.OnDeleteItem -> {
                    repo.deleteCity(event.favoriteItem)
                }
                is HomeEvent.OnFavoriteClick -> {
                    sendUiEvent(UiEvent.Navigate(Routes.COUNTRY_DETAIL + "?countryName=${event.favoriteItem.cityName}"))
                }
            }
        }
    }

    private val _conversion = MutableStateFlow<LocationEvent>(LocationEvent.Empty)
    val conversion: StateFlow<LocationEvent> = _conversion

    private val _globalEventConversion = MutableStateFlow<GlobalDataEvent>(GlobalDataEvent.Empty)
    val globalEventConversion: StateFlow<GlobalDataEvent> = _globalEventConversion

    private val _globalData = MutableStateFlow<TotalStatistics?>(null)
    val globalData: StateFlow<TotalStatistics?> = _globalData

    private val _visibilityProgressBar = MutableStateFlow<Boolean>(true)
    val visibilityProgressBar: StateFlow<Boolean> = _visibilityProgressBar

    fun location() {
        viewModelScope.launch(dispatchers.io) {
            _conversion.value = LocationEvent.Loading
            val response = repo.getCountryName()
            when (response) {

                is Resource.Error -> {
                    Log.d("Homeview model", "location : ${response.message}")
                    _conversion.value = LocationEvent.Failure(response.message!!)
                }
                is Resource.Success -> {
                    val name = response.data!!.country_name
                    repo.insertFavorite(
                        FavoriteItem(
                            1,
                            response.data.country_name,
                            response.data.country_flag,
                            true
                        )
                    )
                    _visibilityProgressBar.value = false
                    _conversion.value = LocationEvent.Success("location: $name")
                    Log.d("Homeview model", "location : $name")
                }

            }
        }

    }

    fun getGlobalData() {
        viewModelScope.launch(Dispatchers.IO) {
            _globalEventConversion.value = GlobalDataEvent.Loading
            val response = repo.getGlobalData()

            when (response) {
                is Resource.Error -> {
                    Log.d("Homeview model", "global data : ${response.message}")
                    _globalEventConversion.value = GlobalDataEvent.Failure(response.message!!)
                }
                is Resource.Success -> {
                    response.data.let {
                        _globalData.value = it!!
                    }
                    _globalEventConversion.value = GlobalDataEvent.Success("")
                }
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}