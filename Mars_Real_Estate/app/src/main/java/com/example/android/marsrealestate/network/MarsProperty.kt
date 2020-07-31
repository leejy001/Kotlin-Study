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

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// Json과 일치하는 속성이 포함된 데이터 클래스 생성
@Parcelize
data class MarsProperty(
        val id: String,
        // @Json이용하여 img_src를 데이터 클래스의 imgSrcUrl에 재매핑
        @Json(name = "img_src") val imgSrcUrl: String,
        val type: String,
        val price: Double ):Parcelable {
    val isRental
        get() = type == "rent"
}
