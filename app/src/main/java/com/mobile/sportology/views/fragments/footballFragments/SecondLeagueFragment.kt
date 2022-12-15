package com.mobile.sportology.views.fragments.footballFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.sportology.R
import com.mobile.sportology.ResponseState
import com.mobile.sportology.api.FootballApi
import com.mobile.sportology.databinding.FragmentLeagueBinding
import com.mobile.sportology.viewModels.FootBallViewModel
import com.mobile.sportology.viewModels.MyViewModelProvider
import com.mobile.sportology.views.adapters.footballAdapters.SecondLeagueRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SecondLeagueFragment: Fragment(R.layout.fragment_league) {
    @Inject
    lateinit var api: FootballApi
    private val footBallViewModel: FootBallViewModel by lazy {
        ViewModelProvider(this, MyViewModelProvider(api, requireActivity().application))[FootBallViewModel::class.java]
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


        val secondLeagueRecyclerViewAdapter = SecondLeagueRecyclerViewAdapter(footBallViewModel)
        binding.leagueRV.apply {
            adapter = secondLeagueRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = null
        }

        footBallViewModel.secondLeagueMatchesLiveData.observe(viewLifecycleOwner) { responseState ->
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

                    secondLeagueRecyclerViewAdapter.differ.submitList(dates)
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