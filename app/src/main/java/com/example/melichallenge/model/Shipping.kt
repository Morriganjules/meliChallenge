package com.example.melichallenge.model

import com.google.gson.annotations.SerializedName

data class Shipping(
    @SerializedName("free_shipping")
    val freeShipping: Boolean? = null
)