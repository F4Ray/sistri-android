package coid.bcafinance.sistriandroid.endpoint

import coid.bcafinance.sistriandroid.model.TestDriveResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface TestDriveAPI {
    @GET("api/consumer/all/0/desc/createdAt?filter-by=createdDate&size=10&value=")
    suspend fun getTestDriveList(@Header("Authorization") authToken: String): TestDriveResponse
}