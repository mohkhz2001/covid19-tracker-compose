package com.mohkhz.covid19_compose.ui.Chooser

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.mohkhz.covid19_compose.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun ChooserScreen(
    viewModel: ChooserViewModel = hiltViewModel(),
    onObserver: (ChooserViewModel) -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
) {

    LaunchedEffect(key1 = true) {

        onObserver(viewModel)

        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it)
                }
                else -> Unit
            }
        }
    }

}