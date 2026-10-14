package com.shafi.myjetpackapplication.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import com.shafi.myjetpackapplication.R

val Amiri = FontFamily(Font(R.font.amiri_regular))

val Cormorant = FontFamily(
    Font(R.font.cormorant_medium, FontWeight.Medium),
    Font(R.font.cormorant_semibold, FontWeight.SemiBold),
)

val Inter = FontFamily(Font(R.font.inter_semibold, FontWeight.SemiBold))

val QuranicTypography = Typography(
    h4 = TextStyle(fontFamily = Cormorant, fontWeight = FontWeight.SemiBold, fontSize = 38.sp, lineHeight = 42.sp),
    h5 = TextStyle(fontFamily = Cormorant, fontWeight = FontWeight.SemiBold, fontSize = 26.sp, lineHeight = 30.sp),
    h6 = TextStyle(fontFamily = Cormorant, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 26.sp),
    subtitle1 = TextStyle(fontFamily = Cormorant, fontWeight = FontWeight.Medium, fontSize = 19.sp, lineHeight = 26.sp),
    body1 = TextStyle(fontFamily = Cormorant, fontWeight = FontWeight.Medium, fontSize = 19.sp, lineHeight = 27.sp),
    body2 = TextStyle(fontFamily = Cormorant, fontWeight = FontWeight.Medium, fontSize = 17.sp, lineHeight = 24.sp),
    button = TextStyle(fontFamily = Inter, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, letterSpacing = 1.2.sp),
    caption = TextStyle(fontFamily = Inter, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.4.sp),
    overline = TextStyle(fontFamily = Inter, fontWeight = FontWeight.SemiBold, fontSize = 10.sp, lineHeight = 14.sp, letterSpacing = 2.sp),
)

/** Quranic script needs generous line height so stacked diacritics never collide. */
val ArabicTextStyle = TextStyle(
    fontFamily = Amiri,
    fontSize = 26.sp,
    lineHeight = 52.sp,
    textDirection = TextDirection.Rtl,
)
