package com.mohkhz.covid19_compose.data.model.Country

data class CountryCovid(
    val active: Long,
    val activePerOneMillion: Double,
    val cases: Long,
    val casesPerOneMillion: Long,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Long,
    val criticalPerOneMillion: Double,
    val deaths: Long,
    val deathsPerOneMillion: Long,
    val oneCasePerPeople: Long,
    val oneDeathPerPeople: Long,
    val oneTestPerPeople: Long,
    val population: Long,
    val recovered: Long,
    val recoveredPerOneMillion: Double,
    val tests: Long,
    val testsPerOneMillion: Long,
    val todayCases: Long,
    val todayDeaths: Long,
    val todayRecovered: Long,
    val updated: Long
)