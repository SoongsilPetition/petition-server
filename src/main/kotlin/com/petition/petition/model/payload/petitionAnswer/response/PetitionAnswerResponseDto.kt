package com.petition.petition.model.payload.petitionAnswer.response

import com.petition.petition.model.entity.User
import com.petition.petition.model.payload.auth.response.UserResponseDto

data class PetitionAnswerResponseDto(
    val petitionAnswerId:Int = 0,
    val petitionAnswerTitle: String = "",
    val petitionAnswerContent: String = "",
    val petitionAnswerImage: String? = null,
    val user: UserResponseDto,
    val petitionId: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = "",
)