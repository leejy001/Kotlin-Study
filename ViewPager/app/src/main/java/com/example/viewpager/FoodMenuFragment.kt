package com.example.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager.databinding.FoodMenuBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FoodMenuFragment : Fragment() {
    private lateinit var foodMenuPagerAdapter: FoodMenuPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FoodMenuBinding = DataBindingUtil.inflate(inflater, R.layout.food_menu, container,false)
        val viewModels= ViewModelProvider(requireActivity()).get(MainVIewModel::class.java)
        binding.mainViewModel = viewModels

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        foodMenuPagerAdapter = FoodMenuPagerAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = foodMenuPagerAdapter


        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "A"
                1 -> tab.text = "B"
                2 -> tab.text = "C"
            }
        }.attach()
    }
}