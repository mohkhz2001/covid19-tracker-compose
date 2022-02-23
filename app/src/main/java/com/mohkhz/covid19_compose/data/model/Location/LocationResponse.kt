package com.mohkhz.covid19_compose.data.model.Location

data class LocationResponse(
    val calling_code: String,
    val city: String,
    val connection_type: String,
    val continent_code: String,
    val continent_name: String,
    val country_capital: String,
    val country_code2: String,
    val country_code3: String,
    val country_flag: String,
    val country_name: String,
    val country_tld: String,
    val currency: Currency,
    val district: String,
    val geoname_id: String,
    val ip: String,
    val is_eu: Boolean,
    val isp: String,
    val languages: String,
    val latitude: String,
    val longitude: String,
    val organization: String,
    val state_prov: String,
    val time_zone: TimeZone,
    val zipcode: String
)