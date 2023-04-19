package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.SearchResultDto
import javax.inject.Inject

class TermSearcher @Inject constructor(
    private val searchRepository: SearchRepository,
) {

    suspend fun searchTerm(type: String, term: String): List<SearchResultDto> =
        searchRepository.searchTypeByTerm(type, term)
}
