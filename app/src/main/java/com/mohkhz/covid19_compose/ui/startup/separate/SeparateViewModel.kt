package com.mohkhz.covid19_compose.ui.startup.separate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohkhz.covid19_compose.data.repo.Repository
import com.mohkhz.covid19_compose.util.Routes
import com.mohkhz.covid19_compose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeparateViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    private val _uiEvent = Channel<UiEvent> { }
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SeparateEvent) {
        when (event) {
            is SeparateEvent.OnNext -> {
                sendUiEvent(UiEvent.Navigate(Routes.HAND_WASH_SCREEN))
            }
        }
    }

    fun saveData() {

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}