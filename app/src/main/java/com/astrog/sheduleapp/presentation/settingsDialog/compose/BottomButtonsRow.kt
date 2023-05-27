package com.astrog.sheduleapp.presentation.settingsDialog.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.astrog.sheduleapp.R
import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.presentation.settingsDialog.SettingsDialogViewModel


@Composable
fun BottomButtonsRow(
    onDismiss: () -> Unit,
    viewModel: SettingsDialogViewModel,
    currentType: ScheduleType,
    currentId: String,
    selectedTerm: String,
    updateScheduleContent: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Button(
            onClick = { onDismiss.invoke() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.surface,
            ),
            elevation = ButtonDefaults.elevation(0.dp),
        ) {
            Text(
                text = stringResource(R.string.cancel),
                color = Color.Gray,
            )
        }

        Button(onClick = {
            viewModel.setActiveState(
                type = currentType,
                id = currentId,
                term = selectedTerm,
            )
            updateScheduleContent.invoke()
            onDismiss.invoke()
        }) {
            Text(text = stringResource(R.string.submit))
        }
    }
}