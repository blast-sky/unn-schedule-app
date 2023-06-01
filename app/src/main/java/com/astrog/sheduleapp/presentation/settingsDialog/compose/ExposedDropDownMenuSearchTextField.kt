@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.astrog.sheduleapp.presentation.settingsDialog.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.astrog.sheduleapp.R
import com.astrog.sheduleapp.domain.model.lesson.SearchResult
import com.astrog.sheduleapp.util.defaultPadding

@Composable
fun ExposedDropDownMenuSearchTextField(
    suggestedResults: List<SearchResult>,
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
        },
    ) {
        SearchTextField(
            focusRequester = focusRequester,
            selectedItem = selectedItem,
            expand = { expanded = true },
            onValueChange = {
                searchTerm.invoke(it)
                onSelectedItemChange.invoke(it)
            },
        )

        ExposedDropdownMenu(
            expanded = if (suggestedResults.isEmpty()) false else expanded,
            onDismissRequest = { expanded = false },
        ) {
            suggestedResults.forEachIndexed { index, result ->
                DropdownMenuItem(
                    modifier = Modifier.padding(horizontal = defaultPadding),
                    onClick = {
                        onSelectedItemChange.invoke(result.label)
                        setActiveId.invoke(result.id)
                        expanded = false
                        focusManager.clearFocus()
                    },
                ) {
                    Column {
                        Text(text = result.label)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = result.description, style = MaterialTheme.typography.caption)
                    }
                }

                if (index != suggestedResults.lastIndex)
                    Divider(modifier = Modifier.padding(horizontal = defaultPadding))
            }
        }
    }
}

@Composable
private fun SearchTextField(
    focusRequester: FocusRequester,
    selectedItem: String,
    expand: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .animateContentSize(),
        value = selectedItem,
        onValueChange = { value ->
            expand.invoke()
            onValueChange.invoke(value)
        },
        label = {
            Text(
                text = if (selectedItem.isBlank())
                    stringResource(R.string.start_to_write)
                else
                    stringResource(R.string.search)
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                onValueChange.invoke(String())
            }) {
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
}