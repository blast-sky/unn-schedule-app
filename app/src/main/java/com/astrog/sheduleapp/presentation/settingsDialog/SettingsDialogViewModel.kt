package com.astrog.sheduleapp.presentation.settingsDialog

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrog.sheduleapp.domain.TermSearcher
import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.internal.SchedulePreferences
import com.astrog.sheduleapp.presentation.settingsDialog.compose.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = SettingsDialogViewModel::class.simpleName

@HiltViewModel
class SettingsDialogViewModel @Inject constructor(
    private val termSearcher: TermSearcher,
    private val schedulePreferences: SchedulePreferences,
) : ViewModel() {

    private val lastSearchJob: Job? = null

    val state = mutableStateOf(
        getStateInitializedByPreferences()
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, ex ->
        Log.e(TAG, ex.stackTraceToString())
        with(state) { value = value.copy(networkError = true) }
    }

    fun setActiveState(type: ScheduleType, id: String, term: String) {
        try {
            with(schedulePreferences) {
                activeId = id.toLong()
                activeType = type
                lastTerm = term
            }
        } catch (ex: NumberFormatException) {
            logger.error { ex.stackTraceToString() }
        }
        state.value = getStateInitializedByPreferences()
    }

    fun searchTerm(type: ScheduleType, term: String) {
        lastSearchJob?.cancel()
        viewModelScope.launch(exceptionHandler) {
            state.value = state.value.copy(
                suggestedResults = termSearcher.searchTerm(type, term),
                networkError = false,
            )
        }
    }

    private fun getStateInitializedByPreferences(): SettingsDialogState {
        return SettingsDialogState(
            activeType = schedulePreferences.activeType,
            activeId = schedulePreferences.activeId?.toString() ?: String(),
            activeTerm = schedulePreferences.lastTerm,
            suggestedResults = emptyList(),
        )
    }
}