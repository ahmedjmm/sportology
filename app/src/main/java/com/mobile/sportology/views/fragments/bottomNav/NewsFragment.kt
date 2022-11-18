package com.mobile.sportology.views.fragments.bottomNav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.sportology.R
import com.mobile.sportology.databinding.FragmentCricketBinding
import com.mobile.sportology.databinding.FragmentNewsBinding

class NewsFragment : Fragment(R.layout.fragment_news) {
    lateinit var _binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}