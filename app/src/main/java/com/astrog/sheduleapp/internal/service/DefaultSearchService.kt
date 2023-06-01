package com.astrog.sheduleapp.internal.service

import com.astrog.sheduleapp.domain.SearchService
import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import com.astrog.sheduleapp.domain.model.lesson.SearchResult
import javax.inject.Inject

class DefaultSearchService @Inject constructor(
    private val client: RuzapiClient,
) : SearchService {

    override suspend fun searchTypeByTerm(type: ScheduleType, term: String): List<SearchResult> =
        client.searchByTerm(term, type)
}