package com.petition.petition.repository

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.PetitionStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PetitionRepository: JpaRepository<Petition, Int> {
    fun findAllByPetitionType(petitionStatus: PetitionStatus): List<Petition>
    @Query("SELECT p FROM Petition p JOIN p.category pc JOIN pc.category c WHERE c.categoryName = :category")
    fun findAllByCategoryNames(category: String, page: Pageable): Page<Petition>

    //find By Petition Content Containing keyword
    @Query("SELECT p FROM Petition p WHERE p.petitionContent LIKE %:keyword%")
    fun findAllByPetitionContentContaining(keyword: String, page: Pageable): Page<Petition>
}