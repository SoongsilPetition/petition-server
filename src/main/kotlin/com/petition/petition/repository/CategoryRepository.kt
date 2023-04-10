package com.petition.petition.repository

import com.petition.petition.model.entity.Category
import com.petition.petition.model.entity.PetitionCategory
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Int> {
    fun findCategoryByCategoryName(categoryName: String): Category?
}