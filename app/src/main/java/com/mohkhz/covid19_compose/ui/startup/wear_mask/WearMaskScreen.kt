package com.mohkhz.covid19_compose.ui.startup.wear_mask

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohkhz.covid19_compose.ui.startup.component.StartUpComponent
import com.mohkhz.covid19_compose.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun WearMaskScreen(
    screenHeightDp: Dp,
    screenWidthDp: Dp,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: WearMaskViewModel = hiltViewModel()
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
        headText1 = "Wear mask",
        headText2 = "save lives",
        description = "Clearly wear mask reduce \n of the speard Coronavirus",
        nextBtn = {
            viewModel.onEvent(WearMaskEvent.OnNext)
        }
    )

}


@Preview(showBackground = true)
@Composable
fun PreviewWearMAsk() {

}