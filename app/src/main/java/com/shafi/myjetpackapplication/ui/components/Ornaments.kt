package com.shafi.myjetpackapplication.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shafi.myjetpackapplication.models.toArabicIndicDigits
import com.shafi.myjetpackapplication.ui.theme.Amiri
import com.shafi.myjetpackapplication.ui.theme.QuranicColors
import com.shafi.myjetpackapplication.ui.theme.QuranicTheme
import kotlin.math.cos
import kotlin.math.sin

/** Two overlapping squares forming the eight-point star (rub el hizb). */
fun eightPointStarPath(center: Offset, radius: Float): Path {
    val inner = radius * 0.7654f // cos(45°) / cos(22.5°)
    return Path().apply {
        for (i in 0 until 16) {
            val r = if (i % 2 == 0) radius else inner
            val angle = Math.toRadians(i * 22.5 - 90.0)
            val x = center.x + r * cos(angle).toFloat()
            val y = center.y + r * sin(angle).toFloat()
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }
}

/** Ayah or surah number set inside a gilded eight-point star, in Arabic-Indic digits. */
@Composable
fun AyahMedallion(
    number: Int,
    modifier: Modifier = Modifier,
    diameter: Dp = 40.dp,
    color: Color = QuranicTheme.colors.gold,
    textColor: Color = color,
) {
    Box(modifier = modifier.size(diameter), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = 1.2.dp.toPx()
            val star = eightPointStarPath(center, size.minDimension / 2 - stroke)
            drawPath(star, color.copy(alpha = 0.14f))
            drawPath(star, color, style = Stroke(width = stroke))
            drawCircle(color.copy(alpha = 0.5f), radius = size.minDimension * 0.3f, style = Stroke(width = 0.8.dp.toPx()))
        }
        Text(
            text = number.toArabicIndicDigits(),
            color = textColor,
            // Three-digit surah numbers need a smaller size to stay inside the inner ring.
            style = TextStyle(fontFamily = Amiri, fontSize = (diameter.value * if (number >= 100) 0.25f else 0.34f).sp),
        )
    }
}

/** Hairline that fades in from both ends toward a small gold diamond. */
@Composable
fun GoldDivider(modifier: Modifier = Modifier, color: Color = QuranicTheme.colors.gold) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(Brush.horizontalGradient(listOf(Color.Transparent, color)))
        )
        Spacer(Modifier.width(8.dp))
        Canvas(Modifier.size(7.dp)) {
            val path = Path().apply {
                moveTo(size.width / 2, 0f)
                lineTo(size.width, size.height / 2)
                lineTo(size.width / 2, size.height)
                lineTo(0f, size.height / 2)
                close()
            }
            drawPath(path, color)
        }
        Spacer(Modifier.width(8.dp))
        Box(
            Modifier
                .weight(1f)
                .height(1.dp)
                .background(Brush.horizontalGradient(listOf(color, Color.Transparent)))
        )
    }
}

/** Faint lattice of eight-point stars, as seen in Islamic tilework. */
fun Modifier.geometricPattern(color: Color, cell: Dp = 64.dp): Modifier = drawWithCache {
    val step = cell.toPx()
    val pattern = Path()
    var row = 0
    var y = 0f
    while (y < size.height + step) {
        var x = if (row % 2 == 0) 0f else step / 2
        while (x < size.width + step) {
            pattern.addPath(eightPointStarPath(Offset(x, y), step * 0.32f))
            x += step
        }
        y += step / 2
        row++
    }
    val stroke = Stroke(width = 1.dp.toPx())
    onDrawBehind { drawPath(pattern, color, style = stroke) }
}

/** Deep emerald gradient with a soft gold glow and the star lattice on top. */
fun Modifier.heroBackground(colors: QuranicColors): Modifier = this
    .background(Brush.verticalGradient(listOf(colors.heroTop, colors.heroBottom)))
    .background(Brush.radialGradient(listOf(colors.gold.copy(alpha = 0.16f), Color.Transparent)))
    .geometricPattern(colors.gold.copy(alpha = 0.09f))

/** Solid emerald band behind the status bar so its light icons stay legible over scrolled content. */
@Composable
fun StatusBarScrim(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .background(QuranicTheme.colors.heroTop)
            .statusBarsPadding()
    )
}
