package com.bhandari.draggable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bhandari.drag.layout.Direction
import com.bhandari.drag.layout.R
import com.bhandari.drag.layout.getDraggableModifier
import com.bhandari.draggable.ui.theme.DraggableLayoutComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Activity()
        }
    }
}

@Composable
fun Activity(modifier: Modifier = Modifier) {
    DraggableLayoutComposeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            var scale by remember { mutableFloatStateOf(1f) }
            MainLayout(modifier = Modifier.padding(innerPadding), scale = scale)
            DraggableLayout(
                modifier = Modifier.padding(innerPadding),
                dragListener = { scale = 1 - it }
            )
        }
    }
}

@Composable
fun MainLayout(modifier: Modifier, scale: Float) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .scale(scale)
    ) {
        Card(
            border = BorderStroke(2.dp, color = Color.Magenta),
            modifier = modifier
                .align(Alignment.Center)
                .size(300.dp, 300.dp),
            shape = RoundedCornerShape(8.dp),
        ) {

        }
    }

}

@Composable
fun DraggableLayout(modifier: Modifier, dragListener: (Float) -> Unit) {
    Card(
        border = BorderStroke(5.dp, color = Color.Magenta),
        modifier = modifier
            .getDraggableModifier(
                direction = Direction.UP,
                percentShow = 0.2f,
                snapThreshold = 0.5f,
                maxReveal = 0.8f,
                percentRevealListener = dragListener
            ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.rocket),
                contentDescription = "Rocket",
                alignment = Alignment.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Activity()
}