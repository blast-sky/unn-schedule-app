package com.astrog.sheduleapp.presentation.settingsDialog

import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.domain.model.SearchResult

data class SettingsDialogState(
    val activeType: ScheduleType,
    val activeId: String,
    val activeTerm: String,
    val suggestedResults: List<SearchResult>,
    val networkError: Boolean = false,
)
