package com.example.edtechvocab

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                        // Nếu chưa đăng nhập -> Hiện màn hình Login
                        LoginScreen(onLoginSuccess = { newToken ->
                            sharedPreferences.edit().putString("jwt_token", newToken).apply()
                            // Ép app vẽ lại màn hình sau khi lưu token thành công
                            recreate()
                        })
                    } else {
                        // Nếu đã đăng nhập thành công -> Hiện màn hình chính của App
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Chào mừng bạn! Token của bạn đã được bảo mật.", style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}