package com.mohkhz.covid19_compose.ui.AddFavorite

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohkhz.covid19_compose.data.db.FavoriteItem
import com.mohkhz.covid19_compose.data.model.AllCountry.AllCountry
import com.mohkhz.covid19_compose.data.repo.Repository
import com.mohkhz.covid19_compose.util.Resource
import com.mohkhz.covid19_compose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFavoriteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _list = MutableStateFlow<List<FavoriteItem>>(emptyList())
    var list: StateFlow<List<FavoriteItem>> = _list

    private var myList: List<FavoriteItem> = emptyList()

    private var _visibilityProgressBar = MutableStateFlow<Boolean>(true)
    var visibilityProgressBar: StateFlow<Boolean> = _visibilityProgressBar

    private var _visibilitySearchField = MutableStateFlow<Boolean>(false)
    var visibilitySearchField: StateFlow<Boolean> = _visibilitySearchField

    var cities = repository.getCityList()

    var searchBox by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddFavoriteEvent) {

        when (event) {
            is AddFavoriteEvent.AddNew -> {
                viewModelScope.launch {
                    event.favoriteItem.let {
                        repository.insertFavorite(it)
                        sendUiEvent(UiEvent.PopBackStack)
                    }
                }

            }
            is AddFavoriteEvent.OnSearchBoxChange -> {
                searchBox = event.txt
                searching(event.txt)
            }
            is AddFavoriteEvent.OnSearchClicked -> {
                _visibilitySearchField.value = !_visibilitySearchField.value
            }
        }

    }

    fun getCountries() {

        viewModelScope.launch {

            var response = repository.getCountries()

            when (response) {
                is Resource.Error -> {
                    Log.d("add favarit view model", "response : ${response.message}")
                }
                is Resource.Success -> {
                    if (response.data != null) {
                        cities.collect {
                            Log.d("add favorite ", ": ${it.size}")

                            response.data!!.sortedWith(compareBy { it.country })

                            val sortedCities = it.sortedWith(compareBy { i -> i.cityName })


                            var favItem = mutableListOf<FavoriteItem>()
                            var saveCounter = 0

                            for (i in sortedCities.indices) {
                                Log.d("add favorite :", " $i ")

                                while (saveCounter < response.data!!.size) {

                                    if (response.data!![saveCounter].country.equals(
                                            sortedCities[i].cityName,
                                            ignoreCase = true
                                        )
                                    ) {
                                        response.data!!.removeAt(saveCounter)

                                        break
                                    }

                                    saveCounter++
                                }

                            }

                            for (item in response.data!!) {
                                favItem.add(
                                    FavoriteItem(
                                        null,
                                        item.country,
                                        item.countryInfo.flag,
                                        false
                                    )
                                )
                            }

                            _list.value = favItem
                            myList = favItem
                            if (_list.value != null && _list.value.size > 0) {
                                _visibilityProgressBar.value = false
                            }
                        }


//                        var test = mutableListOf<FavoriteItem>()
//                        for (item in response.data!!) {
//
//                            test.add(
//                                FavoriteItem(
//                                    null,
//                                    item.country,
//                                    item.countryInfo.flag,
//                                    false
//                                )
//                            )
//
//                        }

//                        _list.value = test
//                        myList = test


                    }

                }
            }


        }

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun searching(txt: String) {


        if (txt.equals("")) {
            _list.value = myList
            return
        }

        var toShowList = mutableListOf<FavoriteItem>()
        for (city in myList) {
            if (city.cityName.lowercase().contains(txt.lowercase())) {
                Log.d("awd", city.cityName)
                toShowList.add(city)
            }
        }
        _list.value = toShowList

    }

    private fun test(cities: List<FavoriteItem>, response: AllCountry): List<FavoriteItem> {
        response.sortBy {
            it.country
        }

        cities.sortedBy { it.cityName }

        var favItem = mutableListOf<FavoriteItem>()

        for (i in cities.indices) {
            for (j in response.indices) {
                if (response[j].country.equals(cities[i].cityName, ignoreCase = true)) {
                    response.removeAt(j)
                    break
                }
                if (i == cities.size - 1) {
                    favItem.add(
                        FavoriteItem(
                            null,
                            response[j].country,
                            response[j].countryInfo.flag,
                            false
                        )
                    )
                }
            }

        }


        return favItem
    }

}

