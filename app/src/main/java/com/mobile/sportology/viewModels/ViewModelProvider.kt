package com.mobile.sportology.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.sportology.api.FootballApi
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")

class MyViewModelProvider @Inject constructor(
    val api: FootballApi,
    val application: Application
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FootBallViewModel::class.java))
            return FootBallViewModel(api, application) as T
        else if (modelClass.isAssignableFrom(NewsViewModel::class.java)) return NewsViewModel() as T
        throw IllegalArgumentException("unknown view model")
    }
}