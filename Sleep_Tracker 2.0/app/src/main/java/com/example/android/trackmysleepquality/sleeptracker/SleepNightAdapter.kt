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

package com.example.android.trackmysleepquality.sleeptracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

// RecyclerView.Adapter -> ListAdapter
class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    // 어댑터 클래스 상속시 구현할 3가지 함수 : getItemCount, onBindViewHolder, onCreateViewHolder
    // 리사이클러뷰에 들어갈 뷰 폴더의 개수 (ListAdapter 구현 시 재정의 할 필요 없음)
    //override fun getItemCount() = data.size
    // 리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수, 뷰 홀더는 실제 레이아웃 파일과 매핑
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // onBindViewHolder (holder, position) 실제 각 뷰 홀더에 데이터 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        //val item = data[position]
        // Quality가 1이하이면 red
        if (item.sleepQuality <= 1) {
            holder.binding.sleepLength.setTextColor(Color.RED)
        } else {
            // reset
            holder.binding.sleepLength.setTextColor(Color.BLACK)
        }
        holder.binding.sleepLength.text = item.sleepQuality.toString()
        holder.bind(item)
    }

    // 뷰 홀더 속성 생성, 클래스 내부에만 호출하도록 생성자를 private로 만듬
    // LayoutInflater를 ListItemSleepNightBinding으로 바꿈, 부모 클래스에 대한 호출 업데이트 (binding.root)
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {
        /* // Refactor -> inline 작업 모든 참조 inline 선택 후 속성 제거
        val sleepLength: TextView = binding.sleepLength
        val quality: TextView = binding.qualityString
        val qualityImage: ImageView = binding.qualityImage
        */

        // bind 함수
        fun bind(item: SleepNight) {
            /** SleepNightAdapter.ViewHolder.bind 코드를 SleepNight항목에 대한 단일 바인딩으로 교체하고,
             *  executePendingBindings()을 실행하기
             */
            binding.sleep = item
            binding.executePendingBindings()
            /* BindingUtils.kt
            val res = itemView.context.resources
            binding.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            // 수치 Quality를 문자열로 변환
            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            }) */
        }

        companion object {
            // from은 companion objact에 있어 생성자 호출이 가능하지만 다른 class에서는 불가능
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                // 새 바인딩 개체 생성
                val binding = ListItemSleepNightBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
}

// SleepNightDiffCallback 클래스 생성
class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}