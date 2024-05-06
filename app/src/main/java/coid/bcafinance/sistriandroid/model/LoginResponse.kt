package coid.bcafinance.sistriandroid.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val data: Data?,
    val success: Boolean,
    val message: String,
    val status: Int,
    val timestamp: Long
)

data class Data(
    @SerializedName("last_name") val lastName: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("email") val email: String,
    val token: String
)
