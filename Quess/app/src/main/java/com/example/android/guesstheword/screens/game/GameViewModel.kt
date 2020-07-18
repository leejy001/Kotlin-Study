package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

// GameViewModel 클래스 만들기

class GameViewModel : ViewModel() {

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    companion object {

        // 게임 끝
        private const val DONE = 0L

        private const val COUNTDOWN_PANIC_SECONDS = 10L

        // 1초
        private const val ONE_SECOND = 1000L

        // 총 경기 시간
        private const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer

    // 현재 _currentTime, _word, _score LiveData 추가하고 캡슐화 하기
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> get() = _currentTime

    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    //internal
    private val _score = MutableLiveData<Int>()
    // external
    val score : LiveData<Int> get() = _score

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType> get() = _eventBuzz

    // Transformation.map을 사용해서 현재 시간 형식화 된 문자열로 변환
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean> get() = _eventGameFinish

    // init 추가
    init {
        Log.i("GameViewModel","GameViewModel created!")
        _eventGameFinish.value = false
        resetList()
        nextWord()
        // _score 값 초기화
        _score.value = 0

        // CountDownTimer 코드
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS )
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventBuzz.value = BuzzType.GAME_OVER
                _eventGameFinish.value = true
            }
        }
        timer.start()
    }

    // Resets the list of words and randomizes the order
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    // Moves to the next word in the list
    private fun nextWord() {
        //Select and remove a word from the list
        // 게임을 끝내지 않고 resetList() 호출
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    /** 버튼 눌렀을 때 동작 **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    // 완료 이벤트
    fun onGameFinishedComplete() {
        _eventGameFinish.value = false
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    // onCleared() 재정의 ViewModel lifecycle 추적
    override fun onCleared() {
        super.onCleared()
        // 타이머 취소
        timer.cancel()
        Log.i("GameViewModel","GameViewModel destroyed!")
    }

}