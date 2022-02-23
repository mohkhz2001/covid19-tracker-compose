package com.mohkhz.covid19_compose.ui.CountryDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohkhz.covid19_compose.ui.AddFavorite.AppColor
import com.mohkhz.covid19_compose.ui.Component.NewStatics
import com.mohkhz.covid19_compose.ui.Component.SeparatePart
import com.mohkhz.covid19_compose.ui.theme.Shapes
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CountryDetail(
    viewModel: CountryDetailViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {

    val countryName = viewModel.countryName
    val countryData = viewModel.countryData.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.getCountryData()
    }

    Scaffold(
        topBar = { TopBar({/*  todo-> back navigation */ onNavigate() }, countryName!!) },
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            Modifier
                .fillMaxSize()
        ) {

            NewStatics(
                cases = countryData?.todayCases,
                recovery = countryData?.todayRecovered,
                death = countryData?.todayDeaths
            )


            TotalStatics(
                countryData?.cases,
                countryData?.active,
                countryData?.recovered,
                countryData?.deaths
            )
            // history separate
            SeparatePart(title = "History")

            // history separate
            SeparatePart(title = "Vaccine")

        }

    }

}

@Composable
fun beta() {

    Scaffold(
        topBar = { TopBar({/*  todo-> back navigation */ }, "Iran") },
        modifier = Modifier.fillMaxSize(),
    ) {

        Column(
            Modifier
                .fillMaxSize()
        ) {

            NewStatics(
                cases = 123456789,
                recovery = 123456789,
                death = 123456789
            )


            // total separate
            SeparatePart(title = "Total statics")

//            TotalStatics()

            // history separate
            SeparatePart(title = "History")

            // history separate
            SeparatePart(title = "Vaccine")

        }


    }
}

@Composable
fun TopBar(backClick: () -> Unit, countryName: String = "test") {
    TopAppBar(
        title = { Text(text = countryName) },
        navigationIcon = {
            IconButton(onClick = { backClick() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "back btn")
            }
        },
        backgroundColor = Color(0xffC4E9E5),
        elevation = 15.dp,
        contentColor = Color.Black,
    )
}

@Composable
fun TotalStatics(totalCases: Long?, active: Long?, recovered: Long?, death: Long?) {


    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .shadow(5.dp, shape = Shapes.medium),
        backgroundColor = Color(0xFFFAFAFA),

        ) {

        if (totalCases != null && active != null && recovered != null && death != null) {
            Column(
                Modifier
                    .padding(15.dp)
            ) {

                Text(
                    text = "Total statistics",
                    color = Color.Black,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF1B1B1B),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Medium
                        )
                    ) { append("${doubleToStringNoDecimal(totalCases)} ") }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFAAAAAA),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Normal
                        )
                    ) { append("  Total cases") }
                })

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 5.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .shadow(0.dp, shape = Shapes.small)
                            .weight((active).toFloat())
                            .height(10.dp),
                        backgroundColor = Color(0xFFFF9800)
                    ) {}
                    Card(
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .shadow(0.dp, shape = Shapes.small)
                            .weight((recovered).toFloat())
                            .height(10.dp),
                        backgroundColor = Color.Green
                    ) {}
                    Card(
                        modifier = Modifier
                            .shadow(0.dp, shape = Shapes.small)
                            .weight((death).toFloat())
                            .height(10.dp),
                        backgroundColor = Color.Red
                    ) {}
                }



                TotalItem(
                    title = "Active cases",
                    value = doubleToStringNoDecimal(active).toString(),
                    Color(0xFFFF9800)
                )
                TotalItem(
                    title = "Recovered cases",
                    value = doubleToStringNoDecimal(recovered).toString(),
                    Color.Green
                )
                TotalItem(
                    title = "Death cases",
                    value = doubleToStringNoDecimal(death).toString(),
                    Color.Red
                )

            }
        } else {
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp), color = AppColor
                )
                Text(
                    text = "Loading...",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFFA8A8A8)
                )
            }
        }


    }
}

@Composable
fun TotalItem(title: String, value: String, boxColor: Color) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                modifier = Modifier
                    .size(20.dp)
                    .shadow(0.dp, shape = Shapes.small),
                backgroundColor = boxColor
            ) {}

            Text(
                text = title,
                fontSize = 18.sp,
                color = Color(0xFFAAAAAA),
                modifier = Modifier.padding(start = 9.dp)
            )
        }

        Text(
            text = value,
            fontSize = 18.sp,
            color = Color(0xFFAAAAAA),
        )
    }

}

private fun doubleToStringNoDecimal(d: Long): String? {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("###,###")
    return formatter.format(d)
}

private fun convertDate(i: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = Date(i)
    return sdf.format(date)
}

@Preview(showBackground = true)
@Composable
fun preview() {
    beta()
}