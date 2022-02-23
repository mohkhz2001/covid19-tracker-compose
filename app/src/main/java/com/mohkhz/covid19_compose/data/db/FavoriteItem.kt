package com.mohkhz.covid19_compose.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteCity")
data class FavoriteItem(
    @PrimaryKey val id: Int? = null,
    val cityName: String,
    val flag: String,
    val isHome: Boolean
)

