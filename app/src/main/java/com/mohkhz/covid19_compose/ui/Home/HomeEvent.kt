package com.mohkhz.covid19_compose.ui.Home

import android.content.Context
import com.mohkhz.covid19_compose.data.db.FavoriteItem

sealed class HomeEvent {
    object OnAddNewFavorite : HomeEvent()
    data class OnDeleteItem(val favoriteItem: FavoriteItem , val context: Context) : HomeEvent()
    data class OnFavoriteClick(val favoriteItem: FavoriteItem) : HomeEvent()
    object OnAboutClick : HomeEvent()
    object OnCountryRating : HomeEvent()
}