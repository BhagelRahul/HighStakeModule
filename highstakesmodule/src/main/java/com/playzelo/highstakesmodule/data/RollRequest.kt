package com.playzelo.highstakesmodule.data



import retrofit2.http.Body
import retrofit2.http.POST



data class RollResponse(
    val success: Boolean,
    val dice: List<Int>,
    val outcome: String,
    val winnings: Int,
    val multiplier: Double?,
    val emoji: String,
    val message: String,
    val walletBalance: Long
)

data class RollRequest(
    val userId: String,
    val betAmount: Double
)

interface DiceApi {
    @POST("api/stakedice/roll")
    suspend fun rollDice(@Body request: RollRequest): RollResponse
}
