package com.mohkhz.covid19_compose.ui.CountryRating

sealed class CountryRatingEvent {

    object OnCasesClick : CountryRatingEvent()
    object OnDeathClick : CountryRatingEvent()
    object OnRecoveredClick : CountryRatingEvent()

}