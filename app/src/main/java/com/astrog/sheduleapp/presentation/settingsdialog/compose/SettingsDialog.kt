package com.astrog.sheduleapp.presentation.settingsdialog.compose

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.astrog.sheduleapp.presentation.settingsdialog.SettingsDialogViewModel
import com.astrog.sheduleapp.util.roundRadius

@Composable
fun SettingsDialog(
    viewModel: SettingsDialogViewModel = hiltViewModel(),
    onDismiss: (needToUpdate: Boolean) -> Unit,
) {
    val state by viewModel.state
    AnimatedTransitionDialogEntryOnly(
        onDismissRequest = { onDismiss.invoke(false) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            shape = RoundedCornerShape(roundRadius),
        ) {
            var currentType by remember { mutableStateOf(state.activeType) }
            var currentId by remember { mutableStateOf(state.activeId.toString()) }
            var selectedTerm by remember { mutableStateOf(state.activeTerm) }
            Column(
                modifier = Modifier
                    .height(height = 500.dp)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                SearchIconButtonsRow(currentType) {
                    currentType = it
                    currentId = String()
                    selectedTerm = String()
                }
                IdTextField(currentId) {
                    currentId = it
                    selectedTerm = String()
                }
                Spacer(modifier = Modifier.height(10.dp))
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Button(
                        onClick = { onDismiss.invoke(false) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.surface,
                        ),
                        elevation = ButtonDefaults.elevation(0.dp),
                    ) {
                        Text(
                            text = "Отмена",
                            color = Color.Gray,
                        )
                    }

                    Button(onClick = {
                        try {
                            viewModel.setActiveState(
                                type = currentType,
                                id = currentId.toLong(),
                                term = selectedTerm,
                            )
                        } catch (ex: NumberFormatException) {
                            Log.e("SettingDialog", ex.stackTraceToString())
                        }
                        onDismiss.invoke(true)
                    }) {
                        Text(text = "Подтвердить")
                    }
                }
            }
        }
    }
}
