package com.petition.petition.service

import com.petition.petition.model.entity.AgreementStatus
import com.petition.petition.model.entity.Concur
import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.User
import com.petition.petition.model.payload.concur.request.ConcurWriteRequestDto
import com.petition.petition.repository.ConcurRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConcurService(
    private val concurRepository: ConcurRepository,
    private val userService: UserService,
    private val petitionService: PetitionService
) {
    //TODO: 청원 작성시, 청원 작성자는 자동으로 동의하도록
    fun saveConcur(
        body: ConcurWriteRequestDto,
        jwt: String
    ): Concur {
        val user: User? = userService.getValidUser(jwt)
        val petition: Petition = petitionService.getPetition(body.petitionId)

        //TODO: Agreement클래스를 변환해야하는지 고민
        val concur = Concur(
            concurContent = body.concurContents,
            user = user,
            petition = petition,
            agreementStatus = AgreementStatus.valueOf(body.agreementStatus)
        )
        return concurRepository.save(concur)
    }

    fun getConcursList(petitionId: Int, page: Int, size: Int, agreementStatus: AgreementStatus): List<Concur>? {
        val pageRequest: Pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "createdAt")
        val petition: Petition = petitionService.getPetition(petitionId)
        val concurs: Page<Concur> =
            concurRepository.findAllByPetitionAndAgreementStatus(petition, agreementStatus, pageRequest)
        return concurs.content
    }

    @Transactional
    fun aggregateConcur(petitionId: Int) {
        val count: Int = concurRepository.countByPetitionAndAgreementStatus(petitionId, AgreementStatus.AGREE)
    }
}