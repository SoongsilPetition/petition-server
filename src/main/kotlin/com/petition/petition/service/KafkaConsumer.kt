package com.petition.petition.service

import com.petition.petition.model.entity.AgreementStatus
import com.petition.petition.model.entity.Concur
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    private val concurService: ConcurService,
    private val userService: UserService,
    private val petitionService: PetitionService
    ) {
    /*
    @KafkaListener(topics = ["concur-events"], groupId = "petition-group")
    fun consume(concurEvent: String) {
        val (petitionId, userId,concurContents,agreementStatus) = concurEvent.split(",")
        val findUser = userService.getById(userId.toInt())
        val findPetition = petitionService.getPetition(petitionId.toInt())
        val concur = Concur(
            concurContent = concurContents,
            user = findUser,
            petition= findPetition,
            agreementStatus = AgreementStatus.valueOf(agreementStatus)
        )
        concurService.saveConcur(concur)
        concurService.saveConcurToRedis(petitionId, userId)
    }

     */
}