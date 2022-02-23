package com.mohkhz.covid19_compose.ui.startup.hand_wash

sealed class HandWashEvent {
    object OnNext : HandWashEvent()
}