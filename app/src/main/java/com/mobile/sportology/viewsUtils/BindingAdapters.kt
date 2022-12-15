package com.mobile.sportology.viewsUtils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mobile.sportology.R

@BindingAdapter("score", "status", "eventTime")
fun bindScore(textView: TextView, score: String, status: String, eventTime: String) {
    when(score){
        "" ->
            textView.text = if(status == "") "Not provided\n$eventTime" else status + "\n" + eventTime
        else -> textView.text = status + "\n" + score
    }
}

@BindingAdapter("url")
fun bindTeamLogo(imageView: ImageView, teamLogo: String?){
    if(teamLogo != "" && teamLogo != null)
        Glide.with(imageView.context)
            .load(teamLogo)
            .into(imageView)
    else
        Glide.with(imageView.context)
        .load(R.drawable.ic_football)
        .into(imageView)
}

@BindingAdapter("leagueLogo")
fun bindLeagueLogo(imageView: ImageView, leagueLogo: String) {
    if(leagueLogo != "")
        Glide.with(imageView.context)
            .load(leagueLogo)
            .into(imageView)
    else
        Glide.with(imageView.context)
            .load(R.drawable.ic_football)
            .into(imageView)
}