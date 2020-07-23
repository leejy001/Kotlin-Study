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

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {

    @Insert // 삽입
    fun insert(night: SleepNight)

    @Update // 업데이트
    fun update(night: SleepNight)

    // nightId가 key와 일치하면 daily_sleep_quality_table에서 모든 열을 선택
    @Query ("SELECT * from daliy_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long):SleepNight?

    // 삭제
    @Query ("DELETE FROM daliy_sleep_quality_table")
    fun clear()
    /**
    @Delete
    fun deleteAllNights(night: List<SleepNight>): Int
    */

    // 내림차순 정렬
    @Query ("SELECT * FROM daliy_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    // 가장 최근 nightId 선택하여 반환
    @Query ("SELECT * FROM daliy_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?

}
