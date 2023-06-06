package com.petition.petition.controller

import com.petition.petition.model.payload.petition.request.PetitionWriteRequestDto
import com.petition.petition.model.payload.petition.response.PetitionResponseDto
import com.petition.petition.service.PetitionService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tags

@Tag(name = "PetitionController", description = "청원 API, agreeCount을 sort 파라미터로 주면 동의 많은 순으로 정렬")
@RestController
class PetitionController(
    private val petitionService: PetitionService
) {

    @Operation(summary = "청원 글 작성 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "401", description = "인증 실패", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @PostMapping("petition")
    fun postPetition(
        @Parameter(description = "청원 글 작성 정보", required = true)
        @RequestBody body: PetitionWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<Any> {
        try{
            val savedPost = petitionService.savePetition(body, jwt)
            return ResponseEntity.ok(savedPost)
        } catch (e: Exception) {
            //분란글이라는 오류 메시지 출력
            return ResponseEntity.badRequest().body("hate petition. Can't save petition")
        }
    }

    //TODO: 최신순, 동의 많은순 정렬 기능 추가
    //필요한 내용만 가져오도록 수정
    @Operation(summary = "청원 글 리스트 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "청원 글 리스트 조회 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PetitionResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @GetMapping("petition")
    fun getPetitions(
        @Parameter(description = "페이지 번호", required = false, example = "1")
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @Parameter(description = "페이지 크기", required = false, example = "10")
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @Parameter(description = "정렬 기준", required = false, example = "agreeCount")
        @RequestParam(required = false, defaultValue = "createdAt") sort: String,
        @Parameter(description = "카테고리", required = false, example = "교통")
        @RequestParam(required = false) category: String?
    ): ResponseEntity<List<PetitionResponseDto>> {
        return ResponseEntity.ok(petitionService.getPetitionsList(page, size, sort, category))
    }

    @Operation(summary = "특정 청원 글 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "청원 글 조회 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PetitionResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @GetMapping("petition/{petitionId}")
    fun getPetition(
        @Parameter(description = "조회할 청원글 ID", required = true)
        @PathVariable petitionId: Int
    ): ResponseEntity<PetitionResponseDto> {
        return ResponseEntity.ok(petitionService.getPetition(petitionId))
    }
}