package com.astrog.sheduleapp.internal.search

import com.astrog.sheduleapp.domain.SearchRepository
import com.astrog.sheduleapp.internal.RuzapiClient
import com.astrog.sheduleapp.util.AUDITORIUM
import com.astrog.sheduleapp.util.GROUP
import com.astrog.sheduleapp.util.LECTURER
import com.astrog.sheduleapp.util.STUDENT
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val client: RuzapiClient,
) : SearchRepository {

    override suspend fun searchTypeByTerm(type: String, term: String): List<SearchResultDto> =
        client.searchByTerm(term, type)

    override suspend fun searchStudentByName(name: String): List<SearchResultDto> =
        client.searchByTerm(name, STUDENT)

    override suspend fun searchGroupByNumber(number: String): List<SearchResultDto> =
        client.searchByTerm(number, GROUP)

    override suspend fun searchLecturerByName(name: String): List<SearchResultDto> =
        client.searchByTerm(name, LECTURER)

    override suspend fun searchAuditoriumByNumber(number: String): List<SearchResultDto> =
        client.searchByTerm(number, AUDITORIUM)
}