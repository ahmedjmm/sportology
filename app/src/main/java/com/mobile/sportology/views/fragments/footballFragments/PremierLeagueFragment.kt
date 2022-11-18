package com.mobile.sportology.views.fragments.footballFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.sportology.R
import com.mobile.sportology.ResponseState
import com.mobile.sportology.databinding.FragmentLeagueBinding
import com.mobile.sportology.models.football.Matches
import com.mobile.sportology.viewModels.FootBallViewModel
import com.mobile.sportology.viewModels.MyViewModelProvider
import com.mobile.sportology.views.adapters.footballAdapters.PremierLeagueRecyclerViewAdapter

class PremierLeagueFragment: Fragment(R.layout.fragment_league) {
    private val footBallViewModel by lazy {
        ViewModelProvider(this, MyViewModelProvider(requireActivity().application))[FootBallViewModel::class.java]
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

        val premierLeagueRecyclerViewAdapter = PremierLeagueRecyclerViewAdapter(footBallViewModel)
        binding.leagueRV.apply {
            adapter = premierLeagueRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = null
        }

        footBallViewModel.premierLeagueMatchesLiveData.observe(viewLifecycleOwner) { responseState ->
            when(responseState){
                is ResponseState.Success -> {
                    binding.circularProgressIndicator.visibility = View.GONE

                    var dates = mutableListOf<String>()
                    responseState.data?.data?.forEach{
                        dates.add(it.matchDate)
                    }
                    dates = dates.distinct().toMutableList()
                    responseState.data?.dates = mutableListOf()

                    for(index in dates.indices)
                        responseState.data?.dates?.add(Matches.Data.Date(dates[index]))

                    premierLeagueRecyclerViewAdapter.differ.submitList(responseState.data?.dates?.toMutableList())
                }
                is ResponseState.Loading -> binding.circularProgressIndicator.visibility = View.VISIBLE
                is ResponseState.Error -> {
                    binding.circularProgressIndicator.visibility = View.GONE
                    Toast.makeText(activity, responseState.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}