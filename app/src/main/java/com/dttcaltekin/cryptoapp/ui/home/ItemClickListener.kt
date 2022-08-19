package com.dttcaltekin.cryptoapp.ui.home

import com.dttcaltekin.cryptoapp.model.homeResponse.Data

interface ItemClickListener {
    fun onItemClick(coin: Data)
}