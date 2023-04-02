package com.petition.petition.model.payload.concur.response

data class ConcurResponseDto(
    val id: Int,
    val userId: Int,
    val userName: String,
    val agreementStatus: String,
    val createdAt: String,
    val updatedAt: String
)