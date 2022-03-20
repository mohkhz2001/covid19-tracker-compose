package com.mohkhz.covid19_compose.ui.startup.hand_wash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mohkhz.covid19_compose.R
import com.mohkhz.covid19_compose.ui.startup.component.StartUpComponent
import com.mohkhz.covid19_compose.util.UiEvent
import kotlinx.coroutines.flow.collect

@ExperimentalPermissionsApi
@Composable
fun HandWashScreen(
    screenHeightDp: Dp,
    screenWidthDp: Dp,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HandWashViewModel = hiltViewModel()
) {


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it)
                }
                else -> Unit
            }
        }
    }

    StartUpComponent(
        screenHeightDp = screenHeightDp,
        screenWidthDp = screenWidthDp,
        headText1 = "wash your hands",
        headText2 = "Regularly",
        description = "Washing your hands with soap \n and water for at least 20s",
        rawRes = R.raw.hands,
        animationBack = R.drawable.wash_hand,
        nextBtn = {
            viewModel.onEvent(HandWashEvent.OnNext)
        }
    )

}