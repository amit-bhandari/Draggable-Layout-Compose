package com.bhandari.draggable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhandari.drag.layout.Direction
import com.bhandari.drag.layout.DragMode
import com.bhandari.drag.layout.R
import com.bhandari.drag.layout.getDraggableModifier
import com.bhandari.draggable.ui.theme.DraggableLayoutComposeTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
    var alpha by remember { mutableFloatStateOf(0f) }
    
    DraggableLayoutComposeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            MainLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .alpha(1 - alpha)
            )
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
fun MainLayout(modifier: Modifier, scale: Float = 1f) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .weight(15f),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.arrow_back_ios),
                contentDescription = "Back",
            )
            Text(
                modifier = Modifier.weight(55f),
                text = "Now Playing",
                color = Color.White
            )
            Image(
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .weight(10f),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.ios_share),
                contentDescription = "Share"
            )
            Image(
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .weight(15f),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.star_24dp),
                contentDescription = "Star"
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Card(
                border = BorderStroke(2.dp, color = Color.White),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(250.dp, 250.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = com.bhandari.draggable.R.drawable.albumart),
                    contentDescription = "Back",
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sanam Teri Kasam",
                color = Color.White,
                fontSize = 24.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Harshvardhan",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp, 24.dp),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.keyboard_arrow_down),
                contentDescription = "Back",
            )
            Image(
                modifier = Modifier.size(24.dp, 24.dp),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.skip_previous),
                contentDescription = "Back"
            )
            Image(
                modifier = Modifier.size(70.dp, 70.dp),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.play_circle),
                contentDescription = "Back"
            )
            Image(
                modifier = Modifier.size(24.dp, 24.dp),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.skip_next),
                contentDescription = "Back"
            )
            Image(
                modifier = Modifier.size(24.dp, 24.dp),
                painter = painterResource(id = com.bhandari.draggable.R.drawable.shuffle),
                contentDescription = "Back"
            )
        }
    }
}


@Composable
fun DraggableLayout(dragListener: (Float) -> Unit) {
    Card(
        modifier = Modifier
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
            containerColor = Color.DarkGray,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text("Up Next", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Activity()
}