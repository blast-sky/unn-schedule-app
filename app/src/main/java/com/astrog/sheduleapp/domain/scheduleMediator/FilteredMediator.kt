package com.astrog.sheduleapp.domain.scheduleMediator

import com.astrog.sheduleapp.domain.model.filter.LessonFilter
import com.astrog.sheduleapp.domain.model.lesson.Lesson
import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import io.github.oshai.KotlinLogging
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

private val logger = KotlinLogging.logger {}

@Singleton
class FilteredMediator @Inject constructor(
    private val serviceRepoMediator: ServiceRepoMediator,
) {

    suspend fun getLessons(
        date: LocalDate,
        objectId: Long,
        objectType: ScheduleType,
        filter: LessonFilter,
    ): List<Lesson> {
        return serviceRepoMediator
            .getLessons(date, objectId, objectType)
            .onEach { logger.debug { it.toString() } }
            .filter(filter::isSatisfy)
    }
}