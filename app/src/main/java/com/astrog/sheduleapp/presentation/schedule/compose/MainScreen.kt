@file:OptIn(ExperimentalFoundationApi::class)

package com.astrog.sheduleapp.presentation.schedule.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.astrog.sheduleapp.R
import com.astrog.sheduleapp.presentation.ScheduleAppBar
import com.astrog.sheduleapp.presentation.schedule.ScheduleViewModel
import com.astrog.sheduleapp.presentation.schedule.model.Page
import com.astrog.sheduleapp.presentation.settingsDialog.compose.SettingsDialog
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.launch
import java.time.LocalDate

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: ScheduleViewModel = hiltViewModel()) {
    val state by viewModel.state
    val pagerState = rememberPagerState(Page.Initial.ordinal)

    var dialogShown by rememberSaveable { mutableStateOf(viewModel.isIdAbsent) }

    val calendarState = rememberUseCaseState(visible = false)

    fun clearPagesAndLoadCurrent() {
        viewModel.clearPagesAndLoadInitial()
        viewModel.requestScheduleRange(Page(pagerState.currentPage))
    }

    Scaffold(
        topBar = {
            ScheduleAppBar(
                title = stringResource(R.string.app_bar_title),
                onMenuClick = { dialogShown = !dialogShown },
                onRefreshClick = ::clearPagesAndLoadCurrent,
                onCalendarClick = { calendarState.show() },
                expanded = dialogShown,
            )
        },
    ) {
        val pagerScrollCoroutineScope = rememberCoroutineScope()

        var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }

        ScheduleViewPager(
            pagerState = pagerState,
            requestSchedulePage = viewModel::requestScheduleRange,
            onPageChanged = { selectedDate = it.date },
            state = state,
        ) {
            pagerScrollCoroutineScope.launch {
                pagerState.animateScrollToPage(Page.Initial.ordinal)
            }
        }

        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                yearSelection = true,
                monthSelection = true,
                style = CalendarStyle.MONTH,
            ),
            selection = CalendarSelection.Date(selectedDate = selectedDate) { date ->
                pagerScrollCoroutineScope.launch {
                    pagerState.animateScrollToPage(Page.dateToOrdinal(date))
                }
            },
        )

        if (dialogShown) {
            SettingsDialog(
                updateScheduleContent = ::clearPagesAndLoadCurrent,
                onDismiss = { dialogShown = false }
            )
        }
    }
}