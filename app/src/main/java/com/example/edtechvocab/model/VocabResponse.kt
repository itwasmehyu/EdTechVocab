package com.example.edtechvocab.model

data class VocabResponse(
    val id: Long,
    val word: String,
    val partOfSpeech: String,
    val phonetic: String,
    val definitionEn: String,
    val definitionVi: String,
    val exampleEn: String,
    val exampleVi: String
)