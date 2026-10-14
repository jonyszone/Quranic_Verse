package com.shafi.myjetpackapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shafi.myjetpackapplication.models.Verses
import com.shafi.myjetpackapplication.models.parseFootnotes
import com.shafi.myjetpackapplication.models.plainTranslation
import com.shafi.myjetpackapplication.models.pluralize
import com.shafi.myjetpackapplication.ui.components.AyahMedallion
import com.shafi.myjetpackapplication.ui.components.GoldDivider
import com.shafi.myjetpackapplication.ui.theme.ArabicTextStyle
import com.shafi.myjetpackapplication.ui.theme.QuranicTheme

@Composable
fun VerseListItem(verse: Verses, onClick: (verse: Verses) -> Unit) {
    val colors = QuranicTheme.colors
    val shape = MaterialTheme.shapes.medium
    val translation = remember(verse.translation) { plainTranslation(verse.translation) }
    val noteCount = remember(verse.footnotes) { parseFootnotes(verse.footnotes).size }

    Card(
        shape = shape,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surface,
        border = BorderStroke(1.dp, colors.border),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(shape)
            .clickable { onClick(verse) }
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AyahMedallion(number = verse.aya.toIntOrNull() ?: 0, diameter = 34.dp)
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "AYAH ${verse.aya}",
                    style = MaterialTheme.typography.overline,
                    color = colors.muted,
                    modifier = Modifier.weight(1f)
                )
                if (noteCount > 0) {
                    Text(
                        text = pluralize(noteCount, "note"),
                        style = MaterialTheme.typography.caption,
                        color = colors.gold,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(colors.gold.copy(alpha = 0.12f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = null,
                    tint = colors.muted
                )
            }

            Spacer(Modifier.height(14.dp))
            Text(
                text = verse.arabic_text,
                style = ArabicTextStyle.copy(textAlign = TextAlign.Right),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            GoldDivider(Modifier.fillMaxWidth(), colors.gold.copy(alpha = 0.55f))
            Spacer(Modifier.height(12.dp))
            Text(
                text = translation,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.82f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
