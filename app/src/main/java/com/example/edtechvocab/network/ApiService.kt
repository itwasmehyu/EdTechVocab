package com.example.edtechvocab.network

import com.example.edtechvocab.model.LoginRequest
import com.example.edtechvocab.model.LoginResponse
import com.example.edtechvocab.model.VocabResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/v1/widget/daily")
    suspend fun getDailyVocab(
        @Header("Authorization") token: String,
        @Query("userId") userId: Long
    ): Response<VocabResponse>
}