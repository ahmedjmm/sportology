package com.mobile.sportology.views.adapters.footballAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.sportology.views.fragments.footballFragments.PremierLeagueFragment
import com.mobile.sportology.views.fragments.footballFragments.SerieAFragment

class ViewStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position == 0) return PremierLeagueFragment()
        return SerieAFragment()
    }

    override fun getItemCount() = 2
}