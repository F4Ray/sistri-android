package coid.bcafinance.sistriandroid.endpoint

import coid.bcafinance.sistriandroid.model.QRCodeResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface QRCodeAPI {
    @GET("api/consumer/getqr/{id}")
    suspend fun getQRCode(@Header("Authorization") authToken: String,@Path("id") id: Int): QRCodeResponse
}