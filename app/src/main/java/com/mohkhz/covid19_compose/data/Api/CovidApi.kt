package com.mohkhz.covid19_compose.data.Api

import com.mohkhz.covid19_compose.data.model.AllCountry.AllCountry
import com.mohkhz.covid19_compose.data.model.Country.CountryCovid
import com.mohkhz.covid19_compose.data.model.Covid.TotalStatistics
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {

    @GET("countries?yesterday=1")
    suspend fun getCountries(): Response<AllCountry>

    @GET("all")
    suspend fun getGlobalData(): Response<TotalStatistics>

    @GET("countries/{countryName}?strict=true")
    suspend fun getCountryData(@Path("countryName") countryName: String): Response<CountryCovid>

}