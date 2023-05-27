package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.domain.model.SearchResult

interface SearchService {

    suspend fun searchTypeByTerm(type: ScheduleType, term: String): List<SearchResult>
}