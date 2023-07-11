package com.shafi.myjetpackapplication.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shafi.myjetpackapplication.DataManager
import com.shafi.myjetpackapplication.R
import com.shafi.myjetpackapplication.models.Verses

@Composable
fun VerseListItemDetails(verse: Verses) {

    BackHandler {
        DataManager.switchPages(null)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(
                Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFFffffff),
                        Color(0xFFe3e3e3)
                    )
                )
            )

    ) {
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .padding(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                //modifier = Modifier.padding(15.dp)
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(16.dp, 24.dp)

            ) {
                Image(
                    imageVector = Icons.Filled.FormatQuote,
                    contentDescription = "verse",
                    modifier = Modifier
                        .size(80.dp)
                        .rotate(180F)
                )


                Text(
                    text = verse.arabic_text,
                    style = MaterialTheme.typography.body1,
                    //modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    fontFamily = FontFamily(Font(R.font.montserrat))
                )
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFFFFF))
                        .fillMaxWidth(.4f)
                        .height(1.dp)
                )
                Spacer(modifier = Modifier.padding(14.dp))
                Text(
                    text = verse.translation,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Thin,
                    fontFamily = FontFamily(Font(R.font.montserrat_thin)),
                    modifier = Modifier.padding(top = 4.dp)
                )

            }


        }


    }
}