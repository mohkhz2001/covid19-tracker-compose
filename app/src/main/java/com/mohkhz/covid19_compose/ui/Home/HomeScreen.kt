package com.mohkhz.covid19_compose.ui.Home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mohkhz.covid19_compose.data.db.FavoriteItem
import com.mohkhz.covid19_compose.ui.Component.NewStatics
import com.mohkhz.covid19_compose.ui.Component.SeparatePart
import com.mohkhz.covid19_compose.util.UiEvent
import kotlinx.coroutines.flow.collect
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    screenHeightDp: Dp,
    screenWidthDp: Dp,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val cities = viewModel.favoriteCities.collectAsState(initial = emptyList())
    val totalStatistics = viewModel.globalData.collectAsState(null).value

    val visibilityProgressBar = viewModel.visibilityProgressBar.collectAsState().value

    LaunchedEffect(key1 = true) {

        viewModel.location()
        viewModel.getGlobalData()

        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Navigate -> {
                    onNavigate(it)
                }
                else -> Unit
            }
        }

        viewModel.globalEventConversion.collect {
            when (val a = viewModel.globalEventConversion.value) {
                is HomeViewModel.GlobalDataEvent.Success -> {

                }
            }
        }

        viewModel.conversion.collect { event ->
            when (val a = viewModel.conversion.value) {
                is HomeViewModel.LocationEvent.Success -> {
                    Log.d("home screen", " location loading = false ")

                }
                is HomeViewModel.LocationEvent.Loading -> {
                    Log.d("home screen", " location loading = true ")
                }
            }
        }

    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopApp()
        },
    ) {

        ConstraintLayout(constraintSet(screenHeightDp, screenWidthDp)) {
            NewStatics(
                cases = totalStatistics?.todayCases,
                recovery = totalStatistics?.todayRecovered,
                death = totalStatistics?.todayDeaths,
                "newStatics"
            )

            SeparatePart(
                "Favorites",
                { viewModel.onEvent(HomeEvent.OnAddNewFavorite) },
                Icons.Default.Add,
                "favoriteSeparate"
            )

            FavoritesLazyColumn(
                cities = cities.value,
                viewModel = viewModel,
                visibilityProgressBar = visibilityProgressBar,
                "favoriteItem"
            )

            SeparatePart("Other Part", layoutId = "otherSeparate")

            OtherPartItem("Country rating", {}, layoutID = "rating")
            Spacer(modifier = Modifier.height(10.dp))
            OtherPartItem("About us", {}, layoutID = "about")
        }

    }

}

fun constraintSet(
    screenHeightDp: Dp,
    screenWidthDp: Dp,
): ConstraintSet {
    return ConstraintSet {

        val newStatics = createRefFor("newStatics")
        val favoriteSeparate = createRefFor("favoriteSeparate")
        val favoriteItem = createRefFor("favoriteItem")
        val otherSeparate = createRefFor("otherSeparate")
        val rating = createRefFor("rating")
        val about = createRefFor("about")

        val guideline25 = createGuidelineFromTop((screenHeightDp / 100) * 25)
        val guideline65 = createGuidelineFromTop((screenHeightDp / 100) * 65)

        constrain(newStatics) {
            top.linkTo(parent.top)
            bottom.linkTo(guideline25)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(favoriteSeparate) {
            top.linkTo(guideline25)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(favoriteItem) {
            top.linkTo(favoriteSeparate.bottom, 15.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(guideline65)
        }

        constrain(otherSeparate) {
            top.linkTo(guideline65, 15.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(rating) {
            top.linkTo(otherSeparate.bottom, 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(about) {
            top.linkTo(rating.bottom, 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

    }
}

fun doubleToStringNoDecimal(d: Long): String? {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("###,###")
    return formatter.format(d)
}

@ExperimentalMaterialApi
@Composable
fun FavoritesItem(
    onItemClick: (FavoriteItem) -> Unit,
    item: FavoriteItem,
    onDelete: () -> Unit,
    visibility: Boolean = false
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(bottom = 5.dp)
            .clickable { onItemClick(item) },
        backgroundColor = Color(0xffC4E9E5)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = item.cityName, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                if (item.isHome && !visibility)
                    Icon(Icons.Default.Home, contentDescription = "")

                Spacer(modifier = Modifier.width(5.dp))

                if (item.isHome && visibility)
                    CircularProgressIndicator(
                        color = Color(0xFF0088FF),
                        modifier = Modifier.size(20.dp)
                    )

            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(item.flag),
                    contentDescription = "",
                    modifier = Modifier
                        .height(30.dp)
                        .width(50.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Image(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "",
                    Modifier.size(22.dp)
                )
            }


        }
    }

}

@Composable
fun OtherPartItem(title: String, onClick: () -> Unit, layoutID: Any = "") {
    // contain -> about , rating ,
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .layoutId(layoutID)
            .padding(horizontal = 18.dp)
            .clickable { onClick() },
        backgroundColor = Color(0xFFA5A5A5)
    ) {
        Row(
            Modifier
                .padding(vertical = 6.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = title, fontSize = 20.sp, color = Color.White)
            Image(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                Modifier.size(22.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

        }
    }


}

@Composable
fun TopApp() {
    return TopAppBar(
        title = {
            Text(text = "Covid-19 tracker", fontWeight = FontWeight.Medium)
        },
        backgroundColor = Color(0xffC4E9E5),
        contentColor = Color.Black,
        elevation = 15.dp
    )
}

@ExperimentalMaterialApi
@Composable
fun FavoritesLazyColumn(
    cities: List<FavoriteItem>,
    viewModel: HomeViewModel,
    visibilityProgressBar: Boolean,
    layoutId: Any = ""
) {
    LazyColumn(
        Modifier
            .height(255.dp)
            .layoutId(layoutId),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        itemsIndexed(
            items = cities,
            key = { index, item ->
                item.hashCode()
            }
        ) { index, item ->
            // if  our item was the current location -> swipe delete is unavailable
            if (item.isHome) {
                FavoritesItem(
                    onItemClick = { viewModel.onEvent(HomeEvent.OnFavoriteClick(it)) },
                    item = item,
                    { viewModel.onEvent(HomeEvent.OnDeleteItem(item)) },
                    visibilityProgressBar
                )
            } else {
                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.onEvent(HomeEvent.OnDeleteItem(item))
                        }
                        false
                    }
                )
                SwipeToDismiss(
                    state = state,
                    background = {
                        val color = when (state.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color.Red
                            else -> Color.Transparent
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 5.dp)
                                .background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(8.dp)
                            )
                        }
                    },
                    dismissContent = {
                        FavoritesItem(
                            onItemClick = { viewModel.onEvent(HomeEvent.OnFavoriteClick(it)) },
                            item = item,
                            { viewModel.onEvent(HomeEvent.OnDeleteItem(item)) },
                        )
                    },
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                )
            }

        }
    }
}