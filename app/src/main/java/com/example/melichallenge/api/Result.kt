package com.example.melichallenge.api


data class Result<out T>(val status: Status, val data: T?) {
    companion object{
        fun<T> success(data: T?): Result<T> = Result(Status.Success, data)
        fun<T> serverError(data: T?): Result<T> = Result(Status.ServerError, data)
    }
}