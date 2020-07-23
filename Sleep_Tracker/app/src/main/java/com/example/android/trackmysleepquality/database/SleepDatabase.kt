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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 추상클래스 작성
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {
    // 추상 값 선언
    abstract val sleepDatabaseDao: SleepDatabaseDao
    // companion object 선언
    companion object {
        // private nullable 변수 인스턴스 선언
        @Volatile
        private var INSTANCE: SleepDatabase? = null
        fun getInstance(context: Context) : SleepDatabase {
            synchronized(this){
                var instance = INSTANCE

                /** instance가 null 이면 Room의 databaseBuilder를 호출하고
                 * 전달된 context, database class 및 database 이름 제공
                 * */
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"
                    ).fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}