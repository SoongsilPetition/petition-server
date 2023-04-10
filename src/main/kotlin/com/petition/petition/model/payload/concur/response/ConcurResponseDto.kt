package com.petition.petition.model.payload.concur.response

import com.petition.petition.model.payload.auth.response.UserResponseDto

data class ConcurResponseDto(
    val concurId: Int,
    val concurContent: String,
    val agreementStatus: String,
    val createdAt: String,
    val updatedAt: String,
    val user: UserResponseDto
)