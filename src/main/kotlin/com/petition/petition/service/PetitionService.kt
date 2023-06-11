package com.petition.petition.service

import com.petition.petition.model.entity.*
import com.petition.petition.model.payload.auth.response.UserResponseDto
import com.petition.petition.model.payload.petition.request.PetitionWriteRequestDto
import com.petition.petition.model.payload.petition.response.PetitionResponseDto
import com.petition.petition.model.payload.petitionCategory.response.PetitionCategoryResponseDto
import com.petition.petition.repository.PetitionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import java.net.http.HttpClient

@Service
class PetitionService(
    private val petitionRepository: PetitionRepository,
    private val userService: UserService,
    private val petitionCategoryService: PetitionCategoryService
) {

    fun savePetition(body: PetitionWriteRequestDto, jwt: String): PetitionResponseDto {
        val user: User? = userService.getValidUser(jwt)
        //TODO:글 내용을 인공 지능 서버와 소통해서 분란글, 정상글, 뻘글인지 분류.
        if (sendToAzureML(body.petitionContent) == "hate") {
            throw Exception("분란글입니다.")
        }else if(sendToAzureML(body.petitionContent) == "meaningless"){
            throw Exception("뻘글입니다.")
        }
        //일단은 APPROPRIATE로 저장
        //Question: petitionType을 Petition엔티티 파일아래에 두는게 아닌 파일을 분리해야하는지 고민
        val petitionType: PetitionType = PetitionType.APPROPRIATE

        val petition = Petition(
            petitionTitle = body.petitionTitle,
            petitionContent = body.petitionContent,
            users = user,
            petitionType = petitionType,
            petitionImage = body.petitionImage,
        )
        //petition을 일단 저장하고 category를 추가하는 방식으로 설정
        petitionRepository.save(petition)
        val petitionCategories = petitionCategoryService.savePetitionCategories(body.petitionCategory, petition)
        petition.category = petitionCategories
        val petitionCategoryResponseDtoList = mutableListOf<PetitionCategoryResponseDto>()
        petitionCategories?.forEach { petitionCategory ->
            println(petitionCategory.category.categoryName)
            val petitionCategoryResponseDto = PetitionCategoryResponseDto(
                petitionCategoryId = petitionCategory.petitionCategoryId,
                petitionCategoryName = petitionCategory.category.categoryName
            )
            petitionCategoryResponseDtoList.add(petitionCategoryResponseDto)
        }

        //category를 추가하여 다시 저장
        val savedPetition = petitionRepository.save(petition)

        val response: PetitionResponseDto = PetitionResponseDto(
            petitionId = savedPetition.petitionId,
            petitionTitle = savedPetition.petitionTitle,
            petitionContent = savedPetition.petitionContent,
            petitionImage = savedPetition.petitionImage,
            petitionCategory = petitionCategoryResponseDtoList,
            user = UserResponseDto(
                userId = savedPetition.users?.userId,
                name = savedPetition.users?.name,
                email = savedPetition.users?.email,
                createdAt = savedPetition.users?.createdAt.toString(),
                updatedAt = savedPetition.users?.updatedAt.toString()
            ),
            petitionAgreement = savedPetition.agreeCount,
            petitionDisagreement = savedPetition.disagreeCount,
            createdAt = savedPetition.createdAt.toString(),
            updatedAt = savedPetition.updatedAt.toString(),
            petitionDueDate = savedPetition.petitionDueDate.toString()
        )
        return response

    }

    fun getPetitionsList(page: Int, size: Int, sort: String, category: String?): List<PetitionResponseDto>? {
        val pageRequest: Pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, sort)
        val petitions: Page<Petition>
        if (category == null) {
            petitions = petitionRepository.findAll(pageRequest)
        } else {
            petitions = petitionRepository.findAllByCategoryNames(category, pageRequest)
        }
        val petitionResponseDtoList = mutableListOf<PetitionResponseDto>()
        petitions.forEach { petition ->
            val petitionCategoryResponseDtoList = mutableListOf<PetitionCategoryResponseDto>()
            val findPetitionCategory = petitionCategoryService.getPetitionCategories(petition)
            findPetitionCategory?.forEach { petitionCategory ->
                val petitionCategoryResponseDto = PetitionCategoryResponseDto(
                    petitionCategoryId = petitionCategory.petitionCategoryId,
                    petitionCategoryName = petitionCategory.category.categoryName
                )
                petitionCategoryResponseDtoList.add(petitionCategoryResponseDto)
            }
            val petitionResponseDto = PetitionResponseDto(
                petitionId = petition.petitionId,
                petitionTitle = petition.petitionTitle,
                petitionContent = petition.petitionContent,
                petitionImage = petition.petitionImage,
                petitionCategory = petitionCategoryResponseDtoList,
                petitionAgreement = petition.agreeCount,
                petitionDisagreement = petition.disagreeCount,
                user = UserResponseDto(
                    userId = petition.users?.userId,
                    name = petition.users?.name,
                    email = petition.users?.email,
                    createdAt = petition.users?.createdAt.toString(),
                    updatedAt = petition.users?.updatedAt.toString()
                ),
                createdAt = petition.createdAt.toString(),
                updatedAt = petition.updatedAt.toString(),
                petitionDueDate = petition.petitionDueDate.toString()
            )
            petitionResponseDtoList.add(petitionResponseDto)
        }
        return petitionResponseDtoList
    }

    fun getValidatedPetition(petitionId: Int): Petition {
        return petitionRepository.getById(petitionId)
    }

    fun getPetition(petitionId: Int): PetitionResponseDto {
        val findPetition = getValidatedPetition(petitionId)

        val findPetitionCategory = petitionCategoryService.getPetitionCategories(findPetition)
        val petitionCategoryResponseDtoList = mutableListOf<PetitionCategoryResponseDto>()
        findPetitionCategory?.forEach { petitionCategory ->
            val petitionCategoryResponseDto = PetitionCategoryResponseDto(
                petitionCategoryId = petitionCategory.petitionCategoryId,
                petitionCategoryName = petitionCategory.category.categoryName
            )
            petitionCategoryResponseDtoList.add(petitionCategoryResponseDto)
        }
        return PetitionResponseDto(
            petitionId = findPetition.petitionId,
            petitionTitle = findPetition.petitionTitle,
            petitionContent = findPetition.petitionContent,
            petitionImage = findPetition.petitionImage,
            petitionCategory = petitionCategoryResponseDtoList,
            petitionAgreement = findPetition.agreeCount,
            petitionDisagreement = findPetition.disagreeCount,
            user = UserResponseDto(
                userId = findPetition.users?.userId,
                name = findPetition.users?.name,
                email = findPetition.users?.email,
                createdAt = findPetition.users?.createdAt.toString(),
                updatedAt = findPetition.users?.updatedAt.toString()
            ),
            createdAt = findPetition.createdAt.toString(),
            updatedAt = findPetition.updatedAt.toString(),
            petitionDueDate = findPetition.petitionDueDate.toString()
        )
    }

    fun getOngoingPetitionsList(): List<Petition>? {
        return petitionRepository.findAllByPetitionType(PetitionStatus.ONGOING)
    }

    fun updatePetition(petition: Petition): Petition {
        return petitionRepository.save(petition)
    }

    fun sendToAzureML(text: String): String {
        val webClient = WebClient.builder().build()
        // Petition 객체를 가정하고, petition.text를 전송 데이터로 사용합니다.
        val requestBody = mapOf("text" to text)
        val response = webClient.post()
            .uri("http://124.49.124.27:9090/predict")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()

        // 응답 데이터에서 "result" 필드의 값을 가져옵니다.
        val result = response?.get("result") as String
        println("결과:"+result)
        return result


    }

}