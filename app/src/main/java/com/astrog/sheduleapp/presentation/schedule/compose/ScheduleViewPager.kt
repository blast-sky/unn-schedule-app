@file:OptIn(ExperimentalFoundationApi::class)

package com.astrog.sheduleapp.presentation.schedule.compose

import android.annotation.SuppressLint
import android.view.ContextMenu
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.astrog.sheduleapp.internal.schedule.SubjectDto
import com.astrog.sheduleapp.presentation.ScheduleAppBar
import com.astrog.sheduleapp.presentation.schedule.ScheduleState
import com.astrog.sheduleapp.presentation.schedule.ScheduleViewModel
import com.astrog.sheduleapp.presentation.settingsdialog.compose.SettingsDialog
import com.astrog.sheduleapp.util.initPage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val defaultPadding = 10.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScheduleViewPager(viewModel: ScheduleViewModel = hiltViewModel()) {
    val state by viewModel.state
    val pagerState = rememberPagerState(initPage)
    val loadedPages = remember { mutableSetOf<Int>() }
    var dialogShown by remember { mutableStateOf(viewModel.isIdAbsent) }

    fun update() {
        viewModel.removeAllAndLoadInitPage()
        loadedPages.clear()
    }

    Scaffold(
        topBar = {
            ScheduleAppBar(
                title = "Расписание занятий",
                onMenuClick = { dialogShown = !dialogShown },
                onRefreshClick = ::update,
                expanded = dialogShown,
            )
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                pageCount = Int.MAX_VALUE,
                state = pagerState,
                modifier = Modifier.weight(1f),
            ) { currentPage ->
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = viewModel.pageToDateString(currentPage),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                    if (currentPage !in loadedPages) {
                        loadedPages += currentPage
                        viewModel.loadSchedule(currentPage)
                    }
                    when (val cashedState = state[currentPage]) {
                        ScheduleState.Loading -> ShowLoading()
                        is ScheduleState.Ready -> ShowSchedule(cashedState.subjects)
                        is ScheduleState.Error -> ShowError(message = cashedState.error)
                        null -> Unit
                    }
                }
            }
            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = initPage)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(defaultPadding),
            ) {
                Text(
                    text = "Текущий день",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }

        if (dialogShown) {
            SettingsDialog(onDismiss = { needToUpdate ->
                if (needToUpdate)
                    update()
                dialogShown = false
            })
        }
    }
}


@Composable
fun ShowSchedule(subjects: List<SubjectDto>) {
    var currentSubjectsList by remember { mutableStateOf<List<SubjectDto>>(listOf()) }
    LaunchedEffect(key1 = LocalContext.current) {
        for (subject in subjects) {
            currentSubjectsList = currentSubjectsList + subject
            delay(50)
        }
        currentSubjectsList = subjects
    }
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
            items = currentSubjectsList,
        ) { subject ->
            SubjectDtoItem(subject)
        }
    }
}

@Composable
fun flingBehavior(pagerState: PagerState, noOfPages: Int): FlingBehavior {
    return PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )
}

@Composable
fun ShowError(message: String) {
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
fun ShowLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}