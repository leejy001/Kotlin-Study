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

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 네트워크에서 devbyte 비디오를 가져와 디스크에 저장하기 위한 저장소.
 */
class VideosRepository (private val database: VideosDatabase) {
    // DatabaseVideo 개체의 LiveData를 도메인 Video 개체로 변환하려면 Transformation.map을 사용
    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }

    // 오프라인 캐시를 새로 고치는 refreshVideo() 함수 정의
    suspend fun refreshVideos() {
        // 네트워크에서 데이터를 가져온 다음 데이터베이스에 저장
        // 코루틴이 IO dispather로 전환
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            // insertAll()을 호출하여 재생 목록을 데이터베이스에 삽입
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}