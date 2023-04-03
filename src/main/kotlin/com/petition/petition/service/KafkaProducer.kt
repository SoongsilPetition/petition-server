package com.petition.petition.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {
    fun sendConcurEvent(petitionId: String, userId: String, concurContents: String, agreementStatus: String) {
        kafkaTemplate.send("concur-events", "$petitionId,$userId,$concurContents,$agreementStatus")
    }
}