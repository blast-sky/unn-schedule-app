package com.astrog.sheduleapp.presentation.schedule

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrog.sheduleapp.internal.ScheduleLoader
import com.astrog.sheduleapp.internal.SchedulePreferences
import com.astrog.sheduleapp.presentation.schedule.model.SubjectPresentation
import com.astrog.sheduleapp.util.dateFormatterWithDayOfWeek
import com.astrog.sheduleapp.util.initialPage
import com.astrog.sheduleapp.util.isSubjectActive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
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

    private val exceptionHandler = CoroutineExceptionHandler { _, ex ->
        Log.e(TAG, ex.stackTraceToString())
        val statusMap = state.value
        statusMap.forEach { (page, currentState) ->
            if (currentState is ScheduleState.Loading) {
                state.value += page to ScheduleState.Error("Не удалось загрузить данные.")
            }
        }
    }

    init {
        clearPagesAndLoadInitial()
    }

    private fun requestSchedule(date: LocalDate, page: Int) =
        viewModelScope.launch(exceptionHandler) {
            val subjects = scheduleLoader.loadSchedule(date)
            val subjectPresentations = subjects.map { subject ->
                SubjectPresentation(subject, isSubjectActive(subject))
            }
            state.value += page to ScheduleState.Ready(subjectPresentations)
        }

    fun pageToDateString(page: Int): String {
        val pageDate = pageToDate(page)
        return dateFormatterWithDayOfWeek.format(pageDate).replaceFirstChar(Char::titlecaseChar)
    }

    fun requestSchedule(page: Int) {
        val neededDate = pageToDate(page)
        val pageStatuses = state.value
        if (pageStatuses.containsKey(page)) {
            return
        }
        state.value = pageStatuses.plus(page to ScheduleState.Loading)
        requestSchedule(neededDate, page)
    }

    private fun pageToDate(page: Int): LocalDate {
        val diffInDays = page - initialPage
        return LocalDate.now().plusDays(diffInDays.toLong())
    }

    fun clearPagesAndLoadInitial() {
        state.value = mutableMapOf()
        requestSchedule(initialPage)
    }

    fun dateToPage(date: LocalDate): Int {
        val now = LocalDate.now()
        return initialPage + Period.between(now, date).days
    }

    val isIdAbsent: Boolean
        get() = schedulePreferences.activeId == -1L
}