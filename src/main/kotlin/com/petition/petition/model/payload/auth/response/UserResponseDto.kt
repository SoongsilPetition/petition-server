package com.petition.petition.model.payload.auth.response

data class UserResponseDto(
    val userId: Int,
    val name: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)