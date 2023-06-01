@file:OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)

package com.astrog.sheduleapp.presentation.settingsDialog.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.astrog.sheduleapp.domain.model.lesson.KindOfWork

@Composable
fun LessonFilterChips(
    changeFilterStatus: (KindOfWork) -> Unit,
    selectedKindsOfWork: Set<KindOfWork>,
) {
    FlowRow(
        maxItemsInEachRow = 4,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth(),
    ) {
        for (kindOfWork in KindOfWork.values()) {
            FilterChip(
                selected = kindOfWork in selectedKindsOfWork,
                onClick = { changeFilterStatus.invoke(kindOfWork) },
            ) {
                Text(text = kindOfWork.name)
            }
        }
    }
}