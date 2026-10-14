package com.shafi.myjetpackapplication.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val LocalQuranicColors = staticCompositionLocalOf { LightQuranicColors }

private val QuranicShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(22.dp),
    large = RoundedCornerShape(32.dp),
)

object QuranicTheme {
    val colors: QuranicColors
        @Composable
        @ReadOnlyComposable
        get() = LocalQuranicColors.current
}

@Composable
fun QuranicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                // Every screen starts with the dark emerald header, so status bar icons stay light.
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalQuranicColors provides if (darkTheme) DarkQuranicColors else LightQuranicColors
    ) {
        MaterialTheme(
            colors = if (darkTheme) DarkPalette else LightPalette,
            typography = QuranicTypography,
            shapes = QuranicShapes,
            content = content,
        )
    }
}
