package com.petition.petition.controller

import com.petition.petition.model.payload.petitionAnswer.request.PetitionAnswerWriteRequestDto
import com.petition.petition.model.payload.petitionAnswer.response.PetitionAnswerResponseDto
import com.petition.petition.service.PetitionAnswerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PetitionAnswerController(
    private val petitionAnswerService: PetitionAnswerService
) {
    @PostMapping("petition/answer")
    fun postPetitionAnswer(
        @RequestHeader("Authorization") jwt: String,
        @RequestBody body: PetitionAnswerWriteRequestDto
    ): ResponseEntity<PetitionAnswerResponseDto> {
        return ResponseEntity.ok(petitionAnswerService.savePetitionAnswer(body,jwt))
    }

    @GetMapping("petition/{petitionId}/answer")
    fun getPetitionAnswer(
        @PathVariable petitionId: Int
    ): ResponseEntity<*> {
        return ResponseEntity.ok(petitionAnswerService.getPetitionAnswer(petitionId))
    }

    @PatchMapping("petition/{petitionId}/answer")
    fun patchPetitionAnswer(
        @PathVariable petitionId: Int,
        @RequestHeader("Authorization") jwt: String,
        @RequestBody body: PetitionAnswerWriteRequestDto
    ): ResponseEntity<*> {
        return ResponseEntity.ok(petitionAnswerService.updatePetitionAnswer(petitionId,body,jwt))
    }

    @DeleteMapping("petition/{petitionId}/answer")
    fun deletePetitionAnswer(
        @PathVariable petitionId: Int,
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<*> {
        petitionAnswerService.deletePetitionAnswer(petitionId,jwt)
        return ResponseEntity.ok("deleted petition answer")
    }

}