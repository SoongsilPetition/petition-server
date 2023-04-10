package com.petition.petition.model.payload.petition.request

import com.petition.petition.model.payload.petitionCategory.request.PetitionCategoryRequestDto

class PetitionWriteRequestDto {
    val petitionTitle: String = ""
    val petitionContent: String = ""
    val petitionImage: String = ""
    val petitionCategory: List<PetitionCategoryRequestDto>? = null
}