package coid.bcafinance.sistriandroid.config

import coid.bcafinance.sistriandroid.endpoint.AuthAPI
import coid.bcafinance.sistriandroid.endpoint.TestDriveAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authAPI: AuthAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }

}