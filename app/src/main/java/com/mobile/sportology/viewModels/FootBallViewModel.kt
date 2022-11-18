package com.mobile.sportology.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.mobile.sportology.Constants
import com.mobile.sportology.Constants.Companion.PREMIER_LEAGUE_SEASON_ID
import com.mobile.sportology.Constants.Companion.SERIE_A_SEASON_ID
import com.mobile.sportology.MyApplication
import com.mobile.sportology.ResponseState
import com.mobile.sportology.api.RetrofitInstance.api
import com.mobile.sportology.models.football.Matches
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class FootBallViewModel(app: Application): AndroidViewModel(app) {
    val premierLeagueMatchesLiveData: MutableLiveData<ResponseState<Matches>> = MutableLiveData()
    val serieAMatchesLiveData: MutableLiveData<ResponseState<Matches>> = MutableLiveData()

    companion object {
        @JvmStatic
        @BindingAdapter("bind:homeScore", "bind:awayScore", "bind:statusCode")
        fun getScore(textView: TextView, homeScore:Int, awayScore: Int, statusCode: Int) {
            when (statusCode) {
                0 -> textView.text = "Not started"
                3 -> textView.text = "$homeScore - $awayScore"
                4 -> textView.text = "Postponed"
                5 -> textView.text = "Canceled"
                11 -> textView.text = "$homeScore HT $awayScore"
                else -> textView.text = "Not provided"
            }
        }
    }

    init {
        viewModelScope.launch{
            if(hasInternetConnection()){
                getPremierLeagueMatches(Constants.API_KEY, PREMIER_LEAGUE_SEASON_ID)
                getSerieAMatches(Constants.API_KEY, SERIE_A_SEASON_ID)
            }
            else {
                premierLeagueMatchesLiveData.value = ResponseState.Error("No internet connection")
                serieAMatchesLiveData.value = ResponseState.Error("No internet connection")
            }
        }
    }

    private suspend fun getPremierLeagueMatches(apiKey: String, PREMIER_LEAGUE_SEASON_ID: Int) =
        withContext(Dispatchers.IO) {
            if(hasInternetConnection()){
                premierLeagueMatchesLiveData.postValue(ResponseState.Loading())
                val response = api.getPremierLeagueMatches(
                    apiKey,
                    PREMIER_LEAGUE_SEASON_ID
                )
                premierLeagueMatchesLiveData.postValue(handlePremiereLeagueResponse(response))
            }
            else premierLeagueMatchesLiveData.postValue(ResponseState.Error("No internet connection"))
    }

    private fun handlePremiereLeagueResponse(response: Response<Matches>):
            ResponseState<Matches> {
        if(response.isSuccessful) {
            response.body().let { matches ->
                //add date without time for every match
                val regex = Regex(" ([0-9]+(:[0-9]+)+)")
                for(index in matches?.data!!.indices){
                    matches.data[index].matchDate =
                        matches.data[index].match_start.split(regex).toString()
                    matches.data[index].matchDate =
                        matches.data[index].matchDate.replace("]","")
                    matches.data[index].matchDate =
                        matches.data[index].matchDate.replace("[","")
                    matches.data[index].matchDate =
                        matches.data[index].matchDate.replace(",","")
                }
                return ResponseState.Success(matches)
            }
        }
        else return ResponseState.Error("Some thing went wrong")
    }

    private fun handleSerieAResponse(response: Response<Matches>): ResponseState<Matches> {
        if(response.isSuccessful) {
            response.body().let { matches ->
                //add date without time for every match
                val regex = Regex(" ([0-9]+(:[0-9]+)+)")
                for(index in matches?.data!!.indices){
                    matches.data[index].matchDate =
                        matches.data[index].match_start.split(regex).toString()
                    matches.data[index].matchDate =
                        matches.data[index].matchDate.replace("]","")
                    matches.data[index].matchDate =
                        matches.data[index].matchDate.replace("[","")
                    matches.data[index].matchDate =
                        matches.data[index].matchDate.replace(",","")
                }
                return ResponseState.Success(matches)
            }
        }
        else return ResponseState.Error("Some thing went wrong")
    }

    private suspend fun getSerieAMatches(apiKey: String, SERIE_A_LEAGUE_ID: Int) =
        withContext(Dispatchers.IO) {
            if(hasInternetConnection()){
                serieAMatchesLiveData.postValue(ResponseState.Loading())
                val response = api.getSerieAMatches(
                    apiKey,
                    SERIE_A_LEAGUE_ID
                )
                serieAMatchesLiveData.postValue(handleSerieAResponse(response))
            }
            else serieAMatchesLiveData.postValue(ResponseState.Error("No internet connection"))
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}