package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import com.astrog.sheduleapp.domain.model.lesson.SearchResult

interface SearchService {

    suspend fun searchTypeByTerm(type: ScheduleType, term: String): List<SearchResult>
}