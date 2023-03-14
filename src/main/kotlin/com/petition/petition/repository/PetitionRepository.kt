package com.petition.petition.repository

import com.petition.petition.model.entity.Petition
import org.springframework.data.jpa.repository.JpaRepository

interface PetitionRepository: JpaRepository<Petition, Int> {

}