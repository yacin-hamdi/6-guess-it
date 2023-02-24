package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    companion object{
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN = 10000L
    }

    private lateinit var wordList:MutableList<String>
    private lateinit var timer: CountDownTimer

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    private var _word = MutableLiveData<String>()
    val word:LiveData<String>
    get() = _word

    private var _hasFinished = MutableLiveData<Boolean>()
    val hasFinished:LiveData<Boolean>
    get() = _hasFinished

    private var _currentTime = MutableLiveData<Long>()
    val currentTimeString = Transformations.map(_currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init{
        _score.value = 0
        _hasFinished.value = false
        resetList()
        nextWord()

        timer = object:CountDownTimer(COUNTDOWN, ONE_SECOND){
            override fun onTick(p0: Long) {
                _currentTime.value = p0/ONE_SECOND
            }

            override fun onFinish() {
                _hasFinished.value = true
            }
        }

        timer.start()

    }

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

    private fun nextWord(){
        if(wordList.isEmpty()){
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip(){
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect(){
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun hasFinishedComplete(){
        _hasFinished.value = false
    }
}