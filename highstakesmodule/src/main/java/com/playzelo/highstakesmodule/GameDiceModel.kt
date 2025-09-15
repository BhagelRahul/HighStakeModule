package com.playzelo.highstakesmodule


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playzelo.highstakesmodule.components.SoundManager
import com.playzelo.highstakesmodule.data.ApiClient
import com.playzelo.highstakesmodule.data.RollRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation


class GameDiceModel : ViewModel() {
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

//    fun rollDice(soundManager: SoundManager) {
//        if (_isRollEnabled.value.not()) return  // safety check
//
//        _isRollEnabled.value = false  // ✅ ek click ke baad disable
//        _diceV1.value = (1..6).random()
//        _diceV2.value = (1..6).random()
//        val steps = _diceV1.value + _diceV2.value
//        moveToken(steps, soundManager)
//    }
private var _pendingBalance: Double = 0.0 // temp balance, token rukne ke liye

    fun rollDiceFromApi(userId: String, soundManager: SoundManager) {
        if (!_isRollEnabled.value) return
        _isRollEnabled.value = false

        viewModelScope.launch {
            try {
                val response = ApiClient.api.rollDice(
                    RollRequest(userId, _betAmount.value)
                )

                _diceV1.value = response.dice.getOrNull(0) ?: (1..6).random()
                _diceV2.value = response.dice.getOrNull(1) ?: (1..6).random()
                val steps = response.dice.sum()

                _pendingBalance = response.walletBalance.toDouble()

                moveToken(steps, soundManager)

            } catch (e: Exception) {
                e.printStackTrace()
                // Fallback dice
                _diceV1.value = (1..6).random()
                _diceV2.value = (1..6).random()
                val steps = _diceV1.value + _diceV2.value

                moveToken(steps, soundManager) // ✅ token movement ensure
            }
        }
    }





    fun onInputChange(newValue: String) {
        if (newValue.all { it.isDigit() } && newValue.length <= 5) {
            _inputText.value = newValue
        }
    }

//    private fun moveToken(steps: Int, soundManager: SoundManager) {
//
//        viewModelScope.launch {
//            // 🎯 Hamesha start card se shuru karo
//            var currentPos = 0
//            _tokenIndex.value = boardPath[currentPos]
//
//            for (i in 1..steps) {
//                delay(300)
//                currentPos = (currentPos + 1) % boardPath.size
//                _tokenIndex.value = boardPath[currentPos]
//                soundManager.TokenSound(0.3f)
//                _isFinalStop.value = (i == steps)
//            }
//
//            // ✅ multiplier logic
//
//            val multiplier = multipliers[_tokenIndex.value] ?: 1.0
//            when {
//                multiplier == 0.0 -> _betAmount.value = 0.0
//                multiplier < 0.0 -> _betAmount.value /= 2
//                else -> _betAmount.value *= multiplier
//            }
//
//        }
//    }
private fun moveToken(steps: Int, soundManager: SoundManager) {

    viewModelScope.launch {

        // 🎯 Hamesha start card se shuru karo
        var currentPos = 0
        _tokenIndex.value = boardPath[currentPos]

        for (i in 1..steps) {
            delay(300) // token animation speed
            currentPos = (currentPos + 1) % boardPath.size
            _tokenIndex.value = boardPath[currentPos]
            soundManager.TokenSound(0.3f)

            // ✅ final stop
            _isFinalStop.value = (i == steps)

            // 🔥 Balance update aur roll enable sirf final step pe
//            if (i == steps) {
//                _betAmount.value = _pendingBalance   // API ya pending balance assign
//                _isRollEnabled.value = true
//            }

            // inside moveToken, after token finishes moving
            if (i == steps) {
                val baseAmount = _pendingBalance// API returned balance or bet
                val multiplier = multipliers[_tokenIndex.value] ?: 1.0
                _betAmount.value = if (multiplier == 0.0) 0.0 else baseAmount * multiplier
                _isRollEnabled.value = true
            }

        }

        // multiplier logic agar tumhare UI me hai
        val multiplier = multipliers[_tokenIndex.value] ?: 1.0
        when {
            multiplier == 0.0 -> _betAmount.value = 0.0
            multiplier < 0.0 -> _betAmount.value /= 2
            else -> _betAmount.value *= multiplier
        }
    }
}


}
