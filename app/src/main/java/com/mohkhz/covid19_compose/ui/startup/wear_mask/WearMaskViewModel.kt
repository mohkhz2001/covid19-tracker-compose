package com.mohkhz.covid19_compose.ui.startup.wear_mask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohkhz.covid19_compose.data.repo.Repository
import com.mohkhz.covid19_compose.util.Routes
import com.mohkhz.covid19_compose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WearMaskViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    private val _uiEvent = Channel<UiEvent> { }
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: WearMaskEvent) {
        when (event) {
            is WearMaskEvent.OnNext -> {
                saveData()
                getData()
                sendUiEvent(UiEvent.Navigate(Routes.SEPARATE_SCREEN))
            }
        }
    }

    private fun saveData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateDataStore("", true)
        }
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.readDataStore().collect {
                Log.d("wearmask", it)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}