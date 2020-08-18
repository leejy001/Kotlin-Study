package com.example.viewpager.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.viewpager.FoodMenuAdapter
import com.example.viewpager.MainVIewModel
import com.example.viewpager.R
import com.example.viewpager.databinding.FoodDetailBinding
import com.example.viewpager.foodMenuListener
import kotlinx.android.synthetic.main.food_detail.*

class FoodDetailFragment : Fragment() {

    val food: Map<String, Int> = mapOf(
        Pair("A 1", 1000),
        Pair("A 2", 1100),
        Pair("A 3", 1200),
        Pair("B 1", 1300),
        Pair("B 2", 1400),
        Pair("B 3", 1500),
        Pair("B 4", 1600),
        Pair("C 1", 1700),
        Pair("C 2", 1800),
        Pair("C 3", 1900),
        Pair("C 4", 2000),
        Pair("C 5", 2100)
    )

    private lateinit var food_name: TextView
    private lateinit var food_price: TextView
    private lateinit var food_image: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FoodDetailBinding = DataBindingUtil.inflate (
            inflater, R.layout.food_detail, container, false)
        val foodName = arguments?.getString("food_name")
        food_name = binding.foodName
        val foodPrice = food.get(foodName)
        food_price = binding.foodPrice
        val foodImage = arguments?.getInt("food_image")
        food_image = binding!!.foodImage

        food_name.setText(foodName)
        food_price.setText(foodPrice.toString())
        food_image.setImageResource(foodImage!!)

        binding.button.setOnClickListener {
            val status = arguments?.getInt("flag")
            if(status == 0) {
                findNavController().navigate(R.id.action_foodDetailFragment_to_foodMenuFragment)
            }
        }
        return binding.root
    }
}
