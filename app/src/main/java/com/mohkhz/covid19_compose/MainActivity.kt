package com.mohkhz.covid19_compose

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Observer
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mohkhz.covid19_compose.ui.AddFavorite.AddFavorite
import com.mohkhz.covid19_compose.ui.Chooser.ChooserScreen
import com.mohkhz.covid19_compose.ui.Chooser.ChooserViewModel
import com.mohkhz.covid19_compose.ui.CountryDetail.CountryDetail
import com.mohkhz.covid19_compose.ui.Home.HomeScreen
import com.mohkhz.covid19_compose.ui.startup.hand_wash.HandWashScreen
import com.mohkhz.covid19_compose.ui.startup.separate.SeparateScreen
import com.mohkhz.covid19_compose.ui.startup.wear_mask.WearMaskScreen
import com.mohkhz.covid19_compose.ui.theme.Covid19_composeTheme
import com.mohkhz.covid19_compose.util.Routes
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@AndroidEntryPoint
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Covid19_composeTheme {

                window.statusBarColor = MaterialTheme.colors.primaryVariant.toArgb()

                NavHost(chooseScreenObserver = {
                    it.value.observe(
                        this@MainActivity,
                        Observer { value ->
                            it.change(value)
                        })
                })

            }
        }
    }

    @Composable
    fun NavHost(
        chooseScreenObserver: (ChooserViewModel) -> Unit,
    ) {
        val configuration = LocalConfiguration.current

        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp

        val navController = rememberAnimatedNavController()
        AnimatedNavHost(
            navController = navController,
            startDestination = Routes.CHOOSER_SCREEN
        ) {

            composable(Routes.CHOOSER_SCREEN,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                }) {
                ChooserScreen(onNavigate = {
                    navController.navigate(it.route)
                },
                    onObserver = {
                        chooseScreenObserver(it)
                    }
                )
            }

            composable(Routes.WEAR_MASK_SCREEN) {
                WearMaskScreen(
                    screenHeightDp = screenHeight,
                    screenWidthDp = screenWidth,
                    onNavigate = {
                        navController.navigate(it.route)
                    })
            }

            composable(Routes.SEPARATE_SCREEN,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                }) {
                SeparateScreen(
                    screenHeightDp = screenHeight,
                    screenWidthDp = screenWidth,
                    onNavigate = {
                        navController.navigate(it.route)
                    })
            }

            composable(Routes.HAND_WASH_SCREEN,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                }) {
                HandWashScreen(
                    screenHeightDp = screenHeight,
                    screenWidthDp = screenWidth,
                    onNavigate = {
                        navController.navigate(it.route)
                    })
            }

            composable(Routes.HOME_SCREEN,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                }) {
                HomeScreen(
                    screenHeightDp = screenHeight,
                    screenWidthDp = screenWidth,
                    onNavigate = {
                        navController.navigate(it.route)
                    })
            }

            composable(Routes.ADD_FAVORITE_SCREEN,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                }) {
                AddFavorite(onNavigate = {
                    navController.popBackStack()
                })
            }

            composable(Routes.COUNTRY_DETAIL + "?countryName={countryName}",
                arguments = listOf(navArgument("countryName") {
                    defaultValue = "test"
                    type = NavType.StringType
                }),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(500)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(500)
                    )
                }) {
                CountryDetail(
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }

        }
    }


    @Composable
    fun RequestPermission() {
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

        permissionsState.permissions.forEach { perm ->
            when (perm.permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    when {
                        perm.hasPermission -> {
                            Log.d("test fun ", "a")
                        }
                        perm.shouldShowRationale -> {
                            Log.d("test fun ", "c")
                        }
                        perm.isPermanentlyDenied() -> {
                            Log.d("test fun ", "b")
                        }
                    }
                }
            }
        }
    }

}





