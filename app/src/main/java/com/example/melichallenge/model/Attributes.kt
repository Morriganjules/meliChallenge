package com.example.melichallenge.model

import com.google.gson.annotations.SerializedName

data class Attributes(
    val name: String? = null,
    @SerializedName("value_name")
    val valueName: String? = null
)
