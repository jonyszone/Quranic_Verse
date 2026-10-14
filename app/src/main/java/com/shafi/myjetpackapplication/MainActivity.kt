package com.shafi.myjetpackapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.shafi.myjetpackapplication.screens.VerseListItemDetails
import com.shafi.myjetpackapplication.screens.VerseListScreen
import com.shafi.myjetpackapplication.ui.components.eightPointStarPath
import com.shafi.myjetpackapplication.ui.components.heroBackground
import com.shafi.myjetpackapplication.ui.theme.QuranicTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        CoroutineScope(Dispatchers.IO).launch {
            DataManager.loadAssetsFromFile(applicationContext)
        }

        setContent {
            QuranicTheme {
                App()
            }
        }
    }

}

@Composable
fun App() {
    // Hoisted so the list keeps its scroll position after returning from a verse.
    val listState = rememberLazyListState()
    val page = if (DataManager.isDataLoaded.value) DataManager.currentPage.value else null

    Crossfade(targetState = page, animationSpec = tween(durationMillis = 400)) { current ->
        when (current) {
            null -> LoadingScreen()
            Pages.LISTING -> VerseListScreen(data = DataManager.data, listState = listState) {
                DataManager.switchPages(it)
            }
            Pages.DETAILS -> DataManager.currentVerses?.let { VerseListItemDetails(verse = it) }
        }
    }
}

@Composable
private fun LoadingScreen() {
    val colors = QuranicTheme.colors
    val rotation by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 90f,
        animationSpec = infiniteRepeatable(tween(2400, easing = LinearEasing), RepeatMode.Restart),
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .heroBackground(colors)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Canvas(
                Modifier
                    .size(72.dp)
                    .rotate(rotation)
            ) {
                val star = eightPointStarPath(center, size.minDimension / 2 - 2.dp.toPx())
                drawPath(star, colors.gold.copy(alpha = 0.15f))
                drawPath(star, colors.gold, style = Stroke(width = 1.5.dp.toPx()))
            }
            Spacer(Modifier.height(28.dp))
            Text(
                text = "Quranic Verses",
                style = MaterialTheme.typography.h5,
                color = colors.onHero
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "PREPARING VERSES",
                style = MaterialTheme.typography.overline,
                color = colors.gold
            )
        }
    }
}

enum class Pages {
    LISTING,
    DETAILS
}
