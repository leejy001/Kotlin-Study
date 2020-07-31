/*
 *  Copyright 2018, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.android.marsrealestate.R
import com.example.android.marsrealestate.network.MarsProperty

class DetailViewModel(marsProperty: MarsProperty, app: Application) : AndroidViewModel(app) {

    // MarsProperty LiveData를 추가
    private val _selectedProperty = MutableLiveData<MarsProperty>()

    // SelectedProperty의 외부 LiveData
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    // _selectedProperty MutableLiveData 초기화
    init {
        _selectedProperty.value = marsProperty
    }

    // 판매 또는 임대 가격을 표시하는 displayPropertyPrice 형식 변환 Map LiveData.
    val displayPropertyPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.display_price_monthly_rental
                    false -> R.string.display_price
                }, it.price)
    }

    // displayProperty유형 형식 변환 맵 라이브데이터("임대/판매용" 문자열 표시)
    val displayPropertyType = Transformations.map(selectedProperty) {
        app.applicationContext.getString(R.string.display_type,
                app.applicationContext.getString(
                        when(it.isRental) {
                            true -> R.string.type_rent
                            false -> R.string.type_sale
                        }))
    }
}
