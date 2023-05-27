package com.astrog.sheduleapp.domain.model

sealed class Cache<T>(private val data: T) {

    data class Obsolete<T>(val data: T) : Cache<T>(data)

    data class Fresh<T>(val data: T) : Cache<T>(data)
}
