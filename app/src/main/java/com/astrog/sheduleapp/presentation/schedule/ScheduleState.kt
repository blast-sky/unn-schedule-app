package com.astrog.sheduleapp.presentation.schedule

import com.astrog.sheduleapp.presentation.schedule.model.SubjectPresentation

sealed class ScheduleState {

    data class Ready(
        val subjects: List<SubjectPresentation>,
    ) : ScheduleState()

    object Loading : ScheduleState()

    sealed class Error : ScheduleState() {

        object LoadError : Error()

        object NullIdError: Error()
    }
}
