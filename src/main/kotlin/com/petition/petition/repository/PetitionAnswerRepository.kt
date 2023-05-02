package com.petition.petition.repository

import com.petition.petition.model.entity.PetitionAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface PetitionAnswerRepository: JpaRepository<PetitionAnswer, Int>{
    fun findByPetition_PetitionId(petitionId: Int): PetitionAnswer
}