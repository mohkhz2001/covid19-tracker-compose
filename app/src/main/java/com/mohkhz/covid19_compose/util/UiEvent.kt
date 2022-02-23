package com.mohkhz.covid19_compose.util

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class Navigate(val route: String) : UiEvent()

}