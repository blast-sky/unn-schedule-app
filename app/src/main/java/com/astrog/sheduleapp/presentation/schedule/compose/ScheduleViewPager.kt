@file:OptIn(ExperimentalFoundationApi::class)

package com.astrog.sheduleapp.presentation.schedule.compose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.astrog.sheduleapp.R
import com.astrog.sheduleapp.internal.dto.LessonDto
import com.astrog.sheduleapp.presentation.schedule.ScheduleState
import com.astrog.sheduleapp.presentation.schedule.ScheduleStateMap
import com.astrog.sheduleapp.presentation.schedule.dateToStringWithDayOfWeek
import com.astrog.sheduleapp.presentation.schedule.model.Page
import com.astrog.sheduleapp.presentation.schedule.model.SubjectPresentation
import com.astrog.sheduleapp.util.defaultPadding
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar.MONDAY

@Composable
fun ScheduleViewPager(
    pagerState: PagerState,
    requestSchedulePage: (Page) -> Unit,
    onPageChanged: (Page) -> Unit,
    state: ScheduleStateMap,
    onMoveToInitial: () -> Unit,
) {
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { ordinal ->
            val page = Page(ordinal)
            onPageChanged.invoke(page)
            requestSchedulePage.invoke(page)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = Int.MAX_VALUE,
            state = pagerState,
            modifier = Modifier.weight(1f),
            flingBehavior = flingBehavior(pagerState = pagerState),
        ) { pageOrdinal ->
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = dateToStringWithDayOfWeek(Page.ordinalToDate(pageOrdinal)),
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                when (val cashedState = state[Page(pageOrdinal)]) {
                    ScheduleState.Loading -> ShowLoading()
                    is ScheduleState.Ready -> ShowSchedule(subjects = cashedState.subjects)
                    is ScheduleState.Error.LoadError ->
                        ShowError(message = stringResource(R.string.failed_to_load_data))

                    ScheduleState.Error.NullIdError -> Unit
                    null -> Unit
                }
            }
        }

        AnimatedVisibility(
            visible = pagerState.currentPage != Page.Initial.ordinal,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(defaultPadding),
        ) {
            RollbackButton { onMoveToInitial.invoke() }
        }
    }
}

@Composable
fun flingBehavior(pagerState: PagerState): SnapFlingBehavior {
    return PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1),
        lowVelocityAnimationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessVeryLow,
        ),
    )
}

@Composable
private fun RollbackButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick.invoke() },
    ) {
        Text(
            text = "Вернуться на текущий день",
            color = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
private fun ShowSchedule(subjects: List<SubjectPresentation>) {
    if (subjects.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Пусто",
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = subjects,
        ) { subject ->
            SubjectDtoItem(subject)
        }
    }
}

@Composable
private fun ShowError(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            backgroundColor = MaterialTheme.colors.error,
            contentColor = MaterialTheme.colors.onError,
        ) {
            Text(
                text = message,
                color = MaterialTheme.colors.onError,
            )
        }
    }
}

@Composable
private fun ShowLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}


@Preview(name = "light", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "night", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewScheduleViewPager() {
    ScheduleViewPager(
        pagerState = rememberPagerState(),
        requestSchedulePage = {},
        onPageChanged = {},
        state = mapOf(
            Page.Initial to ScheduleState.Ready(
                listOf(
                    SubjectPresentation(
                        LessonDto(
                            "",
                            MONDAY.toLong(),
                            "",
                            "",
                            LocalTime.now(),
                            LocalTime.now(),
                            LocalDate.now(),
                            "",
                            "",
                            "",
                            "",
                        ), true
                    )
                )
            )
        )
    ) { }
}
