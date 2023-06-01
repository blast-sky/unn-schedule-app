package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import com.astrog.sheduleapp.domain.model.lesson.SearchResult
import javax.inject.Inject

class TermSearcher @Inject constructor(
    private val searchService: SearchService,
) {

    suspend fun searchTerm(type: ScheduleType, term: String): List<SearchResult> {
        return if (term.isBlank())
            emptyList()
        else
            searchService.searchTypeByTerm(type, term)
    }
}
