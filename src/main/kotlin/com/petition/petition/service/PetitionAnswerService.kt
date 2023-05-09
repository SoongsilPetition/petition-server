package com.petition.petition.service

import com.petition.petition.model.entity.PetitionAnswer
import com.petition.petition.model.payload.auth.response.UserResponseDto
import com.petition.petition.model.payload.petitionAnswer.request.PetitionAnswerWriteRequestDto
import com.petition.petition.model.payload.petitionAnswer.response.PetitionAnswerResponseDto
import com.petition.petition.repository.PetitionAnswerRepository
import org.springframework.stereotype.Service

@Service
class PetitionAnswerService(
    private val petitionAnswerRepository: PetitionAnswerRepository,
    private val petitionService: PetitionService,
    private val userService: UserService
) {
    fun savePetitionAnswer(body: PetitionAnswerWriteRequestDto, jwt: String) : PetitionAnswerResponseDto {
        //TODO: 이미 청원 답변이 작성되었다면 게시글을 작성하지 못하게 하는 로직 필요

        val findPetition = petitionService.getValidatedPetition(body.petitionId)
        val findUser = userService.getValidUser(jwt)
        val petitionAnswer = PetitionAnswer(
            petitionAnswerTitle = body.petitionAnswerTitle,
            petitionAnswerContent = body.petitionAnswerContent,
            petitionAnswerImage = body.petitionAnswerImage,
            petition = findPetition,
            user = findUser
        )
        petitionAnswerRepository.save(petitionAnswer)
        val userResponse = UserResponseDto(
            userId = findUser?.userId,
            name = findUser?.name,
            email = findUser?.email,
            createdAt = findUser?.createdAt.toString(),
            updatedAt = findUser?.updatedAt.toString()
        )
        val response = PetitionAnswerResponseDto(
            petitionAnswerId = petitionAnswer.petitionAnswerId,
            petitionAnswerTitle = petitionAnswer.petitionAnswerTitle,
            petitionAnswerContent = petitionAnswer.petitionAnswerContent,
            petitionAnswerImage = petitionAnswer.petitionAnswerImage,
            user = userResponse,
            petitionId = petitionAnswer.petition.petitionId,
            createdAt = petitionAnswer.createdAt.toString(),
            updatedAt = petitionAnswer.updatedAt.toString()
        )
        return response
    }

    fun getPetitionAnswer(petitionId: Int): PetitionAnswerResponseDto {
        val petitionAnswer = petitionAnswerRepository.findByPetition_PetitionId(petitionId)
        val userResponse = UserResponseDto(
            userId = petitionAnswer.users?.userId,
            name = petitionAnswer.users?.name,
            email = petitionAnswer.users?.email,
            createdAt = petitionAnswer.users?.createdAt.toString(),
            updatedAt = petitionAnswer.users?.updatedAt.toString()
        )
        val response: PetitionAnswerResponseDto = PetitionAnswerResponseDto(
            petitionAnswerId = petitionAnswer.petitionAnswerId,
            petitionAnswerTitle = petitionAnswer.petitionAnswerTitle,
            petitionAnswerContent = petitionAnswer.petitionAnswerContent,
            petitionAnswerImage = petitionAnswer.petitionAnswerImage,
            user = userResponse,
            petitionId = petitionAnswer.petition.petitionId,
            createdAt = petitionAnswer.createdAt.toString(),
            updatedAt = petitionAnswer.updatedAt.toString()
        )
        return response
    }

    fun updatePetitionAnswer(petitionId: Int, body: PetitionAnswerWriteRequestDto, jwt: String): PetitionAnswerResponseDto {
        val findPetitionAnswer = getValidPetitionAnswer(petitionId)
        val findUser = userService.getValidUser(jwt)
        if(findUser?.userId != findPetitionAnswer.users?.userId) {
            throw Exception("해당 청원에 대한 답변을 수정할 권한이 없습니다.")
        }else{
            findPetitionAnswer.petitionAnswerTitle = body.petitionAnswerTitle
            findPetitionAnswer.petitionAnswerContent = body.petitionAnswerContent
            findPetitionAnswer.petitionAnswerImage = body.petitionAnswerImage
        }
        petitionAnswerRepository.save(findPetitionAnswer)
        val userResponse = UserResponseDto(
            userId = findUser?.userId,
            name = findUser?.name,
            email = findUser?.email,
            createdAt = findUser?.createdAt.toString(),
            updatedAt = findUser?.updatedAt.toString()
        )


        val response = PetitionAnswerResponseDto(
            petitionAnswerId = findPetitionAnswer.petitionAnswerId,
            petitionAnswerTitle = findPetitionAnswer.petitionAnswerTitle,
            petitionAnswerContent = findPetitionAnswer.petitionAnswerContent,
            petitionAnswerImage = findPetitionAnswer.petitionAnswerImage,
            user = userResponse,
            petitionId = findPetitionAnswer.petition.petitionId,
            createdAt = findPetitionAnswer.createdAt.toString(),
            updatedAt = findPetitionAnswer.updatedAt.toString()
        )
        return response
    }

    fun deletePetitionAnswer(petitionId: Int, jwt: String) {
        val findUser = userService.getValidUser(jwt)
        val findPetitionAnswer = getValidPetitionAnswer(petitionId)
        if(findUser?.userId != findPetitionAnswer.users?.userId) {
            throw Exception("해당 청원에 대한 답변을 삭제할 권한이 없습니다.")
        }else{
            petitionAnswerRepository.delete(findPetitionAnswer)
        }
    }


    fun getValidPetitionAnswer(petitionId: Int): PetitionAnswer {
        val petitionAnswer = petitionAnswerRepository.findByPetition_PetitionId(petitionId)
        if (petitionAnswer == null) {
            throw Exception("해당 청원에 대한 답변이 존재하지 않습니다.")
        }
        return petitionAnswer
    }
}