package com.example.melichallenge.api.service

import com.example.melichallenge.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MercadoLibreApi {
    @GET("search")
    suspend fun searchItems(@Query("q") query: String): Response<SearchResponse>

    @GET("search")
    suspend fun SearchSelectedItem(@QueryMap queryMap: Map<String, String>)
}