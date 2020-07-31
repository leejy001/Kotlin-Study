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

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://mars.udacity.com/"

// 웹 서비스에서 예상하는 쿼리 값과 일치하는 열거형 만듬
enum class MarsApiFilter(val value: String) { SHOW_RENT("rent"), SHOW_BUY("buy"), SHOW_ALL("all") }

// Moshi Builder를 사용하여 KotlinJosonAdapterFactory에 Moshi 객체 생성
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

// Retrofit 오브젝트 만들기
// ScalarsConverterFactory와 BASE_URL을 추가하고 Build() 호출
private val retrofit = Retrofit.Builder()
        // Moshi Object로 ConverterFactory를 MoshiConverterFactory로 변경
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        // CoroutineCallAdapterFactory 추가
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

// 인터페이스 설정 및 getProperties() 정의
interface MarsApiService {
    /**
     * Returns a Retrofit callback that delivers a String
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    // MarsApiService를 업데이트하여 MarsProperty 개체 목록 반환
    @GET("realestate")
    fun getProperties(@Query("filter") type: String):
            // call 에서 deferred로 반환 유형 변경
            Deferred<List<MarsProperty>>
}

// RetroFit를 사용하여 MarsApiService 구현을 위한 MarsApi 개체 생성
object MarsApi {
    val retrofitService : MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}
