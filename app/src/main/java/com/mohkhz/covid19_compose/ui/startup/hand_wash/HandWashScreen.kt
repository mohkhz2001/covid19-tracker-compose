package com.mohkhz.covid19_compose.ui.startup.hand_wash

import android.Manifest
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mohkhz.covid19_compose.R
import com.mohkhz.covid19_compose.isPermanentlyDenied
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

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    permissionsState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {

                    permissionsState.permissions.forEach { perm ->
                        when (perm.permission) {
                            Manifest.permission.ACCESS_FINE_LOCATION -> {
                                when {
                                    perm.hasPermission -> {
                                        onNavigate(it)
                                    }
                                    perm.shouldShowRationale -> {
                                        onNavigate(it)
                                    }
                                    perm.isPermanentlyDenied() -> {
                                        Log.d("test fun ", "b")
                                    }
                                }
                            }
                        }
                    }

//                    onNavigate(it)
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