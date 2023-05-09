package com.petition.petition.model.payload.petition.response

import com.petition.petition.model.payload.auth.response.UserResponseDto
import com.petition.petition.model.payload.petitionCategory.response.PetitionCategoryResponseDto

data class PetitionResponseDto (
    val petitionId: Int = 0,
    val petitionTitle: String = "",
    val petitionContent: String = "",
    val petitionImage: String? = "",
    val petitionCategory: List<PetitionCategoryResponseDto>? = null,
    val petitionAgreement: Int = 0,
    val petitionDisagreement: Int = 0,
    val user: UserResponseDto? = null,
    val createdAt: String = "",
    val updatedAt: String = "",
)