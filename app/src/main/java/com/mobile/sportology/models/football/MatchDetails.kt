package com.mobile.sportology.models.football

data class MatchDetails(
    val result: List<Result>,
    val success: Int
) {
    data class Result(
        val away_team_key: Int,
        val away_team_logo: String,
        val cards: List<Card>,
        val country_logo: String,
        val country_name: String,
        val event_away_formation: String,
        val event_away_team: String,
        val event_country_key: Int,
        val event_date: String,
        val event_final_result: String,
        val event_ft_result: String,
        val event_halftime_result: String,
        val event_home_formation: String,
        val event_home_team: String,
        val event_key: Int,
        val event_live: String,
        val event_penalty_result: String,
        val event_referee: String,
        val event_stadium: String,
        val event_status: String,
        val event_time: String,
        val fk_stage_key: Int,
        val goalscorers: List<Goalscorer>,
        val home_team_key: Int,
        val home_team_logo: String,
        val league_group: Any?,
        val league_key: Int,
        val league_logo: String,
        val league_name: String,
        val league_round: String,
        val league_season: String,
        val lineups: Lineups,
        val stage_name: String,
        val statistics: List<Statistic>,
        val substitutes: List<Substitute>
    ) {
        data class Card(
            val away_fault: String,
            val away_player_id: String,
            val card: String,
            val home_fault: String,
            val home_player_id: String,
            val info: String,
            val info_time: String,
            val time: String
        )

        data class Goalscorer(
            val away_assist: String,
            val away_assist_id: String,
            val away_scorer: String,
            val away_scorer_id: String,
            val home_assist: String,
            val home_assist_id: String,
            val home_scorer: String,
            val home_scorer_id: String,
            val info: String,
            val info_time: String,
            val score: String,
            val time: String
        )

        data class Lineups(
            val away_team: AwayTeam,
            val home_team: HomeTeam
        ) {
            data class AwayTeam(
                val coaches: List<Any>,
                val missing_players: List<Any>,
                val starting_lineups: List<Any>,
                val substitutes: List<Any>
            )

            data class HomeTeam(
                val coaches: List<Any>,
                val missing_players: List<Any>,
                val starting_lineups: List<Any>,
                val substitutes: List<Any>
            )
        }

        data class Statistic(
            val away: String,
            val home: String,
            var type: String
        )

        data class Substitute(
            val away_assist: String,
            val away_scorers: List<AwayScorer>,
            val home_assist: String,
            val home_scorers: List<HomeScorer>,
            val info: String,
            val info_time: String,
            val score: String,
            val time: String
        ) {
            data class AwayScorer(
                val `in`: String,
                val in_id: Int,
                val `out`: String,
                val out_id: Int
            )

            data class HomeScorer(
                val `in`: String,
                val in_id: Int,
                val `out`: String,
                val out_id: Int
            )
        }
    }
}