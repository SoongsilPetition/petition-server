package com.petition.petition.service

import com.petition.petition.model.entity.Category
import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.PetitionCategory
import com.petition.petition.model.payload.petitionCategory.request.PetitionCategoryRequestDto
import com.petition.petition.repository.CategoryRepository
import com.petition.petition.repository.PetitionCategoryRepository
import org.springframework.stereotype.Service

@Service
class PetitionCategoryService(
    private val categoryRepository: CategoryRepository,
    private val petitionCategoryRepository: PetitionCategoryRepository
) {

    fun savePetitionCategories(bodyPetitionCategoryList: List<PetitionCategoryRequestDto>?,
                               petition: Petition): List<PetitionCategory>?
    {
        if (bodyPetitionCategoryList != null) {
            val petitionCategoryList = mutableListOf<PetitionCategory>()


            bodyPetitionCategoryList.forEach { petitionCategory ->
                val findCategory: Category? =
                    categoryRepository.findCategoryByCategoryName(petitionCategory.petitionCategoryName)

                //category가 없으면 새로 생성
                if (findCategory == null) {
                    val category = Category(
                        categoryName = petitionCategory.petitionCategoryName
                    )
                    categoryRepository.save(category)
                }else{
                    val petitionCategory = PetitionCategory(
                        petition = petition,
                        category = findCategory
                    )
                    petitionCategoryRepository.save(petitionCategory)
                    petitionCategoryList.add(petitionCategory)
                }
            }
            println(petitionCategoryList)
            return petitionCategoryList
        }
        return null
    }
}