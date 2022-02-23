package com.mohkhz.covid19_compose.ui.AddFavorite

import com.mohkhz.covid19_compose.data.db.FavoriteItem

sealed class AddFavoriteEvent {

    data class AddNew(val favoriteItem: FavoriteItem) : AddFavoriteEvent()

    data class OnSearchBoxChange(val txt: String) : AddFavoriteEvent()

    object OnSearchClicked : AddFavoriteEvent()

}