package com.astrog.sheduleapp.presentation.schedule

import com.astrog.sheduleapp.presentation.schedule.model.SubjectPresentation

sealed class ScheduleState {

    object Loading : ScheduleState()

    data class Ready(
        val subjects: List<SubjectPresentation>,
    ) : ScheduleState()

    data class Error(
        val error: String,
    ) : ScheduleState()
}
