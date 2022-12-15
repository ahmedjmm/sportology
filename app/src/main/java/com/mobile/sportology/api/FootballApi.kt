package com.mobile.sportology.api

import com.mobile.sportology.models.football.Leagues
import com.mobile.sportology.models.football.MatchDetails
import com.mobile.sportology.models.football.Matches
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballApi {
    @GET("football/?met=Fixtures")
    suspend fun get1stLeagueMatches(
        @Query("APIkey")
        apiKey: String,
        @Query("leagueId")
        leagueId: Int,
        @Query("from")
        from: String,
        @Query("to")
        to: String,
        @Query("timezone")
        timeZone:String
    ): Response<Matches>

    @GET("football/?met=Fixtures")
    suspend fun get2ndLeagueMatches(
        @Query("APIkey")
        apiKey: String,
        @Query("leagueId")
        leagueId: Int,
        @Query("from")
        from: String,
        @Query("to")
        to: String,
        @Query("timezone")
        timeZone:String
    ): Response<Matches>

    @GET("football/?met=Leagues")
    suspend fun getCurrentLeagues(
        @Query("APIkey")
        apiKey: String,
    ): Response<Leagues>

    @GET("/football/?met=Fixtures")
    suspend fun getMatchDetails(
        @Query("APIkey")
        apiKey: String,
        @Query("matchId")
        matchId: Int,
        @Query("timezone")
        timeZone: String
    ): Response<MatchDetails>
}