package com.astrog.sheduleapp.presentation.settingsdialog

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrog.sheduleapp.domain.TermSearcher
import com.astrog.sheduleapp.internal.SchedulePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

private val TAG = SettingsDialogViewModel::class.simpleName

@HiltViewModel
class SettingsDialogViewModel @Inject constructor(
    private val termSearcher: TermSearcher,
    private val schedulePreferences: SchedulePreferences,
) : ViewModel() {

    private val lastSearchJob: Job? = null

    val state = mutableStateOf(
        initializeStateByPreferences()
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, ex ->
        Log.e(TAG, ex.stackTraceToString())
        with(state) { value = value.copy(networkError = true) }
    }

    fun setActiveState(type: String, id: Long, term: String) {
        with(schedulePreferences) {
            activeId = id
            activeType = type
            lastTerm = term
        }
        state.value = initializeStateByPreferences()
    }

    fun searchTerm(type: String, term: String) {
        lastSearchJob?.cancel()
        viewModelScope.launch(exceptionHandler) {
            state.value = state.value.copy(
                suggestedResults = termSearcher.searchTerm(type, term),
                networkError = false,
            )
        }
    }

    private fun initializeStateByPreferences(): SettingsDialogState {
        return SettingsDialogState(
            activeType = schedulePreferences.activeType,
            activeId = schedulePreferences.activeId,
            activeTerm = schedulePreferences.lastTerm,
            suggestedResults = emptyList(),
        )
    }
}