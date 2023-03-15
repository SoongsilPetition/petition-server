package com.petition.petition.service

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.User
import com.petition.petition.model.payload.petition.request.PetitionWriteRequestDto
import com.petition.petition.repository.PetitionRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PetitionService(
    private val petitionRepository: PetitionRepository,
    private val userService: UserService,
) {
    fun savePetition(body: PetitionWriteRequestDto, jwt:String): Petition {
        val user: User? = userService.getValidUser(jwt)
        val petition = Petition(
            petitionTitle = body.petitionTitle,
            petitionContent = body.petitionContent,
            users = user
        )

        //category를 forEach 반복문을 이용하여 작성
        return petitionRepository.save(petition)
    }

}