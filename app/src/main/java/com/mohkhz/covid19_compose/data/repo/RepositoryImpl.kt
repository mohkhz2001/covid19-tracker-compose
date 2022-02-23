package com.mohkhz.covid19_compose.data.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mohkhz.covid19_compose.data.Api.CovidApi
import com.mohkhz.covid19_compose.data.Api.LocationApi
import com.mohkhz.covid19_compose.data.db.FavoriteDao
import com.mohkhz.covid19_compose.data.db.FavoriteItem
import com.mohkhz.covid19_compose.data.model.AllCountry.AllCountry
import com.mohkhz.covid19_compose.data.model.Country.CountryCovid
import com.mohkhz.covid19_compose.data.model.Covid.TotalStatistics
import com.mohkhz.covid19_compose.data.model.Location.LocationResponse
import com.mohkhz.covid19_compose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val DataStoreName = "startUp_data"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreName)

class RepositoryImpl @Inject constructor(
    private val context: Context,
    private val locationApi: LocationApi,
    private val covidApi: CovidApi,
    private val dao: FavoriteDao
) : Repository {

    companion object {
        val startUp = stringPreferencesKey("START_UP")
    }

    override suspend fun updateDataStore(name: String, checked: Boolean) {
        context.dataStore.edit { data ->
            data[startUp] = checked.toString()
        }
    }

    override suspend fun readDataStore(): Flow<String> {
        return context.dataStore.data.map { test ->
            test[startUp].toString()
        }
    }

    override suspend fun getCountryName(): Resource<LocationResponse> {

        return try {

            val respose = locationApi.getLocation()
            val result = respose.body()

            if (respose.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(respose.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: " error in get country name ")
        }
    }

    override suspend fun insertFavorite(favoriteItem: FavoriteItem) {
        dao.insertFavorite(favoriteItem)
    }

    override suspend fun deleteCity(favoriteItem: FavoriteItem) {
        dao.deleteCity(favoriteItem)
    }

    override fun getCityList(): Flow<List<FavoriteItem>> {
        return dao.getCityList()
    }

    override suspend fun getGlobalData(): Resource<TotalStatistics> {
        return try {

            val respose = covidApi.getGlobalData()
            val result = respose.body()

            if (respose.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(respose.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: " error in get global data ")
        }
    }

    override suspend fun getCountries(): Resource<AllCountry> {
        return try {

            val respose = covidApi.getCountries()
            val result = respose.body()

            if (respose.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(respose.message())
            }

        } catch (e: Exception) {
            Resource.Error(e.message ?: " error in get countries ")
        }
    }

    override suspend fun getCountryData(countryName: String): Resource<CountryCovid> {
        return try {
            val respose = covidApi.getCountryData(countryName)
            val result = respose.body()

            if (respose.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(respose.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message + "error in get country data")
        }
    }

}