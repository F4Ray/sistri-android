package coid.bcafinance.sistriandroid.endpoint

import coid.bcafinance.sistriandroid.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("api/auth/login")
    suspend fun login(@Body credentials: HashMap<String, String>): LoginResponse
}

//api/consumer/all/0/desc/createdAt?filter-by=createdDate&size=10&value=