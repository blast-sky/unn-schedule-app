package com.astrog.sheduleapp.internal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.astrog.sheduleapp.util.STUDENT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SchedulePreferences @Inject constructor(
    @ApplicationContext private val application: Context,
) {

    private val preferences = application.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

    var activeType: String
        get() = preferences.getString(ACTIVE_TYPE, STUDENT)!!
        set(value) = editPreferences { putString(ACTIVE_TYPE, value) }

    var activeId: Long
        get() = preferences.getLong(ACTIVE_ID, -1L)
        set(value) = editPreferences { putLong(ACTIVE_ID, value) }

    var lastTerm: String
        get() = preferences.getString(LAST_TERM, String())!!
        set(value) = editPreferences { putString(LAST_TERM, value) }

    private fun editPreferences(block: SharedPreferences.Editor.() -> Unit) {
        preferences.edit().apply(block).apply()
    }

    private companion object {
        const val PREFERENCES_NAME = "SCHEDULE_PREFERENCES"
        const val ACTIVE_TYPE = "ACTIVE_TYPE"
        const val ACTIVE_ID = "ACTIVE_ID"
        const val LAST_TERM = "LAST_TERM"
    }
}