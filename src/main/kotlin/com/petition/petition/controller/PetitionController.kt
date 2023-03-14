package com.petition.petition.controller

import com.petition.petition.model.entity.Petition
import com.petition.petition.model.payload.petition.request.PetitionWriteRequestDto
import com.petition.petition.service.PetitionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PetitionController(
    private val petitionService: PetitionService
) {
    @PostMapping("petition")
    fun petition(@RequestBody body: PetitionWriteRequestDto): ResponseEntity<Petition> {
        return ResponseEntity.ok(petitionService.savePetition(body))
    }

}