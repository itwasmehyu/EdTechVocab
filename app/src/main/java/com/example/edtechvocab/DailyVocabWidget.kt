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
import androidx.glance.currentState
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

class DailyVocabWidget : GlanceAppWidget() {

    // TUYỆT CHIÊU: Xóa hoàn toàn dòng override stateDefinition.
    // Jetpack Glance sẽ tự động dùng Preferences DATASTORE ngầm định mà không cần gọi tên Class lỗi kia ra nữa.

    // Định nghĩa các Key lưu trữ từ vựng động
    companion object {
        val KEY_WORD = stringPreferencesKey("widget_word")
        val KEY_POS = stringPreferencesKey("widget_pos")
        val KEY_PHONETIC = stringPreferencesKey("widget_phonetic")
        val KEY_DEFINITION = stringPreferencesKey("widget_definition")
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            // Đọc dữ liệu động được Receiver nạp vào, nếu trống thì hiện chữ mặc định
            val prefs = currentState<Preferences>()
            val word = prefs[KEY_WORD] ?: "Học từ vựng"
            val pos = prefs[KEY_POS] ?: "SRS"
            val phonetic = prefs[KEY_PHONETIC] ?: "Bấm để cập nhật"
            val definition = prefs[KEY_DEFINITION] ?: "Đăng nhập app để đồng bộ từ vựng hàng ngày theo thuật toán."

            GlanceWidgetLayout(word, pos, phonetic, definition)
        }
    }

    @Composable
    private fun GlanceWidgetLayout(word: String, pos: String, phonetic: String, definition: String) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color(0xFFEFB8C8)),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = GlanceModifier.fillMaxWidth()) {
                Text(
                    text = word,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
                Spacer(modifier = GlanceModifier.width(6.dp))
                Text(
                    text = "($pos)",
                    style = TextStyle(fontSize = 12.sp)
                )
            }

            Text(
                text = phonetic,
                style = TextStyle(fontSize = 13.sp),
                modifier = GlanceModifier.padding(vertical = 2.dp)
            )

            Spacer(modifier = GlanceModifier.height(4.dp))

            Text(
                text = definition,
                style = TextStyle(fontSize = 14.sp),
                modifier = GlanceModifier.padding(top = 6.dp)
            )
        }
    }
}