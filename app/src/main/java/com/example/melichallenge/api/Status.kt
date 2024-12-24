package com.example.melichallenge.api

sealed class Status {
    object Success : Status()
    object ServerError : Status()
}
