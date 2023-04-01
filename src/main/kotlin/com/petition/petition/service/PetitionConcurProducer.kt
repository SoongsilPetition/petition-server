package com.petition.petition.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.petition.petition.model.entity.Concur
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PetitionConcurProducer(
    private val kafkaTemplate: KafkaTemplate<String,Concur>,
    private val objectMapper: ObjectMapper
) {
    private val petitionConcursTopic = "petition-concurs"

    fun sendPetitionConcur(concur: Concur) {
        kafkaTemplate.send(petitionConcursTopic, concur.concurId.toString(), concur)
    }
}