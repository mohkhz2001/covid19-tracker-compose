package com.mohkhz.covid19_compose.ui.startup.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mohkhz.covid19_compose.R
import com.mohkhz.covid19_compose.ui.theme.Shapes

@Composable
fun StartUpComponent(
    screenHeightDp: Dp,
    screenWidthDp: Dp,
    rawRes: Int = R.raw.wearmask,
    animationBack: Int = R.drawable.wear_mask_back,
    headText1: String,
    headText2: String,
    description: String,
    nextBtn: () -> Unit
) {
    val wearMaskAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))

    val constraints = decoupledConstraints(screenHeightDp, screenWidthDp)

    ConstraintLayout(constraints) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(0.dp)
                .layoutId("animationBox"),
            contentAlignment = Alignment.Center,

            ) {

            Image(
                painter = painterResource(id = animationBack),
                contentDescription = "mask background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            )

            LottieAnimation(wearMaskAnimation, isPlaying = true, restartOnPlay = true)

        }

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append("$headText1\n")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                    )
                ) {
                    append(headText2)
                }
            }, modifier = Modifier
                .layoutId("headTXT")
                .fillMaxWidth(), textAlign = TextAlign.Center
        )

        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("descriptionTXT"),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontStyle = FontStyle.Italic
        )

        OutlinedButton(
            onClick = nextBtn,
            Modifier
                .width(90.dp)
                .height(90.dp)
                .layoutId("nextBTN"),
            border = BorderStroke(width = 3.dp, color = Color(0Xff0089FF)),
            shape = Shapes.large
        ) {

            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .size(100.dp),
                tint = Color.Black
            )

        }
    }

}

private fun decoupledConstraints(
    screenHeightDp: Dp,
    screenWidthDp: Dp
): ConstraintSet {

    return ConstraintSet {

        val animationBox = createRefFor("animationBox")
        val nextBTN = createRefFor("nextBTN")
        val headTXT = createRefFor("headTXT")
        val descriptionTXT = createRefFor("descriptionTXT")

        val guideline45 = createGuidelineFromTop((screenHeightDp / 100) * 45)
        val guideline52 = createGuidelineFromTop((screenHeightDp / 100) * 50)
        val guideline68 = createGuidelineFromTop((screenHeightDp / 100) * 66)
        val guideline80 = createGuidelineFromTop((screenHeightDp / 100) * 80)

        val guideline50 = createGuidelineFromStart((screenWidthDp / 100) * 50)

        constrain(animationBox) {
            top.linkTo(parent.top)
            bottom.linkTo(guideline45)
        }

        constrain(headTXT) {
            top.linkTo(guideline52)
            start.linkTo(guideline50)
            end.linkTo(guideline50)
        }

        constrain(descriptionTXT) {
            top.linkTo(guideline68)
            start.linkTo(guideline50)
            end.linkTo(guideline50)
        }

        constrain(nextBTN) {
            top.linkTo(guideline80)
            start.linkTo(guideline50)
            end.linkTo(guideline50)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewWearMAsk() {
//    WearMaskScreen()
}