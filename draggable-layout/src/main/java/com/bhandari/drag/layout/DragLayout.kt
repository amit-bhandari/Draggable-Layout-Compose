package com.bhandari.drag.layout

import android.util.Log
import androidx.annotation.FloatRange
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

/**
 * Modifier which can help you make your view draggable from any of the [Direction]
 * @param direction Direction in which view should be dragged
 * @param percentShow Initial percentage of total width for [Direction.RIGHT] or [Direction.LEFT] and height for [Direction.UP] or [Direction.DOWN] to be visible
 * @param maxReveal Maximum percentage of reveal possible for view after dragging
 * @param snapThreshold Threshold which decides where the view will rest once finger is lifted.
 *        View will either go to [percentShow] or [maxReveal] depending on where drag action was released.
 * @param percentRevealListener listener which provides callback to know current reveal percentage
 */
@Composable
fun Modifier.getDraggableModifier(
    direction: Direction,
    @FloatRange(0.0, 1.0) percentShow: Float = 1f,
    @FloatRange(0.0, 1.0) maxReveal: Float = 1f,
    @FloatRange(0.0, 1.0) snapThreshold: Float = 0f,
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

    val delta =
        if (direction == Direction.UP || direction == Direction.DOWN) verticalDragDeltaAnimated
        else horizontalDragDeltaAnimated

    val dimen =
        if (direction == Direction.UP || direction == Direction.DOWN) height
        else width

    percentRevealListener(deltaToVisiblePercentage(delta, dimen))

    return this
        .drawBehind {
            height = size.height
            width = size.width
            when (direction) {
                Direction.DOWN, Direction.UP -> verticalDragDelta =
                    percentageToDelta(direction, percentShow, height)

                Direction.RIGHT, Direction.LEFT -> horizontalDragDelta =
                    percentageToDelta(direction, percentShow, width)
            }
            //this breaks UI todo figure out why
            /*Log.d(
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
                            val visiblePercent = deltaToVisiblePercentage(verticalDragDelta, height)
                            verticalDragDelta =
                                if (visiblePercent < snapThreshold)
                                    percentageToDelta(direction, percentShow, height)
                                else
                                    percentageToDelta(direction, maxReveal, height)
                        }
                    ) { _, dragAmount ->
                        val visiblePercent = deltaToVisiblePercentage(verticalDragDelta, height)
                        if (direction == Direction.UP && verticalDragDelta < 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (direction == Direction.DOWN && verticalDragDelta > 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (visiblePercent > maxReveal) {
                            Log.d(TAG, "Max reveal reached $maxReveal")
                        } else {
                            verticalDragDelta += dragAmount
                        }
                    }
                }

                Direction.RIGHT, Direction.LEFT -> {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            val visiblePercent =
                                deltaToVisiblePercentage(horizontalDragDelta, width)
                            horizontalDragDelta =
                                if (visiblePercent < snapThreshold)
                                    percentageToDelta(direction, percentShow, width)
                                else
                                    percentageToDelta(direction, maxReveal, width)
                        }
                    ) { _, dragAmount ->
                        val visiblePercent = deltaToVisiblePercentage(horizontalDragDelta, width)
                        if (direction == Direction.LEFT && horizontalDragDelta < 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (direction == Direction.RIGHT && horizontalDragDelta > 0) {
                            Log.d(TAG, "Reveal overshoot $direction: ")
                        } else if (visiblePercent > maxReveal) {
                            Log.d(TAG, "Max reveal reached $maxReveal")
                        } else {
                            horizontalDragDelta += dragAmount
                        }
                    }
                }
            }
        }
}

fun deltaToVisiblePercentage(delta: Float, dimen: Float): Float {
    return 1 - (abs(delta) / dimen)
}

fun percentageToDelta(direction: Direction, percent: Float, dimen: Float): Float {
    return (if (direction == Direction.DOWN || direction == Direction.RIGHT) -1f else 1f) * dimen * (1f - percent)
}