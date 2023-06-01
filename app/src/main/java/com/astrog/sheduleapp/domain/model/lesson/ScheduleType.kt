package com.astrog.sheduleapp.domain.model.lesson

import com.google.gson.annotations.SerializedName

enum class ScheduleType {
    @SerializedName("student")
    STUDENT,

    @SerializedName("group")
    GROUP,

    @SerializedName("lecturer")
    LECTURER,

    @SerializedName("auditorium")
    AUDITORIUM,
}