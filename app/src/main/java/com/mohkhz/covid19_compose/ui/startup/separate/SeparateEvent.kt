package com.mohkhz.covid19_compose.ui.startup.separate

import com.mohkhz.covid19_compose.ui.startup.wear_mask.WearMaskEvent

sealed class SeparateEvent {
    object OnNext : SeparateEvent()
}
