package com.mohkhz.covid19_compose.ui.Chooser

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
class ChooserViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    private val _uiEvent = Channel<UiEvent> { }
    var uiEvent = _uiEvent.receiveAsFlow()

    var value: MutableLiveData<String> = MutableLiveData()

    init {
        getData()
    }

    fun onEvent(event: ChooseEvent) {
        when (event) {
            is ChooseEvent.OnHome -> {
                sendUiEvent(UiEvent.Navigate(Routes.HOME_SCREEN))
            }
            is ChooseEvent.OnStartUp -> {
                sendUiEvent(UiEvent.Navigate(Routes.WEAR_MASK_SCREEN))
            }
        }
    }

    fun change(value: String) {

        if (value.equals("true")) {
            onEvent(ChooseEvent.OnHome)
        } else {
            onEvent(ChooseEvent.OnStartUp)
        }
    }

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.readDataStore().collect {
                Log.d("startUp", it)
                value.postValue(it)
            }
        }

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}