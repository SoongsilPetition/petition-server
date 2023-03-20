package com.petition.petition.controller

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.payload.petition.request.PetitionWriteRequestDto
import com.petition.petition.service.PetitionService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class PetitionController(
    private val petitionService: PetitionService
) {
    @PostMapping("petition")
    fun postPetition(
        @RequestBody body: PetitionWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<Petition> {
        return ResponseEntity.ok(petitionService.savePetition(body, jwt))
    }

    //TODO: 페이징처리해서 가져오는데 concur하고 내용까지 가져오는걸 수정해서
    //필요한 내용만 가져오도록 수정
    @GetMapping("petition") //petition 리스트 가져오기
    fun getPetitions(
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<*> {
        return ResponseEntity.ok(petitionService.getPetitionsList(page, size))
    }

    @GetMapping("petition/{petitionId}") //특정 청원 글 가져오기
    fun getPetition(
        @PathVariable petitionId: Int
    ): ResponseEntity<*> {
        return ResponseEntity.ok(petitionService.getPetition(petitionId))
    }
}