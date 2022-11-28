package com.mobile.sportology.views.fragments.bottomNav

import android.os.Bundle
import android.text.Layout.Alignment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.mobile.sportology.R
import com.mobile.sportology.api.FootballApi
import com.mobile.sportology.databinding.FragmentFootballBinding
import com.mobile.sportology.databinding.TabItemBinding
import com.mobile.sportology.viewModels.FootBallViewModel
import com.mobile.sportology.viewModels.MyViewModelProvider
import com.mobile.sportology.views.adapters.footballAdapters.ViewStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FootBallFragment : Fragment(R.layout.fragment_football) {
    @Inject
    lateinit var api: FootballApi
    private val footBallViewModel: FootBallViewModel by lazy {
        ViewModelProvider(this, MyViewModelProvider(api, requireActivity().application))[FootBallViewModel::class.java]
    }
    private lateinit var binding: FragmentFootballBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFootballBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.apply {
            adapter = ViewStateAdapter(childFragmentManager, lifecycle)
            isUserInputEnabled = false
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                }
            })
        }

        footBallViewModel.leaguesLiveData.observe(viewLifecycleOwner) {
            it.data.let {
                    leagues ->
                leagues?.result?.forEach {
                    item ->
                    val tabBinding = TabItemBinding.inflate(layoutInflater)
                    tabBinding.league = item
                    binding.tabLayout.addTab(
                        binding.tabLayout.newTab()
                            .setCustomView(tabBinding.root))
                }
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}