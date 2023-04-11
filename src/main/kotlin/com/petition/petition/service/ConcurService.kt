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
        val petition: Petition = petitionService.getPetition(body.petitionId)

        //TODO: Agreement클래스를 변환해야하는지 고민
        val concur = Concur(
            concurContent = body.concurContents,
            user = user,
            petition= petition,
            agreementStatus = AgreementStatus.valueOf(body.agreementStatus)
        )
        concurRepository.save(concur)

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
                createdAt = user?.createdAt.toString(),
                updatedAt = user?.updatedAt.toString()
            )
        )
    }

    fun getConcursList(petitionId: Int, page: Int, size: Int, agreementStatus: AgreementStatus): List<ConcurResponseDto>? {
        val pageRequest: Pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "createdAt")
        val petition:Petition = petitionService.getPetition(petitionId)
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
                    createdAt = it.user?.createdAt.toString(),
                    updatedAt = it.user?.updatedAt.toString()
                )
            )
        }
    }



}