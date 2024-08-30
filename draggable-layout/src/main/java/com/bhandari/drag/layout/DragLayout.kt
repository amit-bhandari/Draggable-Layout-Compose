package com.bhandari.drag.layout

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import kotlin.math.abs

enum class Direction { DOWN, UP, RIGHT, LEFT }

@Composable
fun Modifier.getDraggableModifier(
    direction: Direction,
    percentShow: Float = 0.2f,
    maxReveal: Float = 1f,
    snapThreshold: Float = 0f,
    percentRevealListener: (Float) -> Unit = {}
): Modifier {
    var verticalDragDelta by remember { mutableFloatStateOf(0f) }
    var horizontalDragDelta by remember { mutableFloatStateOf(0f) }

    val verticalDragDeltaAnimated by animateFloatAsState(
        targetValue = verticalDragDelta,
        label = "Vertical Offset Animation",
    )

    val horizontalDragDeltaAnimated by animateFloatAsState(
        targetValue = horizontalDragDelta,
        label = "Horizontal Offset Animation",
    )

    var height by remember { mutableFloatStateOf(0f) }
    var width by remember { mutableFloatStateOf(0f) }

    fun deltaToVisiblePercentage(delta: Float, dimen: Float): Float {
        return 1 - (abs(delta) / dimen)
    }

    fun initialDelta(): Float {
        return when (direction) {
            Direction.DOWN, Direction.UP -> {
                (if (direction == Direction.DOWN) -1f else 1f) * height * (1f - percentShow)
            }

            Direction.RIGHT, Direction.LEFT -> {
                (if (direction == Direction.RIGHT) -1f else 1f) * width * (1f - percentShow)
            }
        }
    }

    fun maxRevealDelta(): Float {
        return when (direction) {
            Direction.DOWN, Direction.UP -> {
                (if (direction == Direction.DOWN) -1f else 1f) * height * (1f - maxReveal)
            }

            Direction.RIGHT, Direction.LEFT -> {
                (if (direction == Direction.RIGHT) -1f else 1f) * width * (1f - maxReveal)
            }
        }
    }


    val delta = if (direction == Direction.UP || direction == Direction.DOWN)
        verticalDragDeltaAnimated
    else
        horizontalDragDeltaAnimated

    val dimen = if (direction == Direction.UP || direction == Direction.DOWN)
        height
    else
        width

    percentRevealListener(deltaToVisiblePercentage(delta, dimen))

    return this
        .drawBehind {
            when (direction) {
                Direction.DOWN, Direction.UP -> {
                    height = size.height
                    verticalDragDelta = initialDelta()
                }

                Direction.RIGHT, Direction.LEFT -> {
                    width = size.width
                    horizontalDragDelta = initialDelta()
                }
            }
            /*
            //this breaks UI todo figure out why
            Log.d(
                TAG,
                "Initial size:$size. Offset applied horizontal: $horizontalDragDelta, vertical: $verticalDragDelta "
            )*/
        }
        .graphicsLayer(
            translationX = horizontalDragDeltaAnimated,
            translationY = verticalDragDeltaAnimated
        )
        .pointerInput(Unit) {
            when (direction) {
                Direction.DOWN, Direction.UP -> {
                    detectVerticalDragGestures(
                        onDragEnd = {
                            verticalDragDelta =
                                if (deltaToVisiblePercentage(verticalDragDelta, height) < snapThreshold)
                                    initialDelta()
                                 else
                                    maxRevealDelta()
                        }
                    ) { _, dragAmount ->
                        if (direction == Direction.UP && verticalDragDelta < 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (direction == Direction.DOWN && verticalDragDelta > 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (deltaToVisiblePercentage(verticalDragDelta, height) > maxReveal) {
                            Log.d(TAG, "Max reveal reached $maxReveal")
                        } else {
                            verticalDragDelta += dragAmount
                        }
                    }
                }

                Direction.RIGHT, Direction.LEFT -> {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                             horizontalDragDelta =
                                if (deltaToVisiblePercentage(horizontalDragDelta, width) < snapThreshold)
                                    initialDelta()
                                else
                                    maxRevealDelta()
                        }
                    ) { _, dragAmount ->
                        if (direction == Direction.LEFT && horizontalDragDelta < 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (direction == Direction.RIGHT && horizontalDragDelta > 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (deltaToVisiblePercentage(horizontalDragDelta, width) > maxReveal) {
                            Log.d(TAG, "Max reveal reached $maxReveal")
                        } else {
                            horizontalDragDelta += dragAmount
                        }
                    }
                }
            }
        }
}