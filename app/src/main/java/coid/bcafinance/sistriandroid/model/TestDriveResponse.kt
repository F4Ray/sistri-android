package coid.bcafinance.sistriandroid.model

import com.google.gson.annotations.SerializedName

data class TestDriveResponse(
    val `data`: TestDriveData,
    val success: Boolean,
    val message: String,
    val status: Int,
    val timestamp: Long
)

data class TestDriveData(
    val totalItems: Int,
    val numberOfElements: Int,
    val totalPages: Int,
    @field:SerializedName("filter-by") val filterBy: String,
    val sort: String,
    @field:SerializedName("component-filter") val componentFilter: List<Any>,
    val value: String,
    val content: List<TestDriveContent>
)

data class TestDriveContent(
    @field:SerializedName("testDriveID") val testDriveID: Int,
    @field:SerializedName("antrianID") val antrianID: String,
    @field:SerializedName("consumerName") val consumerName: String,
    val statusID: Int,
    @field:SerializedName("carID") val carID: CarID
)

data class CarID(
    val carID: Int,
    val merkID: MerkID,
    val tipe: String,
    val createdBy: Int,
    val createdAt: Long,
    val updatedBy: Any?,
    val updatedAt: Long
)

data class MerkID(
    val merkID: Int,
    val merk: String,
    val createdBy: Int,
    val createdAt: Long,
    val updatedBy: Any?,
    val updatedAt: Long,
    @field:SerializedName("dealerCode") val dealerCode: String,
    @field:SerializedName("kode_dealer") val kodeDealerString: String
)