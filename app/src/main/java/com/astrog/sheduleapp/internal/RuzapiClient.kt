package com.astrog.sheduleapp.internal

import com.astrog.sheduleapp.internal.schedule.SubjectDto
import com.astrog.sheduleapp.internal.search.SearchResultDto
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

    @GET("search?term=%D0%A1%D1%82%D1%80%D0%BE%D0%B3%D0%B0&type=student")
    suspend fun searchByTerm(
        @Query("term") term: String,
        @Query("type") type: String,
    ): List<SearchResultDto>
}