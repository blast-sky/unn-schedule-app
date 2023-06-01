package com.astrog.sheduleapp.internal.room.converter

import androidx.room.TypeConverter
import com.astrog.sheduleapp.domain.model.lesson.KindOfWork

class KindOfWorkConverter {

    @TypeConverter
    fun intToKindOfWork(value: Int) = enumValues<KindOfWork>()[value]

    @TypeConverter
    fun kindOfWorkToInt(value: KindOfWork) = value.ordinal
}