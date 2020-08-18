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
import com.example.viewpager.databinding.FragmentFoodMenuFirstBinding

class FoodMenuFirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentFoodMenuFirstBinding = DataBindingUtil.inflate (
            inflater, R.layout.fragment_food_menu_first, container, false)
        val viewModel = ViewModelProvider(requireActivity()).get(MainVIewModel::class.java)
        binding.mainViewModel = viewModel


        val foodMenuFirstAdapter =
            FoodMenuAdapter(foodMenuListener { firstMenu ->
                Toast.makeText(context, firstMenu.foodName, Toast.LENGTH_SHORT).show()
                val bundle = bundleOf("flag" to 0, "food_name" to firstMenu.foodName, "food_image" to firstMenu.food_image)
                findNavController().navigate(R.id.action_foodMenuFragment_to_foodDetailFragment, bundle)
            })
        binding.firstMenuList.adapter = foodMenuFirstAdapter

        var firstMenuItmes: MutableList<FoodMenuItem> = mutableListOf()
        firstMenuItmes.add(FoodMenuItem(R.drawable.ic_a, "A 1"))
        firstMenuItmes.add(FoodMenuItem(R.drawable.ic_a, "A 2"))
        firstMenuItmes.add(FoodMenuItem(R.drawable.ic_a, "A 3"))
        foodMenuFirstAdapter.data = firstMenuItmes

        return binding.root
    }
}