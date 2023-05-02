package com.petition.petition.model.payload.petitionAnswer.response

import com.petition.petition.model.entity.User

data class PetitionAnswerResponseDto(
    val petitionAnswerId:Int = 0,
    val petitionAnswerTitle: String = "",
    val petitionAnswerContent: String = "",
    val petitionAnswerImage: String? = null,
    val user: User? = null,
    val petitionId: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = "",
)