package com.shafi.myjetpackapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shafi.myjetpackapplication.models.Verses
import com.shafi.myjetpackapplication.models.pluralize
import com.shafi.myjetpackapplication.models.surahName
import com.shafi.myjetpackapplication.ui.components.AyahMedallion
import com.shafi.myjetpackapplication.ui.theme.QuranicTheme

@Composable
fun VerseList(
    surahs: Map<String, List<Verses>>,
    listState: LazyListState,
    onClick: (verse: Verses) -> Unit,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
) {
    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
    ) {
        item(key = "header") { header() }
        surahs.forEach { (sura, verses) ->
            item(key = "surah-$sura") {
                SurahHeader(number = sura.toIntOrNull() ?: 0, verseCount = verses.size)
            }
            items(verses, key = { it.id }) {
                VerseListItem(verse = it, onClick)
            }
        }
        item(key = "footer") { Spacer(Modifier.padding(bottom = 24.dp)) }
    }
}

@Composable
private fun SurahHeader(number: Int, verseCount: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 10.dp)
    ) {
        AyahMedallion(number = number, diameter = 46.dp)
        Spacer(Modifier.width(14.dp))
        Column {
            Text(
                text = surahName(number),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "SURAH $number  ·  ${pluralize(verseCount, "VERSE", "VERSES")}",
                style = MaterialTheme.typography.overline,
                color = QuranicTheme.colors.muted
            )
        }
    }
}
