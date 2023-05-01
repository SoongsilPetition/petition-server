package com.petition.petition.repository

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.PetitionCategory
import org.springframework.data.jpa.repository.JpaRepository

interface PetitionCategoryRepository : JpaRepository<PetitionCategory, Int> {
    abstract fun findPetitionCategoriesByPetition(petition: Petition): List<PetitionCategory>?
}