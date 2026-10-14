package com.shafi.myjetpackapplication.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shafi.myjetpackapplication.DataManager
import com.shafi.myjetpackapplication.models.Footnote
import com.shafi.myjetpackapplication.models.Verses
import com.shafi.myjetpackapplication.models.parseFootnotes
import com.shafi.myjetpackapplication.models.surahName
import com.shafi.myjetpackapplication.models.translationSegments
import com.shafi.myjetpackapplication.ui.components.AyahMedallion
import com.shafi.myjetpackapplication.ui.components.GoldDivider
import com.shafi.myjetpackapplication.ui.components.StatusBarScrim
import com.shafi.myjetpackapplication.ui.components.heroBackground
import com.shafi.myjetpackapplication.ui.theme.ArabicTextStyle
import com.shafi.myjetpackapplication.ui.theme.Inter
import com.shafi.myjetpackapplication.ui.theme.QuranicTheme

@Composable
fun VerseListItemDetails(verse: Verses) {

    BackHandler {
        DataManager.switchPages(null)
    }

    val colors = QuranicTheme.colors
    val footnotes = remember(verse.footnotes) { parseFootnotes(verse.footnotes) }
    val translation = remember(verse.translation, colors.gold) {
        annotatedTranslation(verse.translation, colors.gold)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            VerseHero(verse)

            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp)) {
                SectionLabel("TRANSLATION")
                Text(
                    text = translation,
                    style = MaterialTheme.typography.body1.copy(fontSize = 23.sp, lineHeight = 33.sp),
                    color = MaterialTheme.colors.onBackground
                )

                if (footnotes.isNotEmpty()) {
                    Spacer(Modifier.height(36.dp))
                    SectionLabel("NOTES")
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        footnotes.forEach { FootnoteCard(it) }
                    }
                }

                Spacer(
                    Modifier
                        .navigationBarsPadding()
                        .height(40.dp)
                )
            }
        }
        StatusBarScrim()
    }
}

@Composable
private fun VerseHero(verse: Verses) {
    val colors = QuranicTheme.colors
    val surahNumber = verse.sura.toIntOrNull() ?: 0

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
            .heroBackground(colors)
            .statusBarsPadding()
            .padding(bottom = 40.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = { DataManager.switchPages(null) }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = colors.onHero,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(colors.onHero.copy(alpha = 0.1f))
                        .padding(8.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = surahName(surahNumber),
                    style = MaterialTheme.typography.h6,
                    color = colors.onHero
                )
                Text(
                    text = "SURAH ${verse.sura}  ·  AYAH ${verse.aya}",
                    style = MaterialTheme.typography.overline,
                    color = colors.gold
                )
            }
            // Mirrors the back button so the title stays centred.
            Spacer(Modifier.size(48.dp))
        }

        Spacer(Modifier.height(24.dp))
        AyahMedallion(
            number = verse.aya.toIntOrNull() ?: 0,
            diameter = 64.dp,
            color = colors.gold,
            textColor = colors.onHero
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = verse.arabic_text,
            style = ArabicTextStyle.copy(fontSize = 34.sp, lineHeight = 66.sp, textAlign = TextAlign.Center),
            color = colors.onHero,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
        )
        Spacer(Modifier.height(24.dp))
        GoldDivider(Modifier.width(160.dp), colors.gold)
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.overline,
        color = QuranicTheme.colors.gold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
private fun FootnoteCard(footnote: Footnote) {
    val colors = QuranicTheme.colors

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        border = BorderStroke(1.dp, colors.border),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(18.dp)) {
            if (footnote.number.isNotEmpty()) {
                Text(
                    text = footnote.number,
                    style = MaterialTheme.typography.caption,
                    color = colors.gold,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(colors.gold.copy(alpha = 0.12f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                )
                Spacer(Modifier.width(14.dp))
            }
            Text(
                text = footnote.text,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}

/** Renders footnote markers as small gold superscripts. */
private fun annotatedTranslation(translation: String, markerColor: Color): AnnotatedString =
    buildAnnotatedString {
        translationSegments(translation).forEach { segment ->
            if (segment.isMarker) {
                withStyle(
                    SpanStyle(
                        color = markerColor,
                        fontFamily = Inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        baselineShift = BaselineShift.Superscript
                    )
                ) { append(segment.text) }
            } else {
                append(segment.text)
            }
        }
    }
