package com.example.melichallenge.states

import com.example.melichallenge.model.SearchResponse

sealed class UiStates {
    object Loading: UiStates()

    object ServerError: UiStates()

    data class Success(
        val response: SearchResponse
    ): UiStates()
}