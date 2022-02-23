package com.mohkhz.covid19_compose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteItem::class], version = 1)
abstract class FavoriteDataBase : RoomDatabase() {

    abstract val dao: FavoriteDao

}