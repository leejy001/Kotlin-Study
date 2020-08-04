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

package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException

// 비동기 코드와 스레딩을 처리하기 위해 코루틴을 사용
class RefreshDataWorker(appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    // doWork() 재정의
    override suspend fun doWork(): Payload {
        // 데이터베이스 및 리포지토리에 대한 변수를 추가
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        return try {
            repository.refreshVideos()
            Payload(Result.SUCCESS)
        } catch (exception: HttpException) {
            Payload(Result.RETRY)
        }
    }
}