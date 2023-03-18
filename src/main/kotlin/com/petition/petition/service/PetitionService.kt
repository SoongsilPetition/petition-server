package com.petition.petition.service

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.entity.PetitionType
import com.petition.petition.model.entity.User
import com.petition.petition.model.payload.petition.request.PetitionWriteRequestDto
import com.petition.petition.repository.PetitionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class PetitionService(
    private val petitionRepository: PetitionRepository,
    private val userService: UserService,
) {
    fun savePetition(body: PetitionWriteRequestDto, jwt:String): Petition {
        val user: User? = userService.getValidUser(jwt)
        //TODO:글 내용을 인공 지능 서버와 소통해서 분란글, 정상글, 뻘글인지 분류.
        //일단은 APPROPRIATE로 저장
        //Question: petitionType을 Petition엔티티 파일아래에 두는게 아닌 파일을 분리해야하는지 고민
        val petitionType: PetitionType = PetitionType.APPROPRIATE


        val petition = Petition(
            petitionTitle = body.petitionTitle,
            petitionContent = body.petitionContent,
            users = user,
            petitionType = petitionType
        )

        //TODO: category를 forEach 반복문을 이용하여 작성
        return petitionRepository.save(petition)
    }

    fun getPetitionsList(page: Int, size: Int): List<Petition>? {
        val pageRequest: Pageable = PageRequest.of(page - 1, 10, Sort.Direction.DESC, "createdAt")
        val petitions: Page<Petition> = petitionRepository.findAll(pageRequest)
        return petitions.content
    }

    fun getPetition(petitionId: Int): Petition{
        return petitionRepository.getById(petitionId)
    }

}