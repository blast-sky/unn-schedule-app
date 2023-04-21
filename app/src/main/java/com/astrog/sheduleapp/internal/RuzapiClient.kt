package com.astrog.sheduleapp.internal

import com.astrog.sheduleapp.domain.model.SubjectDto
import com.astrog.sheduleapp.domain.model.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface RuzapiClient {

    @POST("schedule/{obj}/{id}")
    suspend fun getSchedule(
        @Path("obj") obj: String,
        @Path("id") id: Long,
        @Query("start") start: String,
        @Query("finish") finish: String,
        @Query("lng") lng: Int = 1,
    ): List<SubjectDto>

    @GET("search")
    suspend fun searchByTerm(
        @Query("term") term: String,
        @Query("type") type: String,
    ): List<SearchResultDto>
}