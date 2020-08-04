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

package com.example.android.devbyteviewer

import android.app.Application
import android.os.Build
import androidx.work.*
import com.example.android.devbyteviewer.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Override application to setup background work via WorkManager
 */
class DevByteApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    // background 코루틴에서 setupRecurringWork를 호출하는 delayedInit 만들기
    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    // setupRecurringWork()에서 Builder를 사용하여 제약 조건을 정의
    private fun setupRecurringWork() {
        // 네트워크 액세스가 없거나 장치가 배터리 부족일 때 작업이 발생하지 않도록 제약 조건을 정의
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }.build()

        // RefreshDataWorker에 대해 PeriodyWorkRequestBuilder를 사용하고 PeriodyWorkRequest를 생성하는 repeatingRequest를 정의
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        // 고유 작업 스케줄 지정
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }

}
