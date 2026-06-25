package com.example.edtechvocab.model

data class VocabResponse(
    val id: Long,
    val word: String,
    val pos: String,
    val definition: String,
    val example: String,
    val phonetic: String
)