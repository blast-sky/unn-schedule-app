@file:OptIn(ExperimentalFoundationApi::class)

package com.astrog.sheduleapp.presentation.schedule.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.astrog.sheduleapp.presentation.ScheduleAppBar
import com.astrog.sheduleapp.presentation.schedule.ScheduleViewModel
import com.astrog.sheduleapp.presentation.settingsdialog.compose.SettingsDialog
import com.astrog.sheduleapp.util.initialPage
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: ScheduleViewModel = hiltViewModel()) {
    val state by viewModel.state
    val pagerState = rememberPagerState(initialPage)
    var dialogShown by rememberSaveable { mutableStateOf(viewModel.isIdAbsent) }

    val calendarState = rememberUseCaseState(visible = false)

    fun clearPagesAndLoadCurrent() {
        viewModel.clearPagesAndLoadInitial()
        viewModel.requestSchedule(pagerState.currentPage)
    }

    Scaffold(
        topBar = {
            ScheduleAppBar(
                title = "Расписание занятий",
                onMenuClick = { dialogShown = !dialogShown },
                onRefreshClick = ::clearPagesAndLoadCurrent,
                onCalendarClick = { calendarState.show() },
                expanded = dialogShown,
            )
        },
    ) {
        val pagerScrollCoroutineScope = rememberCoroutineScope()

        ScheduleViewPager(
            pagerState = pagerState,
            requestSchedulePage = viewModel::requestSchedule,
            pageToDateString = viewModel::pageToDateString,
            state = state,
        ) {
            pagerScrollCoroutineScope.launch {
                pagerState.animateScrollToPage(initialPage)
            }
        }

        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                yearSelection = true,
                monthSelection = true,
                style = CalendarStyle.MONTH,
            ),
            selection = CalendarSelection.Date { date ->
                pagerScrollCoroutineScope.launch {
                    pagerState.animateScrollToPage(viewModel.dateToPage(date))
                }
            },
        )

        if (dialogShown) {
            SettingsDialog(
                onDismiss = { needToUpdate ->
                    if (needToUpdate)
                        clearPagesAndLoadCurrent()
                    dialogShown = false
                }
            )
        }
    }
}