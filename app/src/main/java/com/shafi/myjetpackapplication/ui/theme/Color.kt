package com.shafi.myjetpackapplication.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val Emerald50 = Color(0xFFE6F2EE)
val Emerald300 = Color(0xFF5DBB9C)
val Emerald600 = Color(0xFF135C4B)
val Emerald700 = Color(0xFF0E4D3F)
val Emerald900 = Color(0xFF082A23)
val Emerald950 = Color(0xFF071A16)

val Gold = Color(0xFFD9B26A)
val GoldDeep = Color(0xFFA67C2E)

val Ivory = Color(0xFFF8F4EA)
val IvoryCard = Color(0xFFFFFDF8)
val Parchment = Color(0xFFF5EBD3)

/** Colors the Material 2 palette has no slot for. */
@Immutable
data class QuranicColors(
    val gold: Color,
    val border: Color,
    val muted: Color,
    val heroTop: Color,
    val heroBottom: Color,
    val onHero: Color,
    val onHeroMuted: Color,
)

val LightQuranicColors = QuranicColors(
    gold = GoldDeep,
    border = Color(0xFFE9DFC8),
    muted = Color(0xFF6E7570),
    heroTop = Emerald700,
    heroBottom = Emerald900,
    onHero = Parchment,
    onHeroMuted = Parchment.copy(alpha = 0.7f),
)

val DarkQuranicColors = QuranicColors(
    gold = Gold,
    border = Color(0xFF1F3A32),
    muted = Color(0xFF93A39C),
    heroTop = Color(0xFF0D3F34),
    heroBottom = Color(0xFF0B2A23),
    onHero = Parchment,
    onHeroMuted = Parchment.copy(alpha = 0.66f),
)

val LightPalette = lightColors(
    primary = Emerald600,
    primaryVariant = Emerald900,
    secondary = GoldDeep,
    background = Ivory,
    surface = IvoryCard,
    onPrimary = Parchment,
    onSecondary = Color.White,
    onBackground = Color(0xFF1B2320),
    onSurface = Color(0xFF1B2320),
)

val DarkPalette = darkColors(
    primary = Emerald300,
    primaryVariant = Emerald600,
    secondary = Gold,
    background = Emerald950,
    surface = Color(0xFF0E241E),
    onPrimary = Emerald950,
    onSecondary = Emerald950,
    onBackground = Color(0xFFEDE6D6),
    onSurface = Color(0xFFEDE6D6),
)
