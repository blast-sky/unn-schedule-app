package com.astrog.sheduleapp.internal.room.converter

import androidx.room.TypeConverter
import com.astrog.sheduleapp.domain.model.KindOfWork
import com.astrog.sheduleapp.internal.service.typeAdapter.KindOfWorkSerializer

class KindOfWorkConverter {

    @TypeConverter
    fun kindOfWorkToString(kindOfWork: KindOfWork): String {
        return kindOfWork.type
    }

    @TypeConverter
    fun stringToKindOfString(string: String): KindOfWork {
        return KindOfWorkSerializer.stringToKindOfWork(string)
    }
}