package com.bhandari.drag.layout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

enum class Direction { UP, DOWN, LEFT, RIGHT }

@Composable
fun Modifier.getDraggableModifier(
    direction: Direction,
    percentShow: Float = 0.2f,
    snapThreshold: Float = 0f,
    percentRevealListener: (Float) -> Unit = {}
): Modifier {
    var verticalDelta by remember {
        mutableFloatStateOf(0f)
    }

    val verticalAnimated by animateFloatAsState(
        targetValue = verticalDelta,
        label = "Offset Animation",
    )

    var height by remember {
        mutableFloatStateOf(0f)
    }

    return this
        .drawBehind { verticalDelta = size.height.also { height = it } * (1f - percentShow) }
        .graphicsLayer(translationY = verticalAnimated)
        .pointerInput(Unit) {
            detectVerticalDragGestures(
                onDragEnd = {
                    val percent = verticalDelta / height
                    verticalDelta = if (percent > 1 - snapThreshold)
                        height * (1f - percentShow) //snap to collapsed
                    else
                        0f //snap to expanded
                }
            ) { _, dragAmount ->
                percentRevealListener(1 - (verticalDelta / height))
                verticalDelta += dragAmount
            }
        }
}