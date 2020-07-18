package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    /** 지정된 클래스의 새 인스턴스 생성
        ViewModel은 추상 클래스이며 이 클래스를 상속하는 것 만으로 ViewModel을 만들수 있다. */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            // ScoreViewModel 구성 및 반환
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}