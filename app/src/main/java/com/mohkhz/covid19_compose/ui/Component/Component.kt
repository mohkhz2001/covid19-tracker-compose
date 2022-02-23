package com.mohkhz.covid19_compose.ui.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohkhz.covid19_compose.R
import com.mohkhz.covid19_compose.ui.Home.doubleToStringNoDecimal
import com.mohkhz.covid19_compose.ui.theme.Shapes

@Composable
fun NewStatics(cases: Long?, recovery: Long?, death: Long?, layoutId: Any = "") {

    Row(
        Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(20.dp)
            .layoutId(layoutId)
    ) {
        Card(
            Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(end = 10.dp)
                .shadow(5.dp, shape = Shapes.medium),
            backgroundColor = Color(0xffFFE4B0),
        ) {

            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painterResource(id = R.drawable.infected), contentDescription = "",
                    Modifier.size(60.dp)
                )

                Text(text = "New cases", fontSize = 20.sp)
                Text(
                    text = if (cases != null) doubleToStringNoDecimal(cases).toString() else "N/A",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,

                    )

            }

        }

        Column(
            Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Card(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(bottom = 5.dp)
                    .shadow(5.dp, shape = Shapes.medium),
                backgroundColor = Color(0xffB8FFB7)

            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "New Recovery", fontSize = 20.sp)
                    Text(
                        text = if (recovery != null) doubleToStringNoDecimal(recovery).toString() else "N/A",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
            Card(
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(top = 5.dp)
                    .shadow(5.dp, shape = Shapes.medium),
                backgroundColor = Color(0xffFFBABA)

            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "New Death", fontSize = 20.sp)
                    Text(
                        text = if (death != null) doubleToStringNoDecimal(death).toString() else "N/A",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
            }
        }

    }

}

@Composable
fun SeparatePart(
    title: String,
    onAddClick: () -> Unit? = {},
    icon: ImageVector? = null,
    layoutId: Any = ""
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .layoutId(layoutId)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = title, fontSize = 22.sp)

            if (icon != null)
                IconButton(
                    onClick = { onAddClick() },
                    Modifier
                        .padding(0.dp)
                        .size(20.dp)
                ) {
                    Icon(icon, contentDescription = "", Modifier.padding(0.dp))
                }

        }

        TabRowDefaults.Divider(
            color = Color(0xffA5A5A5),
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

    }

}

@Preview
@Composable
fun ComponentPreview() {

}