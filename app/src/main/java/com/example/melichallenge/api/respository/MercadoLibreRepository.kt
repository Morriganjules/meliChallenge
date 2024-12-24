package com.example.melichallenge.api.respository

import android.util.Log
import com.example.melichallenge.api.Result
import com.example.melichallenge.api.service.MercadoLibreApi
import com.example.melichallenge.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MercadoLibreRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.mercadolibre.com/sites/MLA/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(MercadoLibreApi::class.java)

    suspend fun getSearchedItem(searchInput: String): Flow<Result<SearchResponse>> {
        return flow {
            Log.d("MercadoLibreRepository", "Haciendo la llamada a Retrofit")
            emit(service.searchItems(searchInput))
            Log.d("MercadoLibreRepository", "Después del emit")
        }.map {
            mapResponse(it)
        }
    }

    private fun mapResponse(response: Response<SearchResponse>): Result<SearchResponse>{
        Log.d("response", response.isSuccessful.toString())
        return if(response.isSuccessful){
            Result.success(response.body())
        } else {
            Result.serverError(null)
        }
    }
}