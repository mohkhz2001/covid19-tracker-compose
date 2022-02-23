package com.mohkhz.covid19_compose.data.model.Location

data class TimeZone(
    val current_time: String,
    val current_time_unix: Double,
    val dst_savings: Int,
    val is_dst: Boolean,
    val name: String,
    val offset: Float
)