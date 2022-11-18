package com.mobile.sportology.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MyViewModelProvider(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FootBallViewModel::class.java)) return FootBallViewModel(app) as T
        else if (modelClass.isAssignableFrom(NewsViewModel::class.java)) return NewsViewModel() as T
        throw IllegalArgumentException("unknown view model")
    }
}