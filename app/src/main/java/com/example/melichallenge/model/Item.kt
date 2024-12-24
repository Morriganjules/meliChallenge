package com.example.melichallenge.model

import com.google.gson.annotations.SerializedName

data class Item(
    val id: String? = null,
    val title: String? = null,
    val price: Float? = null,
    val thumbnail: String? = null,
    val permalink: String? = null,
    val seller: Seller? = null,
    val shipping: Shipping? = null,
    @SerializedName("currency_id")
    val currencyId: String? = null
)
