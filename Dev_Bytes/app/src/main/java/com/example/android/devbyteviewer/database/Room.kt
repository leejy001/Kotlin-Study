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
 *
 */

package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    // 삽입 기능 추가
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

@Database(entities = [DatabaseVideo::class], version = 1)
// RoomDatabase를 확장하는 추상 VideosDatabase Class
abstract class VideosDatabase: RoomDatabase() {
    abstract val videoDao: VideoDao
}

// 싱글 톤 생성 (하나의 인스턴스만 가질 수 있는 객체)
private lateinit var INSTANCE: VideosDatabase

fun getDatabase(context: Context): VideosDatabase {
    // 코드 동기화
    synchronized(VideosDatabase::class.java) {
        // 인스턴스가 초기화 되었는지 확인
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, VideosDatabase::class.java, "videos").build()
        }
    }
    return INSTANCE
}