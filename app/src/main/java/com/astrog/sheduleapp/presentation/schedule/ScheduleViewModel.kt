package com.astrog.sheduleapp.presentation.schedule

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrog.sheduleapp.domain.model.filter.KindOfWorkFilter
import com.astrog.sheduleapp.domain.model.lesson.isActive
import com.astrog.sheduleapp.domain.scheduleMediator.FilteredMediator
import com.astrog.sheduleapp.internal.SchedulePreferences
import com.astrog.sheduleapp.presentation.schedule.model.Page
import com.astrog.sheduleapp.presentation.schedule.model.SubjectPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.oshai.KotlinLogging
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ScheduleStateMap = Map<Page, ScheduleState>

private val logger = KotlinLogging.logger { }

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val filteredMediator: FilteredMediator,
    private val schedulePreferences: SchedulePreferences,
) : ViewModel() {

    private val _state: MutableState<ScheduleStateMap> = mutableStateOf(mapOf())

    private var scheduleStateMap by _state
    val state: State<ScheduleStateMap> = _state

    private val batchSize = 10

    private val exceptionHandler = CoroutineExceptionHandler { _, ex ->
        logger.error { ex.stackTraceToString() }
        replaceLoadingOnErrorState()
    }

    private fun replaceLoadingOnErrorState() {
        val errorStates = scheduleStateMap
            .filterValues { state -> state is ScheduleState.Loading }
            .keys
            .map { page -> page to ScheduleState.Error.LoadError }
        scheduleStateMap = scheduleStateMap + errorStates
    }

    init {
        clearPagesAndLoadInitial()
    }

    fun requestScheduleRange(centerPage: Page) {
        for (offset in (-batchSize / 2)..(batchSize / 2)) {
            val currentPage = centerPage + offset
            requestSchedule(currentPage)
        }
    }

    private fun requestSchedule(page: Page) {
        if (scheduleStateMap.containsKey(page))
            return

        viewModelScope.launch(exceptionHandler) {
            scheduleStateMap = scheduleStateMap + (page to ScheduleState.Loading)

            val objectId = schedulePreferences.activeId
            val activeKindsOfWork = schedulePreferences.activeKindsOfWork

            val lessons = if (objectId != null)
                filteredMediator.getLessons(
                    page.date,
                    objectId,
                    schedulePreferences.activeType,
                    KindOfWorkFilter(activeKindsOfWork),
                )
            else
                emptyList()

            val subjectPresentations = lessons.map { subject ->
                SubjectPresentation(subject, subject.isActive)
            }
            scheduleStateMap = scheduleStateMap +
                (page to ScheduleState.Ready(subjectPresentations))
        }
    }

    fun clearPagesAndLoadInitial() {
        scheduleStateMap = mutableMapOf()
        requestScheduleRange(Page.Initial)
    }

    val isIdAbsent: Boolean
        get() = schedulePreferences.activeId == null
}