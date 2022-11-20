package com.mobile.sportology.models.football

import android.media.audiofx.DynamicsProcessing
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter

data class Matches(
    val data: List<Data>,
    val query: Query,
    var dates: MutableList<Data.Date>
) {
    data class Data(
        val away_team: AwayTeam,
        val group: Group?,
        val home_team: HomeTeam,
        val league_id: Int,
        val match_id: Int,
        var matchDate: String,
        val match_start: String,
        val match_start_iso: String,
        val minute: Int?,
        val referee_id: Int?,
        val round: Round,
        val season_id: Int,
        val stage: DynamicsProcessing.Stage,
        val stats: Stats,
        val status: String,
        val status_code: Int,
        val venue: Venue?
    ) {

//            companion object {
//            @JvmStatic
//            @BindingAdapter("homeScore", "awayScore", "statusCode")
//            fun getScore(textView: TextView, homeScore:Int, awayScore: Int, statusCode: Int) {
//                when (statusCode) {
//                    0 -> textView.text = "Not started"
//                    3 -> textView.text = "$homeScore - $awayScore"
//                    4 -> textView.text = "Postponed"
//                    5 -> textView.text = "Canceled"
//                    11 -> textView.text = "$homeScore HT $awayScore"
//                    else -> textView.text = "Not provided"
//                }
//            }
//        }

        data class AwayTeam(
            val common_name: String,
            val country: Country?,
            val logo: String,
            val name: String,
            val short_code: String,
            val team_id: Int
        ) {
            data class Country(
                val continent: String,
                val country_code: String,
                val country_id: Int,
                val name: String
            )
        }

        data class Group(
            val group_id: Int,
            val group_name: String
        )

        data class HomeTeam(
            val common_name: String,
            val country: Country?,
            val logo: String,
            val name: String,
            val short_code: String,
            val team_id: Int
        ) {
            data class Country(
                val continent: String,
                val country_code: String,
                val country_id: Int,
                val name: String
            )
        }

        data class Round(
            val is_current: Int?,
            val name: String,
            val round_id: Int
        )

        data class Stage(
            val name: String,
            val stage_id: Int
        )

        data class Stats(
            val away_score: Int,
            val et_score: Any?,
            val ft_score: String?,
            val home_score: Int,
            val ht_score: String?,
            val ps_score: Any?
        )

        data class Venue(
            val capacity: Int,
            val city: String,
            val country_id: Int,
            val name: String,
            val venue_id: Int
        )

        data class Date(
            var date:String
        )
    }

    data class Query(
        val apikey: String,
        val season_id: String
    )
}