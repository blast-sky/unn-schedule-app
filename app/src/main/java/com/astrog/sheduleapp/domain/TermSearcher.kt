package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.domain.model.SearchResult
import javax.inject.Inject

class TermSearcher @Inject constructor(
    private val searchService: SearchService,
) {

    suspend fun searchTerm(type: ScheduleType, term: String): List<SearchResult> =
        searchService.searchTypeByTerm(type, term)
}
