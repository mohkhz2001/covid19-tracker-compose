package com.mohkhz.covid19_compose.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteItem: FavoriteItem)

    @Delete
    suspend fun deleteCity(favoriteItem: FavoriteItem)

    @Query("SELECT * FROM favoriteCity")
    fun getCityList(): Flow<List<FavoriteItem>>

}