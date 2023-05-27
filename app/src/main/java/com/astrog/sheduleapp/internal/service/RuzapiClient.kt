package com.astrog.sheduleapp.internal.service

import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.internal.dto.LessonDto
import com.astrog.sheduleapp.domain.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate


interface RuzapiClient {

    @POST("schedule/{obj}/{id}")
    suspend fun getSchedule(
        @Path("obj") type: ScheduleType,
        @Path("id") id: Long,
        @Query("start") start: LocalDate,
        @Query("finish") finish: LocalDate,
        @Query("lng") lng: Int = 1,
    ): List<LessonDto>

    @GET("search")
    suspend fun searchByTerm(
        @Query("term") term: String,
        @Query("type") type: ScheduleType,
    ): List<SearchResult>

    companion object {
        const val BASE_URL = "https://portal.unn.ru/ruzapi/"
    }
}