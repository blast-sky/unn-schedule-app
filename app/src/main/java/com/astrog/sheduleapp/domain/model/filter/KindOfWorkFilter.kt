package com.astrog.sheduleapp.domain.model.filter

import com.astrog.sheduleapp.domain.model.lesson.KindOfWork
import com.astrog.sheduleapp.domain.model.lesson.Lesson

data class KindOfWorkFilter(
    private val filteredKinds: Set<KindOfWork>,
) : LessonFilter {

    override fun isSatisfy(lesson: Lesson): Boolean {
        return filteredKinds.any { kindOfWork -> lesson.getKindOfWorkType() == kindOfWork }
    }
}
