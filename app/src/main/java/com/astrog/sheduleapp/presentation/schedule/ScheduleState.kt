package com.astrog.sheduleapp.presentation.schedule

import com.astrog.sheduleapp.domain.model.SubjectDto

sealed class ScheduleState {

    object Loading : ScheduleState()

    data class Ready(
        val subjects: List<SubjectDto>,
    ) : ScheduleState()

    data class Error(
        val error: String,
    ) : ScheduleState()
}
