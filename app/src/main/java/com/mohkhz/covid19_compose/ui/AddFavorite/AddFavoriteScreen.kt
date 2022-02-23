package com.mohkhz.covid19_compose.ui.AddFavorite

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mohkhz.covid19_compose.data.db.FavoriteItem
import com.mohkhz.covid19_compose.ui.theme.Shapes
import com.mohkhz.covid19_compose.util.UiEvent
import kotlinx.coroutines.flow.collect

val AppColor = Color(0xffC4E9E5)

@Composable
fun AddFavorite(
    viewModel: AddFavoriteViewModel = hiltViewModel(),
    onNavigate: () -> Unit,
) {

    val list = viewModel.list.collectAsState(initial = emptyList())
    val visibilityProgressBar = viewModel.visibilityProgressBar.collectAsState().value
    val visibilitySearchField = viewModel.visibilitySearchField.collectAsState().value

    LaunchedEffect(key1 = true) {

        viewModel.getCountries()

        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.PopBackStack -> onNavigate()
                else -> Unit
            }
        }

    }


    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopBar({ onNavigate() }, { viewModel.onEvent(AddFavoriteEvent.OnSearchClicked) })
        },
    ) {

        if (!visibilityProgressBar)
            Column() {
                if (visibilitySearchField)
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        value = viewModel.searchBox,
                        onValueChange = { viewModel.onEvent(AddFavoriteEvent.OnSearchBoxChange(it)) },
                        maxLines = 1,
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            {
                                Log.d("imekey", "clicked")
                            }
                        ),
                        placeholder = { Text(text = "EXP: Iran") },
                        shape = Shapes.medium,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                        ),
                        isError = false,
                        readOnly = false,

                        )

                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(items = list.value) { index, item ->
                        CountryItem(
                            favoriteItem = item,
                            index + 1,
                            {
                                Log.d("add favorite screen ", " lazy column : ${it.cityName}")
                                viewModel.onEvent(AddFavoriteEvent.AddNew(it))
                            })
                    }
                }

            }
        else
            CircularProgressHomeScreen()


    }


}

@Composable
fun CircularProgressHomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp), color = AppColor
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

@Composable
fun TopBar(back: () -> Unit, searchEvent: () -> Unit) {
    return TopAppBar(
        title = {
            Text(text = "choose country", fontWeight = FontWeight.Medium)
        },
        backgroundColor = AppColor,
        contentColor = Color.Black,
        elevation = 15.dp,
        navigationIcon = {
            IconButton(
                onClick = { back() },
                Modifier.padding(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "", Modifier.size(25.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { searchEvent() },
                Modifier.padding(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "", Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }
    )

}

@Composable
fun CountryItem(
    favoriteItem: FavoriteItem,
    number: Int,
    onClick: (favoriteItem: FavoriteItem) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(favoriteItem) },
        backgroundColor = Color(0xffC4E9E5),
        shape = Shapes.medium,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = number.toString(),
                fontSize = 20.sp,
            )
            Image(
                painter = rememberImagePainter(favoriteItem.flag),
                contentDescription = "",
                modifier = Modifier
                    .height(30.dp)
                    .width(50.dp)
            )
            Text(
                text = favoriteItem.cityName.uppercase(), fontSize = 18.sp, maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }

    }
}
