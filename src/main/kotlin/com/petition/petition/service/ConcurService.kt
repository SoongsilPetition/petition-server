package com.petition.petition.service

import com.petition.petition.model.entity.AgreementStatus
import com.petition.petition.model.entity.Concur
import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.User
import com.petition.petition.model.payload.concur.request.ConcurWriteRequestDto
import com.petition.petition.repository.ConcurRepository
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
    ): Concur {
        val user: User? = userService.getValidUser(jwt)
        val petition: Petition = petitionService.getPetition(body.petitionId)

        //Q Agreement클래스를 변환해야하는지 고민
        val concur = Concur(
            concurContent = body.concurContents,
            user = user,
            petition= petition,
            agreementStatus = AgreementStatus.valueOf(body.agreementStatus)
        )
        return concurRepository.save(concur)
    }
}