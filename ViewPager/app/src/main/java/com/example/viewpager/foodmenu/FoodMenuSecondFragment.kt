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
import com.example.viewpager.databinding.FragmentFoodMenuSecondBinding

class FoodMenuSecondFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFoodMenuSecondBinding = DataBindingUtil.inflate (
            inflater, R.layout.fragment_food_menu_second, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(MainVIewModel::class.java)
        binding.mainViewModel = viewModel


        val foodMenuSecondAdapter =
            FoodMenuAdapter(foodMenuListener { secondMenu ->
                Toast.makeText(context, secondMenu.foodName, Toast.LENGTH_SHORT).show()
                val bundle = bundleOf("flag" to 0, "food_name" to secondMenu.foodName, "food_image" to secondMenu.food_image)
                findNavController().navigate(R.id.action_foodMenuFragment_to_foodDetailFragment, bundle)
            })
        binding.secondMenuList.adapter = foodMenuSecondAdapter

        var secondMenuItmes: MutableList<FoodMenuItem> = mutableListOf()
        secondMenuItmes.add(FoodMenuItem(R.drawable.ic_b, "B 1"))
        secondMenuItmes.add(FoodMenuItem(R.drawable.ic_b, "B 2"))
        secondMenuItmes.add(FoodMenuItem(R.drawable.ic_b, "B 3"))
        secondMenuItmes.add(FoodMenuItem(R.drawable.ic_b, "B 4"))
        foodMenuSecondAdapter.data = secondMenuItmes

        return binding.root

    }
}
