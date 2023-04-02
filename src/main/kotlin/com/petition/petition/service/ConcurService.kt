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
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConcurService(
    private val concurRepository: ConcurRepository,
    private val userService: UserService,
    private val petitionService: PetitionService,
    private val kafkaProducer: KafkaProducer,
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun concur(body: ConcurWriteRequestDto, jwt: String) {
        val user: User? = userService.getValidUser(jwt)
        if (user != null) {
            kafkaProducer.sendConcurEvent(body.petitionId.toString(), user.userId.toString() ,body.concurContents,body.agreementStatus)
        }
    }

    fun saveConcurToRedis(petitionId: String, userId: String) {
        val key = "petition:$petitionId"
        redisTemplate.opsForSet().add(key, userId)
    }

    //TODO: 청원 작성시, 청원 작성자는 자동으로 동의하도록
    fun saveConcur(
        concur:Concur
    ): Concur {
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