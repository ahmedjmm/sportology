package com.mobile.sportology.api

import com.mobile.sportology.Constants.Companion.API_KEY
import com.mobile.sportology.Constants.Companion.PREMIER_LEAGUE_SEASON_ID
import com.mobile.sportology.Constants.Companion.SERIE_A_SEASON_ID
import com.mobile.sportology.models.football.Matches
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesApi {
    @GET("api/v1/soccer/matches")
    suspend fun getSerieAMatches(
        @Query("apikey")
        apiKey: String = API_KEY,
        @Query("season_id")
        seasonId: Int = SERIE_A_SEASON_ID
    ): Response<Matches>

    @GET("api/v1/soccer/matches")
    suspend fun getPremierLeagueMatches(
        @Query("apikey")
        apiKey: String = API_KEY,
        @Query("season_id")
        seasonId: Int = PREMIER_LEAGUE_SEASON_ID
    ): Response<Matches>
}