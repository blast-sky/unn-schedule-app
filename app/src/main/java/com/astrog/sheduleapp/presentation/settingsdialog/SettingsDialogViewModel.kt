package com.astrog.sheduleapp.presentation.settingsdialog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrog.sheduleapp.domain.TermSearcher
import com.astrog.sheduleapp.internal.SchedulePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsDialogViewModel @Inject constructor(
    private val termSearcher: TermSearcher,
    private val schedulePreferences: SchedulePreferences,
) : ViewModel() {

    private val lastSearchJob: Job? = null

    val state = mutableStateOf(
        initializeStateByPreferences()
    )

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
        viewModelScope.launch {
            state.value = state.value.copy(
                suggestedResults = termSearcher.searchTerm(type, term)
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