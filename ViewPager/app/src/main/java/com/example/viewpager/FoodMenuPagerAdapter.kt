package com.example.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.viewpager.foodmenu.FoodMenuFirstFragment
import com.example.viewpager.foodmenu.FoodMenuSecondFragment
import com.example.viewpager.foodmenu.FoodMenuThirdFragment

class FoodMenuPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragmentList : MutableList<Fragment> = arrayListOf(
        FoodMenuFirstFragment(),
        FoodMenuSecondFragment(),
        FoodMenuThirdFragment()
    )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}