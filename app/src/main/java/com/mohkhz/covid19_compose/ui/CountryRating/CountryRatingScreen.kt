package com.mohkhz.covid19_compose.ui.CountryRating

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mohkhz.covid19_compose.ui.AddFavorite.AppColor
import com.mohkhz.covid19_compose.ui.AddFavorite.CircularProgressHomeScreen
import com.mohkhz.covid19_compose.ui.theme.Shapes
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

val AppColor = Color(0xffC4E9E5)

@Composable
fun CountryRating(
    viewModel: CountryRatingViewModel = hiltViewModel(),
    navigate: () -> Unit
) {

    val list = viewModel.ratingData.collectAsState().value
    var activeName by remember {
        mutableStateOf("Cases")
    }

    LaunchedEffect(key1 = true) {
        viewModel.getData()
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { TopBar { navigate() } }
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                SortBtn(
                    txt = "Cases",
                    onClick = {
                        activeName = "Cases"
                        viewModel.onEvent(CountryRatingEvent.OnCasesClick)
                    },
                    activeName = activeName
                )

                Spacer(modifier = Modifier.width(10.dp))

                SortBtn(
                    txt = "Death",
                    onClick = {
                        activeName = "Death"
                        viewModel.onEvent(CountryRatingEvent.OnDeathClick)

                    },
                    activeName = activeName
                )

                Spacer(modifier = Modifier.width(10.dp))

                SortBtn(
                    txt = "Recovered",
                    onClick = {
                        activeName = "Recovered"
                        viewModel.onEvent(CountryRatingEvent.OnRecoveredClick)
                    },
                    activeName = activeName
                )

            }

            if (list != null) {
                LazyColumn(
                    Modifier
                        .fillMaxSize(),

                    ) {
                    itemsIndexed(items = list) { index, item ->

                        Item(
                            title = item.country,
                            index = index + 1,
                            url = item.countryInfo.flag,
                            cases = item.cases,
                            death = item.deaths,
                            recovered = item.recovered
                        )

                    }
                }
            } else
                CircularProgressHomeScreen()

        }
    }

}

@Composable
fun TopBar(back: () -> Unit) {
    return TopAppBar(
        backgroundColor = AppColor,
        elevation = 5.dp,
        title = { Text(text = "Country rating") },
        navigationIcon = {
            IconButton(
                onClick = { back() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "")
            }
        }
    )
}

@Composable
fun Item(
    title: String,
    cases: Long = 0,
    death: Long = 0,
    recovered: Long = 0,
    index: Int,
    url: String
) {

    var expended by remember {
        mutableStateOf(false)
    }

    val rotationState by animateFloatAsState(targetValue = if (expended) 180f else 0f)

    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .shadow(3.dp, shape = Shapes.small)
            .animateContentSize(
                animationSpec = tween(
                    100,
                    easing = LinearOutSlowInEasing
                )
            ),//TODO:  animation
        backgroundColor = AppColor,
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Row(
                Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = index.toString(), fontSize = 17.sp)

                    Image(
                        painter = rememberImagePainter(url),
                        contentDescription = "",
                        modifier = Modifier
                            .height(30.dp)
                            .width(50.dp)
                    )

                    Text(
                        text = title,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(0.dp)
                    )
                }

                IconButton(
                    onClick = { expended = !expended },
                    Modifier
                        .size(20.dp)
                        .padding(0.dp)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState)
                ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "", Modifier.size(20.dp))
                }


            }

            if (expended)
                PopUp(cases, death, recovered)
        }

    }
}

@Composable
fun PopUp(cases: Long = 0, death: Long = 0, recovered: Long = 0) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        backgroundColor = Color.White,
        elevation = 3.dp,
        shape = Shapes.medium
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            PopUpItem(modifier = Modifier.weight(1f), title = "Cases", data = cases)
            PopUpItem(modifier = Modifier.weight(1f), title = "Death", data = death)
            PopUpItem(modifier = Modifier.weight(1f), title = "Recovered", data = recovered)
        }
    }
}

@Composable
fun PopUpItem(modifier: Modifier, title: String, data: Long) {

    val color: Color = when (title) {
        "Cases" -> {
            Color(0xffFFE4B0)
        }
        "Death" -> {
            Color(0xffFFBABA)
        }
        else -> {
            Color(0xffB8FFB7)
        }
    }

    Card(modifier, elevation = 3.dp, shape = Shapes.large, backgroundColor = color) {
        Column(
            Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = title, color = Color.Black, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = doubleToStringNoDecimal(data).toString(),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


private fun doubleToStringNoDecimal(d: Long): String? {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("###,###")
    return formatter.format(d)
}

@Composable
fun SortBtn(txt: String, onClick: () -> Unit, activeName: String = "Cases") {
    return OutlinedButton(
        onClick = { onClick() }, shape = CircleShape,
        border = BorderStroke(2.dp, Color.Blue),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (activeName == txt) Color.White else Color(0x8C7700FF),
            backgroundColor = if (activeName == txt) Color(0x8C7700FF) else Color.Transparent,
        )
    ) {
        Text(text = txt)
    }
}