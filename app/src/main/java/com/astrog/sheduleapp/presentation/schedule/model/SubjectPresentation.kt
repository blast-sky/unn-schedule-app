package com.astrog.sheduleapp.presentation.schedule.model

import com.astrog.sheduleapp.domain.model.SubjectDto

data class SubjectPresentation(
    val subjectDto: SubjectDto,
    val isActive: Boolean,
)