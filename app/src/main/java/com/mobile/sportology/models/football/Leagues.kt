package com.mobile.sportology.models.football

data class Leagues(
    val result: List<Result>,
    val success: Int
) {
    data class Result(
        val country_key: Int,
        val country_logo: String,
        val country_name: String,
        val league_key: Int,
        val league_logo: String,
        val league_name: String
    )
}