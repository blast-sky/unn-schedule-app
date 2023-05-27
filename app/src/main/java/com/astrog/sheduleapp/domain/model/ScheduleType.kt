package com.astrog.sheduleapp.domain.model

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