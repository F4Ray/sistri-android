package coid.bcafinance.sistriandroid.model

data class QRCodeResponse(
    val data: String,
    val success: Boolean,
    val message: String,
    val status: Int,
    val timestamp: Long
)
