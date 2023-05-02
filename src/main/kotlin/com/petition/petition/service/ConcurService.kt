package com.petition.petition.service

import com.petition.petition.model.entity.AgreementStatus
import com.petition.petition.model.entity.Concur
import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.User
import com.petition.petition.model.payload.auth.response.UserResponseDto
import com.petition.petition.model.payload.concur.request.ConcurWriteRequestDto
import com.petition.petition.model.payload.concur.response.ConcurResponseDto
import com.petition.petition.repository.ConcurRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ConcurService(
    private val concurRepository: ConcurRepository,
    private val userService: UserService,
    private val petitionService: PetitionService
) {
    fun saveConcur(
        body: ConcurWriteRequestDto,
        jwt: String
    ): ConcurResponseDto {
        val user: User? = userService.getValidUser(jwt)
        val petition: Petition = petitionService.getValidatedPetition(body.petitionId)

        val concur = Concur(
            concurContent = body.concurContents,
            user = user,
            petition= petition,
            agreementStatus = AgreementStatus.valueOf(body.agreementStatus)
        )
        concurRepository.save(concur)
        //TODO: 청원 동의/비동의 여부에 따라 petition테이블에 저장된 동의/비동의 수 변경

        if(concur.agreementStatus == AgreementStatus.AGREE){
            petition.agreeCount += 1
        }else{
            petition.disagreeCount += 1
        }
        petitionService.updatePetition(petition)

        return ConcurResponseDto(
            concurId = concur.concurId,
            concurContent = concur.concurContent,
            agreementStatus = concur.agreementStatus.toString(),
            createdAt = concur.createdAt.toString(),
            updatedAt = concur.updatedAt.toString(),
            user = UserResponseDto(
                userId = user?.userId,
                name = user?.name,
                email = user?.email,
                createdAt = user?.createdAt,
                updatedAt = user?.updatedAt
            )
        )
    }

    fun getConcursList(petitionId: Int, page: Int, size: Int, agreementStatus: AgreementStatus): List<ConcurResponseDto>? {
        val pageRequest: Pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "createdAt")
        val petition:Petition = petitionService.getValidatedPetition(petitionId)
        val concurs: Page<Concur> = concurRepository.findAllByPetitionAndAgreementStatus(petition, agreementStatus,pageRequest)
        return concurs.content.map {
            ConcurResponseDto(
                concurId = it.concurId,
                concurContent = it.concurContent,
                agreementStatus = it.agreementStatus.toString(),
                createdAt = it.createdAt.toString(),
                updatedAt = it.updatedAt.toString(),
                user = UserResponseDto(
                    userId = it.user?.userId,
                    name = it.user?.name,
                    email = it.user?.email,
                    createdAt = it.user?.createdAt,
                    updatedAt = it.user?.updatedAt
                )
            )
        }
    }


    fun getUserConcursList(userId:Int,page: Int,size: Int):List<ConcurResponseDto>{
        val pageRequest: Pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "createdAt")
        val findConcurs = concurRepository.findAllByUser_UserId(userId,pageRequest)
        return findConcurs.content.map {
            ConcurResponseDto(
                concurId = it.concurId,
                concurContent = it.concurContent,
                agreementStatus = it.agreementStatus.toString(),
                createdAt = it.createdAt.toString(),
                updatedAt = it.updatedAt.toString(),
                user = UserResponseDto(
                    userId = it.user?.userId,
                    name = it.user?.name,
                    email = it.user?.email,
                    createdAt = it.user?.createdAt,
                    updatedAt = it.user?.updatedAt
                )
            )
        }
    }
}