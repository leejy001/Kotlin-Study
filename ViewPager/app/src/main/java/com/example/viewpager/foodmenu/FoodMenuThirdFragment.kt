package com.example.viewpager.foodmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.viewpager.*
import com.example.viewpager.databinding.FragmentFoodMenuThirdBinding

class FoodMenuThirdFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFoodMenuThirdBinding = DataBindingUtil.inflate (
            inflater, R.layout.fragment_food_menu_third, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(MainVIewModel::class.java)
        binding.mainViewModel = viewModel


        val foodMenuThirdAdapter =
            FoodMenuAdapter(foodMenuListener { thirdMenu ->
                Toast.makeText(context, thirdMenu.foodName, Toast.LENGTH_SHORT).show()
                val bundle = bundleOf("flag" to 0, "food_name" to thirdMenu.foodName,"food_image" to thirdMenu.food_image)
                findNavController().navigate(R.id.action_foodMenuFragment_to_foodDetailFragment, bundle)
            })
        binding.thirdMenuList.adapter = foodMenuThirdAdapter

        var thirdMenuItmes: MutableList<FoodMenuItem> = mutableListOf()
        thirdMenuItmes.add(FoodMenuItem(R.drawable.ic_c, "C 1"))
        thirdMenuItmes.add(FoodMenuItem(R.drawable.ic_c, "C 2"))
        thirdMenuItmes.add(FoodMenuItem(R.drawable.ic_c, "C 3"))
        thirdMenuItmes.add(FoodMenuItem(R.drawable.ic_c, "C 4"))
        thirdMenuItmes.add(FoodMenuItem(R.drawable.ic_c, "C 5"))
        foodMenuThirdAdapter.data = thirdMenuItmes

        return binding.root
    }
}