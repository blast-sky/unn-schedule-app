package com.astrog.sheduleapp.internal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.astrog.sheduleapp.domain.model.ScheduleType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SchedulePreferences @Inject constructor(
    @ApplicationContext private val application: Context,
) {

    private val preferences = application.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

    var activeType: ScheduleType
        get() = ScheduleType.valueOf(preferences.getString(ACTIVE_TYPE, ScheduleType.STUDENT.toString())!!)
        set(value) = editPreferences { putString(ACTIVE_TYPE, value.name) }

    var activeId: Long?
        get() {
            val savedId = preferences.getLong(ACTIVE_ID, -1L)
            return if (savedId == -1L) null else savedId
        }
        set(value) = editPreferences { putLong(ACTIVE_ID, value ?: -1L) }

    var lastTerm: String
        get() = preferences.getString(LAST_TERM, String())!!
        set(value) = editPreferences { putString(LAST_TERM, value) }

    private fun editPreferences(block: SharedPreferences.Editor.() -> Unit) {
        preferences.edit().apply(block).apply()
    }

    private companion object {
        const val PREFERENCES_NAME = "SCHEDULE_PREFERENCES"
        const val ACTIVE_TYPE = "ACTIVE_TYPE_V3"
        const val ACTIVE_ID = "ACTIVE_ID"
        const val LAST_TERM = "LAST_TERM"
    }
}