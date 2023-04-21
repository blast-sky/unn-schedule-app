package com.astrog.sheduleapp.presentation.settingsdialog

import com.astrog.sheduleapp.domain.model.SearchResultDto

data class SettingsDialogState(
    val activeType: String,
    val activeId: Long,
    val activeTerm: String,
    val suggestedResults: List<SearchResultDto>,
    val networkError: Boolean = false,
)
