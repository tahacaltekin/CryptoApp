package com.dttcaltekin.cryptoapp.ui.home

import com.dttcaltekin.cryptoapp.base.BaseRepository
import com.dttcaltekin.cryptoapp.network.ApiFactory
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiFactory: ApiFactory) : BaseRepository() {

    suspend fun getData(
        apiKey: String,
        limit: String
    ) = safeApiRequest {
        apiFactory.getData(apiKey, limit)
    }
}