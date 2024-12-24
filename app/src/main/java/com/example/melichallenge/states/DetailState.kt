package com.example.melichallenge.states

import com.example.melichallenge.model.Item

sealed class DetailState {
    object Loading: DetailState()

    object ServerError: DetailState()

    data class Success(
        val response: Item
    ): DetailState()
}
