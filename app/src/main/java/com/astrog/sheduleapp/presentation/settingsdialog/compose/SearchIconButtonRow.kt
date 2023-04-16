package com.astrog.sheduleapp.presentation.settingsdialog.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.astrog.sheduleapp.R
import com.astrog.sheduleapp.util.AUDITORIUM
import com.astrog.sheduleapp.util.GROUP
import com.astrog.sheduleapp.util.LECTURER
import com.astrog.sheduleapp.util.STUDENT

@Composable
fun SearchIconButtonsRow(selectedType: String, changeType: (String) -> Unit) {
    val searchTypes = listOf(
        STUDENT,
        AUDITORIUM,
        LECTURER,
        GROUP,
    )

    val typeToIcon = mapOf(
        AUDITORIUM to painterResource(id = R.drawable.ic_auditorium_24),
        LECTURER to painterResource(id = R.drawable.ic_lectorer_24),
        STUDENT to painterResource(id = R.drawable.ic_student_24),
        GROUP to painterResource(id = R.drawable.ic_group_24),
    )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (type in searchTypes) {
            IconButton(
                onClick = { changeType.invoke(type) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = typeToIcon[type]!!,
                    contentDescription = type.replaceFirstChar(Char::titlecase),
                    tint = if (selectedType == type)
                        MaterialTheme.colors.primary
                    else
                        MaterialTheme.colors.onSurface
                )
            }
        }
    }
}