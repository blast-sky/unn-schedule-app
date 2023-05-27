package com.astrog.sheduleapp.internal.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.astrog.sheduleapp.domain.model.Lesson
import com.astrog.sheduleapp.domain.model.StudyDay
import com.astrog.sheduleapp.internal.mapper.toLessonEntity
import com.astrog.sheduleapp.internal.room.entity.StudyDayWithLessonsEntity
import com.astrog.sheduleapp.internal.room.entity.LessonEntity
import com.astrog.sheduleapp.internal.room.entity.StudyDayEntity
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface LessonDao {

    @Transaction
    @Query("SELECT * FROM cashed_day WHERE object_id = :objectId")
    suspend fun getCachedDays(objectId: Long): List<StudyDayWithLessonsEntity>

    @Transaction
    @Query("SELECT * FROM cashed_day WHERE object_id = :objectId AND date = :date")
    suspend fun getCachedDay(objectId: Long, date: LocalDate): StudyDayWithLessonsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashedDay(studyDayEntity: StudyDayEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Query("DELETE FROM cashed_day WHERE object_id = :objectId")
    suspend fun deleteCashedDays(objectId: Long)

    @Transaction
    suspend fun saveStudyDay(objectId: Long, date: LocalDate, lessons: List<Lesson>) {
        val dayId = insertCashedDay(
            StudyDayEntity(
                id = null,
                objectId = objectId,
                date = date,
                modifiedDateTime = LocalDateTime.now(),
            )
        )

        insertLessons(lessons.map { it.toLessonEntity(dayId) })
    }

    @Transaction
    suspend fun saveStudyDays(objectId: Long, studyDays: List<StudyDay>) =
        studyDays.forEach { day ->
            saveStudyDay(objectId, day.date, day.lessons)
        }
}