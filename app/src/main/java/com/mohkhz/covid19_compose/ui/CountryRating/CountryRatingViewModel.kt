package com.mohkhz.covid19_compose.ui.CountryRating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohkhz.covid19_compose.data.model.AllCountry.AllCountry
import com.mohkhz.covid19_compose.data.repo.Repository
import com.mohkhz.covid19_compose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryRatingViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _ratingData = MutableStateFlow<AllCountry?>(null)
    val ratingData: StateFlow<AllCountry?> = _ratingData

    fun onEvent(event: CountryRatingEvent) {
        when (event) {
            is CountryRatingEvent.OnCasesClick -> {
                sortByCases()
            }
            is CountryRatingEvent.OnDeathClick -> {
                sortByDeath()
            }
            is CountryRatingEvent.OnRecoveredClick -> {
                sortByRecovered()
            }
        }
    }

    fun getData() {
        viewModelScope.launch {
            val response = repository.getRatingData()

            when (response) {
                is Resource.Error -> {

                }
                is Resource.Success -> {
                    var list = response.data
                    _ratingData.value = list

                    sortByCases() // default

                }
            }

        }
    }

    private fun sortByCases() {
        val list = _ratingData.value
        list?.sortByDescending { it.cases }
        _ratingData.value = list
    }

    private fun sortByDeath() {
        val list = _ratingData.value
        list?.sortByDescending { it.deaths }
        _ratingData.value = list
    }

    private fun sortByRecovered() {
        val list = _ratingData.value
        list?.sortByDescending { it.recovered }
        _ratingData.value = list
    }

}