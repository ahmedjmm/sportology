package com.mobile.sportology.viewsUtils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("homeScore", "awayScore", "statusCode")
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

