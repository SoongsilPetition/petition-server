package com.petition.petition.controller

import com.petition.petition.model.entity.Concur
import com.petition.petition.model.payload.concur.request.ConcurWriteRequestDto
import com.petition.petition.service.ConcurService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class ConcurController(
    private val concurService: ConcurService
) {
    @PostMapping("concur")
    fun postConcur(
        @RequestBody body: ConcurWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ):ResponseEntity<Concur> {

        return ResponseEntity.ok(concurService.saveConcur(body,jwt))
    }
}