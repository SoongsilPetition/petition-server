package com.petition.petition.repository

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.PetitionStatus
import org.springframework.data.jpa.repository.JpaRepository

interface PetitionRepository: JpaRepository<Petition, Int> {
    fun findAllByPetitionType(petitionStatus: PetitionStatus): List<Petition>

}