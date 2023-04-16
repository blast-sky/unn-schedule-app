package com.astrog.sheduleapp.presentation.schedule

import com.astrog.sheduleapp.internal.schedule.SubjectDto

sealed class ScheduleState {

    object Loading : ScheduleState()

    data class Ready(
        val subjects: List<SubjectDto>,
    ) : ScheduleState()

    data class Error(
        val error: String,
    ) : ScheduleState()
}
