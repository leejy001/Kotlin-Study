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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {
    // viewModelJob 정의 및 할당
    private var viewModelJob = Job()
    
    // onCleared() 재정의, 모든 코루틴 취소
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    // 코루틴에 대한 uiScope 정의
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    // MutableLiveData 설정
    private var tonight = MutableLiveData<SleepNight>()
    // database에서 getAllNight() 할당
    private val nights = database.getAllNights()

    // Utility.kt의 formatNights()이용
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }
    // SleepNight가 null일 때
    val startButtonVisible = Transformations.map(tonight) {
        null == it
    }
    // SleepNight가 null이 아닐 때
    val stopButtonVisible = Transformations.map(tonight) {
        null != it
    }
    // 초기화
    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }
    // 캡슐화된 이벤트 생성
    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent: LiveData<Boolean>get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    // onStopTracking()에서 탐색할 때 변경되는 LiveData를 설정
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight>get() = _navigateToSleepQuality
    // 이벤트를 재설정하는 doneNavigating() 추가
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    init {
        initializeTonight()
    }

    // uiScope에서 코루틴 실행
    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        // 코루틴 실행을 I/O 스레드로 이동하여 호출 함수를 기본 안전 함수로 만듬
       return withContext(Dispatchers.IO) {
            var night = database.getTonight()
           // 시작, 종료 시간이 같지 않으면 null 반환 그렇지 않으면 night 반환
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    // start button의 클릭 핸들러인 onStartTracking 구현
    fun onStartTracking() {
        uiScope.launch {
            //현재 시간을 시작 시간으로 잡고, insert() 호출
            var newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    // 삽입 함수
    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            // night를 database에 삽입
            database.insert(night)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            // 현재 시스템 설정
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }
    // update 함수
    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
            // 이벤트 trigger하려면 true로 설정
            _showSnackbarEvent.value = true
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
}

