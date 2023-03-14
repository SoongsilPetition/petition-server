package com.petition.petition.service

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.payload.petition.request.PetitionWriteRequestDto
import com.petition.petition.repository.PetitionRepository
import org.springframework.stereotype.Service

@Service
class PetitionService(
    private val petitionRepository: PetitionRepository
) {
    fun savePetition(body: PetitionWriteRequestDto): PetitionResponseDto {
        val petition = Petition(
            petitionTitle = body.title,
            petitionContent = body.content,

        )

        //category를 forEach 반복문을 이용하여 작성
        return petitionRepository.save(petition)
    }

}