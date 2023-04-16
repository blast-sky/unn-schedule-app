package com.astrog.sheduleapp.presentation.settingsdialog.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun IdTextField(currentId: String, onIdChange: (String) -> Unit) {
    TextField(
        label = { Text(text = "id") },
        modifier = Modifier.fillMaxWidth(),
        value = if (currentId == "-1") "" else currentId,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            try {
                it.toLong()
            } catch (ex: NumberFormatException) { }
            onIdChange.invoke(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = MaterialTheme.shapes.medium,
    )
}