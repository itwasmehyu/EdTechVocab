package com.example.edtechvocab

import android.content.Context
import android.content.Intent
import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edtechvocab.screen.LoginScreen
import com.example.edtechvocab.ui.theme.EdTechVocabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdTechVocabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sharedPreferences = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    var token = sharedPreferences.getString("jwt_token", null)

                    if (token == null) {
                        LoginScreen(onLoginSuccess = { newToken ->
                            sharedPreferences.edit().putString("jwt_token", newToken).apply()

                            // Phát tín hiệu ép Widget cập nhật ngay khi vừa Login
                            val intent = Intent(this, DailyVocabWidgetReceiver::class.java).apply {
                                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                            }
                            sendBroadcast(intent)

                            recreate()
                        })
                    } else {
                        // SỬA BLOCK NÀY: Thêm nút Đăng xuất trực quan
                        Column(
                            modifier = Modifier.fillMaxSize().padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Chào mừng bạn!",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Token của bạn đã được lưu trữ bảo mật.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 32.dp)
                            )

                            Button(
                                onClick = {
                                    // Xóa sạch Token khỏi bộ nhớ máy
                                    sharedPreferences.edit().remove("jwt_token").apply()

                                    // Báo cho app vẽ lại giao diện (sẽ tự nhảy về màn Login)
                                    recreate()
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                            ) {
                                Text("Đăng xuất tài khoản")
                            }
                        }
                    }
                }
            }
        }
    }
}