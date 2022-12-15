package com.mobile.sportology.views.fragments.footballFragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mobile.sportology.R
import com.mobile.sportology.ResponseState
import com.mobile.sportology.api.FootballApi
import com.mobile.sportology.databinding.FragmentLeagueBinding
import com.mobile.sportology.models.football.Matches
import com.mobile.sportology.viewModels.FootBallViewModel
import com.mobile.sportology.viewModels.MyViewModelProvider
import com.mobile.sportology.views.adapters.footballAdapters.FirstLeagueRecyclerViewAdapter
import com.mobile.sportology.views.adapters.footballAdapters.MatchRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstLeagueFragment: Fragment(R.layout.fragment_league) {
    @Inject
    lateinit var api: FootballApi
    private val footBallViewModel: FootBallViewModel by lazy {
        ViewModelProvider(this,
            MyViewModelProvider(api, requireActivity().application))[FootBallViewModel::class.java]
    }

    lateinit var binding: FragmentLeagueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firstLeagueRecyclerViewAdapter = FirstLeagueRecyclerViewAdapter(footBallViewModel)
        binding.leagueRV.apply {
            adapter = firstLeagueRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = null
        }

        footBallViewModel.firstLeagueMatchesLiveData.observe(viewLifecycleOwner) { responseState ->
            when(responseState){
                is ResponseState.Success -> {
                    binding.circularProgressIndicator.visibility = View.GONE
                    var dates = mutableListOf<String>()
                    responseState.data?.result?.forEach{
                        dates.add(it.event_date)
                    }
                    dates = dates.distinct().toMutableList()
                    for(index in dates.indices)
                        responseState.data?.dates?.add(dates[index])
                    firstLeagueRecyclerViewAdapter.differ.submitList(dates)
                }
                is ResponseState.Loading -> binding.circularProgressIndicator.visibility = View.VISIBLE
                is ResponseState.Error -> {
                    binding.circularProgressIndicator.visibility = View.GONE
                    Toast.makeText(context, responseState.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}