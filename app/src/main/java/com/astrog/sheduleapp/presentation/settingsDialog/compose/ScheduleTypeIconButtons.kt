package com.astrog.sheduleapp.presentation.settingsDialog.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.astrog.sheduleapp.R
import com.astrog.sheduleapp.domain.model.ScheduleType

@Composable
fun ScheduleTypeIconButtons(
    selectedType: ScheduleType,
    modifier: Modifier = Modifier,
    changeType: (ScheduleType) -> Unit,
) {
    val typeToIcon = mapOf(
        ScheduleType.AUDITORIUM to painterResource(id = R.drawable.class_room),
        ScheduleType.LECTURER to painterResource(id = R.drawable.teacher),
        ScheduleType.STUDENT to painterResource(id = R.drawable.student),
        ScheduleType.GROUP to painterResource(id = R.drawable.students),
    )

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier)
        ) {
            for (type in ScheduleType.values()) {
                IconButton(
                    onClick = { changeType.invoke(type) },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = typeToIcon[type]!!,
                        contentDescription = type.name.replaceFirstChar(Char::titlecase),
                        tint = if (selectedType == type)
                            MaterialTheme.colors.primary
                        else
                            MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(45.dp),

                        )
                }
            }
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}