package com.mohkhz.covid19_compose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mohkhz.covid19_compose.data.Api.CovidApi
import com.mohkhz.covid19_compose.data.Api.LocationApi
import com.mohkhz.covid19_compose.data.db.FavoriteDataBase
import com.mohkhz.covid19_compose.data.repo.Repository
import com.mohkhz.covid19_compose.data.repo.RepositoryImpl
import com.mohkhz.covid19_compose.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL_LOCATION = "https://api.ipgeolocation.io/"
private const val BASE_URL_COVID = "https://disease.sh/v3/covid-19/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providerFavoritesDatabase(app: Application): FavoriteDataBase {
        return Room.databaseBuilder(
            app,
            FavoriteDataBase::class.java,
            "favoritesCity.db"
        ).build()
    }

    @Singleton
    @Provides
    fun providerRepository(
        @ApplicationContext context: Context,
        locationApi: LocationApi,
        covidApi: CovidApi,
        db: FavoriteDataBase
    ): Repository {
        return RepositoryImpl(context, locationApi, covidApi, db.dao)
    }

    @Singleton
    @Provides
    fun providerLocationApi(): LocationApi =
        Retrofit.Builder().baseUrl(BASE_URL_LOCATION)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LocationApi::class.java)

    @Singleton
    @Provides
    fun providerCovidApi(): CovidApi =
        Retrofit.Builder().baseUrl(BASE_URL_COVID)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CovidApi::class.java)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}