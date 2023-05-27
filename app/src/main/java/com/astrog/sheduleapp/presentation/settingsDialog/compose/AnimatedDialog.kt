@file:OptIn(ExperimentalAnimationApi::class, ExperimentalAnimationApi::class)

package com.astrog.sheduleapp.presentation.settingsDialog.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


private const val ANIMATION_TIME = 300

@Composable
fun AnimatedDialog(
    onDismissRequest: () -> Unit,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    var animateTrigger by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animateTrigger = true }

    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.size(500.dp)) {
            AnimatedVisibility(
                visible = animateTrigger,
                enter = scaleIn(
                    animationSpec = tween(ANIMATION_TIME)
                ),
                exit = scaleOut(
                    animationSpec = tween(ANIMATION_TIME)
                ),
                content = content,
            )
        }
    }
}