package com.astrog.sheduleapp.presentation.settingsdialog.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.astrog.sheduleapp.domain.model.SearchResultDto

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchTextField(
    suggestedResults: List<SearchResultDto>,
    selectedItem: String,
    onSelectedItemChange: (String) -> Unit,
    searchTerm: (String) -> Unit,
    setActiveId: (Long) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = selectedItem,
            onValueChange = { value ->
                expanded = true
                searchTerm.invoke(value)
                onSelectedItemChange.invoke(value)
            },
            label = {
                Text(
                    text = if (selectedItem.isBlank())
                        "Начните писать..."
                    else
                        "Поиск"
                )
            },
            trailingIcon = {
                IconButton(onClick = { onSelectedItemChange.invoke(String()) }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            shape = MaterialTheme.shapes.medium,
        )

        ExposedDropdownMenu(
            expanded = if (suggestedResults.isEmpty()) false else expanded,
            onDismissRequest = { expanded = false },
        ) {
            suggestedResults.forEachIndexed { index, result ->
                DropdownMenuItem(onClick = {
                    onSelectedItemChange.invoke(result.label)
                    setActiveId.invoke(result.id)
                    expanded = false
                    focusManager.clearFocus()
                }) {
                    Text(text = "${result.label}\n${result.description}")
                }

                if(index != suggestedResults.lastIndex)
                    Divider()
            }
        }
    }
}