package com.bhandari.draggable

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bhandari.drag.layout.Direction
import com.bhandari.drag.layout.DragMode
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Activity() {
    var alpha by remember { mutableFloatStateOf(0f) }

    DraggableLayoutComposeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { _ ->
            MainLayout()
            DraggableLayout(
                dragListener = {
                    println("Drag listener $it")
                    alpha = it
                }
            )
        }
    }
}

@Composable
fun MainLayout(scale: Float = 1f) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.sky),
        contentDescription = "Superman",
        contentScale = ContentScale.Crop,
        alpha = scale
    )
}


@Composable
fun DraggableLayout(dragListener: (Float) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .getDraggableModifier(
                direction = Direction.UP,
                mode = DragMode.SNAP,
                percentShow = 0.1f,
                snapThreshold = 0.5f,
                maxReveal = 0.9f,
                percentRevealListener = dragListener
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF222222),
        ),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.superman),
            contentDescription = "Superman",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Activity()
}