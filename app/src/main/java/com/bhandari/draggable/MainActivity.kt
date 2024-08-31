package com.bhandari.draggable

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun MainLayout() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.2f),
            painter = painterResource(id = R.drawable.batman4),
            contentDescription = "Batman",
            contentScale = ContentScale.Crop,
        )
    }
}


@Composable
fun DraggableLayout(dragListener: (Float) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .getDraggableModifier(
                direction = Direction.UP,
                mode = DragMode.SNAP,
                percentShow = 0.2f,
                snapThreshold = 0.5f,
                maxReveal = 0.8f,
                percentRevealListener = dragListener
            ),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(5.dp, Color.White),
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