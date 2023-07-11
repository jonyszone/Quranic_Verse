package com.shafi.myjetpackapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shafi.myjetpackapplication.models.Verses

@Composable
fun VerseListScreen(data: Array<Verses>, onClick: (verse: Verses) -> Unit) {

    Column {
        Text(
            text = "Quranic Verses",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp, 24.dp)
                .fillMaxWidth(1f),
            style = MaterialTheme.typography.h5,
            fontFamily = FontFamily.Monospace
        )
        VerseList(data = data, onClick)
    }
}