package com.mohkhz.covid19_compose.data.repo

import com.mohkhz.covid19_compose.data.db.FavoriteItem
import com.mohkhz.covid19_compose.data.model.AllCountry.AllCountry
import com.mohkhz.covid19_compose.data.model.Country.CountryCovid
import com.mohkhz.covid19_compose.data.model.Covid.TotalStatistics
import com.mohkhz.covid19_compose.data.model.Location.LocationResponse
import com.mohkhz.covid19_compose.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun updateDataStore(name: String, checked: Boolean)

    suspend fun readDataStore(): Flow<String>

    suspend fun getCountryName(): Resource<LocationResponse>

    suspend fun getGlobalData(): Resource<TotalStatistics>

    suspend fun getCountries(): Resource<AllCountry>

    suspend fun getCountryData(countryName: String): Resource<CountryCovid>

    suspend fun insertFavorite(favoriteItem: FavoriteItem)

    suspend fun deleteCity(favoriteItem: FavoriteItem)

    fun getCityList(): Flow<List<FavoriteItem>>

}