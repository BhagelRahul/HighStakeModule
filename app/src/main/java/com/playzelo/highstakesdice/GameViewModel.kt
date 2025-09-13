package com.playzelo.highstakesdice

import android.app.Application
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playzelo.highstakesdice.components.SoundManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _tokenType = MutableStateFlow("default")

    val tokenType: StateFlow<String> = _tokenType

    //Dice value

    private val _diceV1 = MutableStateFlow(1)
    val diceV1: StateFlow<Int> = _diceV1

    private val _diceV2 = MutableStateFlow(1)
    val diceV2: StateFlow<Int> = _diceV2

    //token position
    private val _tokenIndex = MutableStateFlow(0)
    val tokenIndex: StateFlow<Int> = _tokenIndex

    private val _isFinalStop = MutableStateFlow(false)
    val isFinalStop: StateFlow<Boolean> = _isFinalStop

    //Bet  States
    private val _betAmount = MutableStateFlow(0.0)
    val betAmount: StateFlow<Double> = _betAmount

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _isRollEnabled = MutableStateFlow(false)
    val isRollEnabled: StateFlow<Boolean> = _isRollEnabled

    //Board Path
    private val boardPath = listOf(
        0, 1, 2, 3,
        7, 11, 6,
        10, 9, 8,
        5, 4
    )


    //Multiplier
    private val multipliers = mapOf(
        0 to 0.0,
        1 to 0.34,
        2 to 0.55,
        3 to 1.66,
        4 to 1.50,
        5 to 0.0,
        6 to 0.0,
        7 to 2.09,
        8 to 1.44,
        9 to 0.0,
        10 to 4.00,
        11 to 2.40


    )

    // functions


    fun placeBet() {
        _betAmount.value = inputText.value.toDoubleOrNull() ?: 0.0
        _isRollEnabled.value = _betAmount.value > 0   // ✅ Bet lagate hi roll enable
    }

    fun rollDice(soundManager: SoundManager) {
        if (_isRollEnabled.value.not()) return  // safety check

        _isRollEnabled.value = false  // ✅ ek click ke baad disable
        _diceV1.value = (1..6).random()
        _diceV2.value = (1..6).random()
        val steps = _diceV1.value + _diceV2.value
        moveToken(steps, soundManager )
    }


    fun onInputChange(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 5) {
            _inputText.value = newValue
        }
    }

//    fun placeBet() {
//        _betAmount.value = inputText.value.toDoubleOrNull() ?: 0.0
//    }
//
//    fun rollDice() {
//        _diceV1.value = (1..6).random()
//        _diceV2.value = (1..6).random()
//        val steps = _diceV1.value + _diceV2.value
//        moveToken(steps)
//    }

    private fun moveToken(steps: Int,soundManager: SoundManager) {

        viewModelScope.launch {
            // 🎯 Hamesha start card se shuru karo
            var currentPos = 0
            _tokenIndex.value = boardPath[currentPos]

            //sound
            //val soundManager = SoundManager(getApplication<Application>().applicationContext)

            for (i in 1 .. steps) {
                delay(300)
                currentPos = (currentPos + 1) % boardPath.size
                _tokenIndex.value = boardPath[currentPos]
                soundManager.TokenSound(0.3f)
                _isFinalStop.value = (i == steps)
            }

            // ✅ multiplier logic

            val multiplier = multipliers[_tokenIndex.value] ?: 1.0
            when {
                multiplier == 0.0 -> _betAmount.value = 0.0
                multiplier < 0.0 -> _betAmount.value /= 2
                else -> _betAmount.value *= multiplier
            }

        }
    }
}
