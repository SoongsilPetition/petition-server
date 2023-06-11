package com.petition.petition.service

import com.petition.petition.model.payload.category.response.CategoryResponseDto
import com.petition.petition.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun getAllCategories():List<CategoryResponseDto>?{
        return categoryRepository.findAll().map {
            CategoryResponseDto(
                id = it.categoryId,
                categoryName = it.categoryName
            )
        }
    }
}