package com.example.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodMenuAdapter(private val clickListener: foodMenuListener) : RecyclerView.Adapter<FoodMenuAdapter.ViewHolder>() {
    var data = listOf<FoodMenuItem>()
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.menu_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val foodImage: ImageView = itemView.findViewById(R.id.menu_image)
        private val foodName: TextView = itemView.findViewById(R.id.menu_string)

        fun bind(clickListener: foodMenuListener, item: FoodMenuItem){
            if(item.food_image == 0){
                foodImage.setImageResource(R.drawable.ic_launcher_background)
            } else{
                foodImage.setImageResource(item.food_image)
            }
            foodName.text = item.foodName
            itemView.setOnClickListener{
                clickListener.onClick(item)
            }
        }
    }
}

class foodMenuListener(val clickListener: (order: FoodMenuItem) -> Unit) {
    fun onClick(order: FoodMenuItem) = clickListener(order)
}