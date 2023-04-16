package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.util.AUDITORIUM
import com.astrog.sheduleapp.util.GROUP
import com.astrog.sheduleapp.util.LECTURER
import com.astrog.sheduleapp.util.STUDENT

sealed class ScheduleType(val value: String) {

    object Student : ScheduleType(STUDENT)

    object Group : ScheduleType(GROUP)

    object Lecturer : ScheduleType(LECTURER)

    object Auditorium : ScheduleType(AUDITORIUM)
}
