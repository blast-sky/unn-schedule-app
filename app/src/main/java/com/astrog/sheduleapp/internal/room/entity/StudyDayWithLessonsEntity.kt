package com.astrog.sheduleapp.internal.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class StudyDayWithLessonsEntity(
    @Embedded
    val studyDayEntity: StudyDayEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "cached_day_id",
    )
    val lessons: List<LessonEntity>,
)
