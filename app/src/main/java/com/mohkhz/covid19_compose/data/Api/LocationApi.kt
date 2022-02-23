package com.mohkhz.covid19_compose.data.Api

import com.mohkhz.covid19_compose.data.model.Location.LocationResponse
import retrofit2.Response
import retrofit2.http.GET

interface LocationApi {

    @GET("ipgeo?apiKey=9ca5a87050e64a9aa2b8dfd60e7d1ca2")
    suspend fun getLocation(): Response<LocationResponse>

}