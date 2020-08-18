package com.example.viewpager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.viewpager.model.User

class MainVIewModel (application: Application) : AndroidViewModel(application){
    private var clickItems: MutableList<User> = mutableListOf()

    fun getOrderItems() = clickItems

    fun addOrderedItems(item: User) {
        var itemFlag = false

        if(!itemFlag){
            clickItems.add(item)
        }
    }
}