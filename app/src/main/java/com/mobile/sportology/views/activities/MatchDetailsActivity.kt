package com.mobile.sportology.views.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.mobile.sportology.Constants
import com.mobile.sportology.R
import com.mobile.sportology.ResponseState
import com.mobile.sportology.api.FootballApi
import com.mobile.sportology.databinding.ActivityMatchDetailsBinding
import com.mobile.sportology.databinding.MatchDetailsStatisticsBinding
import com.mobile.sportology.models.football.MatchDetails
import com.mobile.sportology.viewModels.MatchDetailsViewModel
import com.mobile.sportology.viewModels.MyViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MatchDetailsActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMatchDetailsBinding
    @Inject lateinit var api: FootballApi
    private val args: MatchDetailsActivityArgs by navArgs()
    private val matchDetailsViewModel by lazy {
        ViewModelProvider(this,
            MyViewModelProvider(api, application))[MatchDetailsViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_match_details)
        lifecycleScope.launch(Dispatchers.IO) {
            matchDetailsViewModel.getMatchDetails(Constants.API_KEY, args.eventId, Constants.DubaiTimeZone)
        }

        matchDetailsViewModel.matchDetails.observe(this){
            when(it) {
                is ResponseState.Success -> {
                    mainBinding.matchDetails = it.data!!.result[0]
                    mainBinding.included.loading.visibility = View.GONE
                    mainBinding.included.errorLayout.visibility = View.GONE
                    it.data.result.forEach{ matchResult -> createStatisticsLayout(matchResult) }
                }
                is ResponseState.Loading -> {
                    mainBinding.included.loading.visibility = View.VISIBLE
                    mainBinding.included.errorLayout.visibility = View.GONE
                }
                is ResponseState.Error -> {
                    mainBinding.included.errorLayout.visibility = View.VISIBLE
                    mainBinding.included.errorText.text = it.message
                }
            }
        }
    }

    private fun createStatisticsLayout(matchDetails: MatchDetails.Result) {
        if(matchDetails.statistics.isNotEmpty()) {
            matchDetails.statistics.forEach {
                val binding = MatchDetailsStatisticsBinding.inflate(layoutInflater,
                    mainBinding.included.body.rootView as ViewGroup, false)
                val delimiter = " "
                if(it.type.contains(delimiter)){
                    var manipulateString = it.type.split(delimiter).toString()
                    if(manipulateString.contains(","))
                        manipulateString = manipulateString.replace(",", "\n")
                    if(manipulateString.contains("["))
                        manipulateString = manipulateString.replace("[", "")
                    if(manipulateString.contains("]"))
                        manipulateString = manipulateString.replace("]", "")
                    it.type = manipulateString
                }
                binding.matchDetails = it
                mainBinding.included.body.addView(binding.root)
            }
        }
        else {
            val emptyResultTextView = TextView(this)
            emptyResultTextView.text = "Not provided"
            emptyResultTextView.gravity = Gravity.CENTER
            mainBinding.included.body.addView(emptyResultTextView)
        }
    }
}