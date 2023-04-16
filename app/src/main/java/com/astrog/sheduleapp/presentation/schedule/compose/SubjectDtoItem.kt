package com.astrog.sheduleapp.presentation.schedule.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.astrog.sheduleapp.internal.schedule.SubjectDto

@Composable
fun SubjectDtoItem(subject: SubjectDto, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 40.dp)
            .padding(defaultPadding)
            .then(modifier),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Column {
            val headerColor = if (subject.kindOfWork == "Лекция") {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.secondary
            }
            var needToShowKindOfWork by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerColor)
                    .clickable { needToShowKindOfWork = !needToShowKindOfWork }
                    .animateContentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .padding(defaultPadding)
                        .weight(1f),
                ) {
                    Text(
                        text = subject.discipline,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.onPrimary,
                        softWrap = true,
                    )
                    if (needToShowKindOfWork) {
                        Text(
                            text = subject.kindOfWork,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colors.onPrimary,
                            softWrap = true,
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(defaultPadding)
                        .defaultMinSize(minWidth = 40.dp),
                    text = "${subject.beginLesson} - ${subject.endLesson}",
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colors.onPrimary,
                    softWrap = false,
                )
            }
            Text(
                modifier = Modifier
                    .padding(
                        top = defaultPadding,
                        start = defaultPadding,
                        end = defaultPadding
                    ),
                text = "${subject.building} - ${subject.auditorium}",
                textAlign = TextAlign.Start,
                softWrap = true,
            )
            Text(
                modifier = Modifier.padding(
                    start = defaultPadding,
                    end = defaultPadding,
                    bottom = defaultPadding
                ),
                text = subject.lecturer,
            )
        }
    }
}