package com.mohkhz.covid19_compose.ui.startup.separate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohkhz.covid19_compose.R
import com.mohkhz.covid19_compose.ui.startup.component.StartUpComponent
import com.mohkhz.covid19_compose.ui.startup.hand_wash.HandWashEvent
import com.mohkhz.covid19_compose.ui.startup.hand_wash.HandWashViewModel
import com.mohkhz.covid19_compose.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun SeparateScreen(
    screenHeightDp: Dp,
    screenWidthDp: Dp,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SeparateViewModel = hiltViewModel()
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
        headText1 = "Keep your",
        headText2 = "Separate",
        description = "keep your separate at least\n2m each person",
        rawRes = R.raw.seperate,
        animationBack = R.drawable.separate,
        nextBtn = {
            viewModel.onEvent(SeparateEvent.OnNext)
        }
    )

}
