package com.astrog.sheduleapp.presentation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun ScheduleAppBar(
    title: String,
    onMenuClick: () -> Unit,
    onRefreshClick: () -> Unit,
    expanded: Boolean,
) {
    var refreshAngle by remember { mutableStateOf(0f) }
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                AnimatedMenuIcon(expanded = expanded)
            }
        },
        actions = {
            IconButton(onClick = {
                refreshAngle += 360f
                onRefreshClick.invoke()
            }) {
                AnimatedRefreshIcon(refreshAngle = refreshAngle)
            }
        }
    )
}

@Composable
private fun AnimatedMenuIcon(expanded: Boolean) {
    val rotationAngle = animateFloatAsState(
        targetValue = if (expanded) -90f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
        ))

    Icon(
        imageVector = Icons.Filled.Menu,
        contentDescription = "Menu",
        modifier = Modifier.rotate(rotationAngle.value),
        tint = MaterialTheme.colors.onSurface
    )
}

@Composable
private fun AnimatedRefreshIcon(refreshAngle: Float) {
    val rotation by animateFloatAsState(
        targetValue = refreshAngle,
        animationSpec = tween(500)
    )

    val ration = -abs((rotation % 361) - 180f) / 180f + 2f
    val defaultSize = 24

    Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = "Refresh",
        modifier = Modifier
            .rotate(rotation)
            .size((ration * defaultSize).dp),
        tint = MaterialTheme.colors.onSurface
    )
}