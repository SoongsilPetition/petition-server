package com.petition.petition.controller

import com.petition.petition.model.entity.AgreementStatus
import com.petition.petition.model.entity.Concur
import com.petition.petition.model.payload.concur.request.ConcurWriteRequestDto
import com.petition.petition.service.ConcurService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    //parameter를 받아서 최신순, 동의한 concur, 반대한 concur
    //TODO: 특정 petition의 concur를 가져오는걸 어떻게 할건지 고민해보기
    @GetMapping("concur")
    fun getConcur(
        @RequestParam(required = true) petitionId: Int,
        @RequestParam(required = false, defaultValue = "AGREE") agreementStatus: AgreementStatus, //sort가 string이 맞나?
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ):ResponseEntity<*>{
        return ResponseEntity.ok(concurService.getConcursList(petitionId, page,size,agreementStatus))
    }
}