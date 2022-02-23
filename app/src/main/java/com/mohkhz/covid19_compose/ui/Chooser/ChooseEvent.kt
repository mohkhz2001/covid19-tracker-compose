package com.mohkhz.covid19_compose.ui.Chooser

sealed class ChooseEvent {
    object OnStartUp : ChooseEvent()
    object OnHome : ChooseEvent()
}
