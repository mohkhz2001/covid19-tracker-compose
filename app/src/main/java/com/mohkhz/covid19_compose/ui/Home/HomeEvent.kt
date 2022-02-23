package com.mohkhz.covid19_compose.ui.Home

import com.mohkhz.covid19_compose.data.db.FavoriteItem

sealed class HomeEvent {
    object OnAddNewFavorite : HomeEvent()
    data class OnDeleteItem(val favoriteItem: FavoriteItem) : HomeEvent()
    data class OnFavoriteClick(val favoriteItem: FavoriteItem) : HomeEvent()
}