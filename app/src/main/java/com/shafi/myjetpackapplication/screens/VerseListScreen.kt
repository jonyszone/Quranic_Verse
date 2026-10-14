package com.shafi.myjetpackapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shafi.myjetpackapplication.models.Verses
import com.shafi.myjetpackapplication.models.pluralize
import com.shafi.myjetpackapplication.ui.components.GoldDivider
import com.shafi.myjetpackapplication.ui.components.StatusBarScrim
import com.shafi.myjetpackapplication.ui.components.heroBackground
import com.shafi.myjetpackapplication.ui.theme.ArabicTextStyle
import com.shafi.myjetpackapplication.ui.theme.QuranicTheme

private const val BISMILLAH = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ"

@Composable
fun VerseListScreen(data: Array<Verses>, listState: LazyListState, onClick: (verse: Verses) -> Unit) {
    // groupBy keeps the order in which surahs first appear in the data.
    val surahs = remember(data) { data.groupBy { it.sura } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        VerseList(
            surahs = surahs,
            listState = listState,
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            header = { ListHero(verseCount = data.size, surahCount = surahs.size) }
        )
        StatusBarScrim()
    }
}

@Composable
private fun ListHero(verseCount: Int, surahCount: Int) {
    val colors = QuranicTheme.colors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
            .heroBackground(colors)
            .statusBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 40.dp)
    ) {
        Text(
            text = "THE NOBLE QUR'AN",
            style = MaterialTheme.typography.overline,
            color = colors.gold
        )
        Spacer(Modifier.height(18.dp))
        Text(
            text = BISMILLAH,
            style = ArabicTextStyle.copy(fontSize = 30.sp, lineHeight = 58.sp, textAlign = TextAlign.Center),
            color = colors.onHero
        )
        Spacer(Modifier.height(14.dp))
        GoldDivider(Modifier.width(150.dp), colors.gold)
        Spacer(Modifier.height(18.dp))
        Text(
            text = "Quranic Verses",
            style = MaterialTheme.typography.h4,
            color = colors.onHero
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "${pluralize(verseCount, "verse")}  ·  ${pluralize(surahCount, "surah")}",
            style = MaterialTheme.typography.caption,
            color = colors.onHeroMuted
        )
    }
}
