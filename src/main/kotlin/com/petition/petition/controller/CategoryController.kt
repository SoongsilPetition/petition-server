package com.petition.petition.controller

import com.petition.petition.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping("/category")
    fun getCategory(): ResponseEntity<Any> {
        val category = categoryService.getAllCategories()
        return ResponseEntity.ok(category)
    }
}