package com.astrog.sheduleapp.internal.room

import com.astrog.sheduleapp.domain.ScheduleRepository
import com.astrog.sheduleapp.domain.model.Cache
import com.astrog.sheduleapp.domain.model.Lesson
import com.astrog.sheduleapp.domain.model.StudyDay
import com.astrog.sheduleapp.internal.room.entity.StudyDayWithLessonsEntity
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class RoomScheduleRepository @Inject constructor(
    private val lessonDao: LessonDao,
) : ScheduleRepository {

    override suspend fun getCachedDay(
        objectId: Long,
        date: LocalDate,
    ): Cache<StudyDayWithLessonsEntity>? = lessonDao
        .getCachedDay(objectId, date)
        ?.let { cache ->
            if (cache.isObsolete) {
                Cache.Obsolete(cache)
            } else {
                Cache.Fresh(cache)
            }
        }

    override suspend fun deleteAllCachedDays(objectId: Long) = lessonDao
        .deleteCashedDays(objectId)

    override suspend fun saveStudyDays(objectId: Long, studyDays: List<StudyDay>) = lessonDao
        .saveStudyDays(objectId, studyDays)

    private val StudyDayWithLessonsEntity.isObsolete: Boolean
        get() {
            val duration = Duration.between(LocalDateTime.now(), studyDayEntity.modifiedDateTime)
            return duration > cashObsoleteDuration
        }

    companion object {
        val cashObsoleteDuration: Duration = Duration.ofHours(2)
    }
}