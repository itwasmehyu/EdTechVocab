package com.example.edtechvocab

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.edit
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.example.edtechvocab.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyVocabWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DailyVocabWidget()

    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    // Hàm tự động kích hoạt khi Widget nhận tín hiệu update từ Android hệ thống
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        // Chạy bất đồng bộ ngầm để gọi API không làm đơ máy điện thoại
        repositoryScope.launch {
            val sharedPrefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            val token = sharedPrefs.getString("jwt_token", null)

            if (token != null) {
                try {
                    // Gọi API chọc sang Docker Backend xịn của bạn (UserId giả định là 1, đính kèm token Bearer)
                    val response = RetrofitClient.instance.getDailyVocab("Bearer $token", 1L)

                    if (response.isSuccessful && response.body() != null) {
                        val vocab = response.body()!!

                        // Lấy danh sách toàn bộ Widget đang ghim ngoài màn hình
                        val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(DailyVocabWidget::class.java)

                        for (glanceId in glanceIds) {
                            // Nạp đè dữ liệu SRS thật từ DB Postgres vào bộ nhớ đệm của từng Widget
                            updateAppWidgetState(context, glanceId) { prefs ->
                                prefs[DailyVocabWidget.KEY_WORD] = vocab.word
                                prefs[DailyVocabWidget.KEY_POS] = vocab.partOfSpeech ?: "adjective"
                                prefs[DailyVocabWidget.KEY_PHONETIC] = vocab.phonetic
                                prefs[DailyVocabWidget.KEY_DEFINITION_VI] = vocab.definitionVi
                                prefs[DailyVocabWidget.KEY_DEFINITION_EN] = vocab.definitionEn
                            }
                            // Ra lệnh cho Widget vẽ lại giao diện mới tức thì
                            glanceAppWidget.update(context, glanceId)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace() // Ghi log lỗi nếu không kết nối được Docker
                }
            }
        }
    }
}