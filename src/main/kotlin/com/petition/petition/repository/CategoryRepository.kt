package com.petition.petition.repository

import com.petition.petition.model.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Int> {

}