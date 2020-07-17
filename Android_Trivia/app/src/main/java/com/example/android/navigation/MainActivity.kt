/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // drawerLayout 선언
    private  lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // drawerLayout 초기화
        drawerLayout = binding.drawerLayout

        // NavigationUI.setupWithNavController를 사용하여 NavController를 ActionBar에 연결
        val navController = this.findNavController(R.id.myNavHostFragment)
        // DrawerLayout을 setupActionBarWithNavController의 세 번째 매개 변수로 추가
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        // 시작 대상에 없는 경우 탐색 방지
        navController.addOnDestinationChangedListener { nc:NavController, nd: NavDestination, args: Bundle? ->
            if(nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    // 1) onSupportNavigateUp 오버라이드 (뒤로가기 기능)
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        // 2) navController.navigateUp을 NavigationUI.navigateUp으로 drawerLayout을 매개 변수로 바꾸기.
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
