package com.shafi.myjetpackapplication.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.shafi.myjetpackapplication.models.Verses

@Composable
fun VerseList(data: Array<Verses>, onClick: (verse: Verses) -> Unit) {

    LazyColumn(content = {
        items(data) {
            VerseListItem(verse = it, onClick)
        }
    })
}