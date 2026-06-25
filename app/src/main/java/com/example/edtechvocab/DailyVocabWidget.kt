package com.example.edtechvocab

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.compose.ui.graphics.Color

class DailyVocabWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val word       = "Persistent"
        val pos        = "adjective"
        val phonetic   = "/pəˈsɪs.tənt/"
        val definition = "Cố chấp, dai dẳng, liên tục."

        provideContent {
            GlanceWidgetLayout(word, pos, phonetic, definition)
        }
    }

    @Composable
    private fun GlanceWidgetLayout(
        word: String,
        pos: String,
        phonetic: String,
        definition: String
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color(0xFFD0BCFF)),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = GlanceModifier.fillMaxWidth()) {
                Text(
                    text = word,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize   = 18.sp
                    )
                )
                Spacer(modifier = GlanceModifier.width(6.dp))
                Text(
                    text  = "($pos)",
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }

            Text(
                text     = phonetic,
                style    = TextStyle(
                    fontSize = 13.sp
                ),
                modifier = GlanceModifier.padding(vertical = 2.dp)
            )

            Spacer(modifier = GlanceModifier.height(4.dp))

            Text(
                text     = definition,
                style    = TextStyle(
                    fontSize = 14.sp
                ),
                modifier = GlanceModifier.padding(top = 6.dp)
            )
        }
    }
}