package com.petition.petition.controller

import com.petition.petition.model.payload.petitionAnswer.request.PetitionAnswerWriteRequestDto
import com.petition.petition.model.payload.petitionAnswer.response.PetitionAnswerResponseDto
import com.petition.petition.service.PetitionAnswerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@RestController
class PetitionAnswerController(
    private val petitionAnswerService: PetitionAnswerService
) {
    @Operation(summary = "청원 답변 작성 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "청원 답변 작성 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PetitionAnswerResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "401", description = "인증 실패", content = [Content()]),
            ApiResponse(responseCode = "404", description = "청원이 존재하지 않음", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @PostMapping("petition/answer")
    fun postPetitionAnswer(
        @RequestHeader("Authorization") jwt: String,
        @RequestBody body: PetitionAnswerWriteRequestDto
    ): ResponseEntity<PetitionAnswerResponseDto> {
        return ResponseEntity.ok(petitionAnswerService.savePetitionAnswer(body, jwt))
    }


    @Operation(summary = "특정 청원에 대한 답변 조회 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "답변 조회 성공",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PetitionAnswerResponseDto::class)
            )]
        ),
        ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
        ApiResponse(responseCode = "404", description = "해당 청원이 존재하지 않음", content = [Content()]),
        ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
    )
    @GetMapping("petition/{petitionId}/answer")
    fun getPetitionAnswer(
        @PathVariable petitionId: Int
    ): ResponseEntity<*> {
        return ResponseEntity.ok(petitionAnswerService.getPetitionAnswer(petitionId))
    }


    @Operation(summary = "청원 답변 수정 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "청원 답변 수정 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PetitionAnswerResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "401", description = "인증 실패", content = [Content()]),
            ApiResponse(responseCode = "404", description = "해당 청원 답변이 존재하지 않음", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @PatchMapping("petition/{petitionId}/answer")
    fun patchPetitionAnswer(
        @PathVariable petitionId: Int,
        @RequestHeader("Authorization") jwt: String,
        @RequestBody body: PetitionAnswerWriteRequestDto
    ): ResponseEntity<*> {
        return ResponseEntity.ok(petitionAnswerService.updatePetitionAnswer(petitionId, body, jwt))
    }

    @Operation(summary = "특정 청원 답변 삭제 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "답변 삭제 성공", content = [Content()]),
            ApiResponse(responseCode = "401", description = "인증 실패", content = [Content()]),
            ApiResponse(responseCode = "404", description = "해당 청원 답변이 존재하지 않음", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @DeleteMapping("petition/{petitionId}/answer")
    fun deletePetitionAnswer(
        @PathVariable petitionId: Int,
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<*> {
        petitionAnswerService.deletePetitionAnswer(petitionId, jwt)
        return ResponseEntity.ok("deleted petition answer")
    }

}