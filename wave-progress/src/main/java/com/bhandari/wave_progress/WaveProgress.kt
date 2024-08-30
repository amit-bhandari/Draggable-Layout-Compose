package com.bhandari.wave_progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WaveProgress(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2
        val baseRadius = size.minDimension / 3  // Base radius for the circle
        val bumpAmplitude = baseRadius / 5      // Amplitude of the bumps
        val frequency = 5                       // Number of bumps around the circle

        val stepAngle = 10  // Angle step in degrees

        // Start from the top of the circle
        for (angle in 0..360 step stepAngle) {
            val radian = Math.toRadians(angle.toDouble())
            val bump = bumpAmplitude * sin(frequency * radian) // Adjust radius for bumps
            val adjustedRadius = baseRadius + bump

            val x = centerX + adjustedRadius * cos(radian).toFloat()
            val y = centerY + adjustedRadius * sin(radian).toFloat()

            if (angle == 0) {
                path.moveTo(x.toFloat(), y.toFloat())
            } else {
                path.lineTo(x.toFloat(), y.toFloat())
            }
        }

        // Close the path to complete the circle
        path.close()

        drawPath(
            path,
            color = Color.Magenta,
            style = Stroke(20f)
        )
    }
}

@Preview
@Composable
fun WavePreview(modifier: Modifier = Modifier) {
    WaveProgress()
}