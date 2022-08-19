package com.dttcaltekin.cryptoapp.network

import com.dttcaltekin.cryptoapp.model.detailResponse.DetailResponse
import com.dttcaltekin.cryptoapp.model.homeResponse.CryptoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiFactory {

    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getData(
        @Header("X-CMC_PRO_API_KEY") apiKey : String,
        @Query("limit") limit : String
    ) : CryptoResponse

    @GET("v2/cryptocurrency/info")
    suspend fun getDetail(
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
        @Query("symbol") symbol: String
    ) : DetailResponse
}