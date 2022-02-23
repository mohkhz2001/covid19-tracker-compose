package com.mohkhz.covid19_compose.ui.About

import androidx.lifecycle.ViewModel
import com.mohkhz.covid19_compose.data.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(private val repository: Repository) :ViewModel() {

}