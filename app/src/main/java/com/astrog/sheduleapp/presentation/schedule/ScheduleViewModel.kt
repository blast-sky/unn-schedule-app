package com.astrog.sheduleapp.presentation.schedule

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrog.sheduleapp.domain.ScheduleLoader
import com.astrog.sheduleapp.internal.SchedulePreferences
import com.astrog.sheduleapp.util.AUDITORIUM
import com.astrog.sheduleapp.util.GROUP
import com.astrog.sheduleapp.util.LECTURER
import com.astrog.sheduleapp.util.STUDENT
import com.astrog.sheduleapp.util.dateFormatterWithDayOfWeek
import com.astrog.sheduleapp.util.initPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

typealias ScheduleStateMap = Map<Int, ScheduleState>

private val TAG = ScheduleViewModel::class.simpleName

@SuppressLint("NewApi")
@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleLoader: ScheduleLoader,
    private val schedulePreferences: SchedulePreferences,
) : ViewModel() {

    val state: MutableState<ScheduleStateMap> = mutableStateOf(mapOf())

    init {
        removeAllAndLoadInitPage()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, ex ->
        Log.e(TAG, ex.stackTraceToString())
        val statusMap = state.value
        statusMap.forEach { (page, currentState) ->
            if (currentState is ScheduleState.Loading) {
                state.value += page to ScheduleState.Error("Не удалось загрузить данные.")
            }
        }
    }

    private fun loadSchedule(date: LocalDate, page: Int) = viewModelScope.launch {
        try {
            val subjects = scheduleLoader.loadSchedule(date)
            Log.i(TAG, "page $page is just loaded")
            state.value += page to ScheduleState.Ready(subjects)
        } catch (ex: RuntimeException) {
            println(ex.stackTraceToString())
            Log.i(TAG, "page $page is failed to load")
            state.value += page to ScheduleState.Error("Не удалось загрузить данные.")
        }
    }

    fun pageToDateString(page: Int): String {
        val now = LocalDate.now()
        val pageDiff = page - initPage
        val pageDate = now.plusDays(pageDiff.toLong())
        return dateFormatterWithDayOfWeek.format(pageDate).replaceFirstChar(Char::titlecaseChar)
    }

    fun loadSchedule(page: Int) {
        val diffInDays = page - initPage
        val neededDate = LocalDate.now().plusDays(diffInDays.toLong())
        val pageStatuses = state.value
        Log.i(TAG, "loading $page page")
        if (pageStatuses.containsKey(page)) {
            Log.i(TAG, "page $page is already loaded")
            return
        }
        state.value = pageStatuses.plus(page to ScheduleState.Loading)
        loadSchedule(neededDate, page)
    }

    fun removeAllAndLoadInitPage() {
        state.value = mutableMapOf()
        loadSchedule(initPage)
    }

    val isIdAbsent: Boolean
        get() = schedulePreferences.activeId == -1L
}