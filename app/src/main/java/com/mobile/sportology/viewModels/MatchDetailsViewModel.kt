package com.mobile.sportology.viewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.sportology.Constants
import com.mobile.sportology.ResponseState
import com.mobile.sportology.api.FootballApi
import com.mobile.sportology.models.football.MatchDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    val api:FootballApi, val app: Application,
): AndroidViewModel(app) {
    val matchDetails: MutableLiveData<ResponseState<MatchDetails>> = MutableLiveData()

    suspend fun getMatchDetails(apiKey: String, matchId: Int, timeZone: String) {
        matchDetails.postValue(ResponseState.Loading())
        try{
            val response = api.getMatchDetails(apiKey, matchId, timeZone)
            matchDetails.postValue(ResponseState.Success(response.body()!!))
        }
        catch (e: Exception) {
            Log.e("getMatchDetails()", e.message!!)
            Toast.makeText(app.applicationContext, e.message!!, Toast.LENGTH_LONG).show()
        }
    }
}