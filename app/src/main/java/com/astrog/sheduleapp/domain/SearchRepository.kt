package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.internal.search.SearchResultDto

interface SearchRepository {

    suspend fun searchTypeByTerm(type: String, term: String): List<SearchResultDto>

    suspend fun searchStudentByName(name: String): List<SearchResultDto>

    suspend fun searchGroupByNumber(number: String): List<SearchResultDto>

    suspend fun searchLecturerByName(name: String): List<SearchResultDto>

    suspend fun searchAuditoriumByNumber(number: String): List<SearchResultDto>
}