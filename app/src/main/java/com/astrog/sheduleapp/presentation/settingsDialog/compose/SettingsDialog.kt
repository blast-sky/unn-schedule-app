package com.astrog.sheduleapp.presentation.settingsDialog.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.astrog.sheduleapp.R
import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.presentation.settingsDialog.SettingsDialogViewModel
import com.astrog.sheduleapp.util.defaultPadding
import com.astrog.sheduleapp.util.roundRadius
import io.github.oshai.KotlinLogging

val logger = KotlinLogging.logger { }

@Composable
fun SettingsDialog(
    viewModel: SettingsDialogViewModel = hiltViewModel(),
    updateScheduleContent: () -> Unit,
    onDismiss: () -> Unit,
) {
    val state by viewModel.state
    AnimatedDialog(onDismissRequest = { onDismiss.invoke() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            shape = RoundedCornerShape(roundRadius),
        ) {
            var currentType by rememberSaveable { mutableStateOf(state.activeType) }
            var currentId by rememberSaveable { mutableStateOf(state.activeId) }
            var selectedTerm by rememberSaveable { mutableStateOf(state.activeTerm) }
            Column(
                modifier = Modifier
                    .height(height = 500.dp)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                ScheduleTypeIconButtons(
                    currentType,
                    Modifier.padding(top = defaultPadding),
                ) {
                    currentType = it
                    if (currentType == state.activeType) {
                        currentId = state.activeId
                        selectedTerm = state.activeTerm
                    } else {
                        currentId = String()
                        selectedTerm = String()
                    }
                }
                Spacer(modifier = Modifier.height(defaultPadding + 5.dp))
                SearchTextField(
                    suggestedResults = state.suggestedResults,
                    searchTerm = { viewModel.searchTerm(currentType, it) },
                    setActiveId = { newId -> currentId = newId.toString() },
                    selectedItem = selectedTerm,
                    onSelectedItemChange = { selectedTerm = it },
                )
                if (state.networkError) {
                    NetworkErrorLabel()
                }
                Spacer(modifier = Modifier.weight(1f))
                BottomButtonsRow(
                    onDismiss,
                    viewModel,
                    currentType,
                    currentId,
                    selectedTerm,
                    updateScheduleContent
                )
            }
        }
    }
}

