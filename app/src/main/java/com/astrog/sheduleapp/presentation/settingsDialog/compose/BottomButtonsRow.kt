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


@Composable
fun BottomButtonsRow(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Button(
            onClick = onDismiss,
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
            onSubmit.invoke()
            onDismiss.invoke()
        }) {
            Text(text = stringResource(R.string.submit))
        }
    }
}