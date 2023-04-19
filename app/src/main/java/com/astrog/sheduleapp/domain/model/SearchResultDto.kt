package com.astrog.sheduleapp.domain.model

data class SearchResultDto(
    val id: Long,
    val label: String,
    val description: String,
    val type: String,
)