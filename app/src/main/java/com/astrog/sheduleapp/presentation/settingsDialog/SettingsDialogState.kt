package com.astrog.sheduleapp.presentation.settingsDialog

import com.astrog.sheduleapp.domain.model.lesson.KindOfWork
import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import com.astrog.sheduleapp.domain.model.lesson.SearchResult

data class SettingsDialogState(
    val activeType: ScheduleType,
    val activeId: String,
    val activeTerm: String,
    val suggestedResults: List<SearchResult>,
    val networkError: Boolean = false,
    val selectedKindOfWork: Set<KindOfWork>,
)
