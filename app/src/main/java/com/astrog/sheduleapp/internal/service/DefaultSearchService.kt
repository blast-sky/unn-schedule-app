package com.astrog.sheduleapp.internal.service

import com.astrog.sheduleapp.domain.SearchService
import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.domain.model.SearchResult
import javax.inject.Inject

class DefaultSearchService @Inject constructor(
    private val client: RuzapiClient,
) : SearchService {

    override suspend fun searchTypeByTerm(type: ScheduleType, term: String): List<SearchResult> =
        client.searchByTerm(term, type)
}