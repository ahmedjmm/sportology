package com.mobile.sportology.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.sportology.Constants
import com.mobile.sportology.Constants.Ghana_ID
import com.mobile.sportology.Constants.Lithuania_ID
import com.mobile.sportology.MyApplication
import com.mobile.sportology.ResponseState
import com.mobile.sportology.api.FootballApi
import com.mobile.sportology.models.football.Leagues
import com.mobile.sportology.models.football.Matches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class FootBallViewModel @Inject constructor(
    val api: FootballApi,
    app: Application
): AndroidViewModel(app) {
    val firstLeagueMatchesLiveData: MutableLiveData<ResponseState<Matches>> = MutableLiveData()
    val secondLeagueMatchesLiveData: MutableLiveData<ResponseState<Matches>> = MutableLiveData()
    val leaguesLiveData: MutableLiveData<ResponseState<Leagues>> = MutableLiveData()

    init {
        viewModelScope.launch{
            if(hasInternetConnection()){
                try {
                    getLeagues(Constants.API_KEY)
                    get1stLeagueMatches(Constants.API_KEY, Ghana_ID, "2022-01-01", "2023-12-30")
                    get2ndLeagueMatches(Constants.API_KEY, Lithuania_ID, "2022-01-01", "2023-12-30")
                }
                catch (exception: SocketTimeoutException) {
                    secondLeagueMatchesLiveData.value = ResponseState.Error("Can't connect to server")
                }
            }
            else {
                firstLeagueMatchesLiveData.value = ResponseState.Error("No internet connection")
                secondLeagueMatchesLiveData.value = ResponseState.Error("No internet connection")
            }
        }
    }

    private suspend fun getLeagues(apiKey: String) = withContext(Dispatchers.IO) {
        if(hasInternetConnection()){
            leaguesLiveData.postValue(ResponseState.Loading())
            try {
                val response = api.getCurrentLeagues(
                    apiKey,
                )
                leaguesLiveData.postValue(ResponseState.Success(response.body()!!))
            }
            catch (exception: SocketTimeoutException) {
                leaguesLiveData.postValue(ResponseState.Error("Can't connect to server"))
            }

        }
        else leaguesLiveData.postValue(ResponseState.Error("No internet connection"))
    }

    private suspend fun get1stLeagueMatches(apiKey: String, LEAGUE_ID: Int, from: String, to: String) =
        withContext(Dispatchers.IO) {
            if(hasInternetConnection()){
                firstLeagueMatchesLiveData.postValue(ResponseState.Loading())
                try {
                    val response = api.get1stLeagueMatches(
                        apiKey,
                        LEAGUE_ID,
                        from,
                        to
                    )
                    firstLeagueMatchesLiveData.postValue(ResponseState.Success(response.body()!!))
                }
                catch (exception: SocketTimeoutException) {
                    firstLeagueMatchesLiveData.postValue(ResponseState.Error("Can't connect to server"))
                }
            }
            else firstLeagueMatchesLiveData.postValue(ResponseState.Error("No internet connection"))
    }

    private suspend fun get2ndLeagueMatches(apiKey: String, LEAGUE_ID: Int, from: String, to: String) =
        withContext(Dispatchers.IO) {
            if(hasInternetConnection()){
                secondLeagueMatchesLiveData.postValue(ResponseState.Loading())
                try {
                    val response = api.get2ndLeagueMatches(
                        apiKey,
                        LEAGUE_ID,
                        from,
                        to
                    )
                    Log.i("requestUrl", response.raw().request.url.toString())
                    secondLeagueMatchesLiveData.postValue(ResponseState.Success(response.body()!!))
                }
                catch (exception: SocketTimeoutException) {
                    secondLeagueMatchesLiveData.postValue(ResponseState.Error("Can't connect to server"))
                }
            }
            else secondLeagueMatchesLiveData.postValue(ResponseState.Error("No internet connection"))
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