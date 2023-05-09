package com.petition.petition.controller

import com.petition.petition.model.entity.AgreementStatus
import com.petition.petition.model.payload.concur.request.ConcurWriteRequestDto
import com.petition.petition.model.payload.concur.response.ConcurResponseDto
import com.petition.petition.service.ConcurService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


//청원의경우 청원 수정, 청원 취소는 없음
@RestController
@Tag(name = "ConcurController", description = "청원 의견 제기 API")
class ConcurController(
    private val concurService: ConcurService
) {
    @Operation(summary = "의견 제출 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "의견 제출 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ConcurResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "401", description = "인증 실패", content = [Content()]),
            ApiResponse(responseCode = "404", description = "해당 청원이 존재하지 않음", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @PostMapping("concur")
    fun postConcur(
        @RequestBody body: ConcurWriteRequestDto,
        @RequestHeader("Authorization") jwt: String
    ): ResponseEntity<ConcurResponseDto> {
        return ResponseEntity.ok(concurService.saveConcur(body, jwt))
    }


    //parameter를 받아서 최신순, 동의한 concur, 반대한 concur
    @Operation(summary = "특정 청원의 모든 의견 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "의견 리스트 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ConcurResponseDto::class)),
                        examples = [
                            ExampleObject(
                                value = "[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"petitionId\": 1,\n" +
                                        "    \"userId\": 1,\n" +
                                        "    \"content\": \"청원에 동의합니다\",\n" +
                                        "    \"agreementStatus\": \"AGREE\",\n" +
                                        "    \"createdAt\": \"2021-08-22T15:00:00\",\n" +
                                        "    \"updatedAt\": \"2021-08-22T15:00:00\"\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"petitionId\": 1,\n" +
                                        "    \"userId\": 2,\n" +
                                        "    \"content\": \"청원에 반대합니다\",\n" +
                                        "    \"agreementStatus\": \"DISAGREE\",\n" +
                                        "    \"createdAt\": \"2021-08-22T15:00:00\",\n" +
                                        "    \"updatedAt\": \"2021-08-22T15:00:00\"\n" +
                                        "  }\n" +
                                        "]"
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "401", description = "인증 실패", content = [Content()]),
            ApiResponse(responseCode = "404", description = "해당 청원이 존재하지 않음", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @GetMapping("concur")
    fun getConcur(
        @RequestParam(required = true) petitionId: Int,
        @RequestParam(required = false, defaultValue = "AGREE") agreementStatus: AgreementStatus, //sort가 string이 맞나?
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): ResponseEntity<List<ConcurResponseDto>> {
        return ResponseEntity.ok(concurService.getConcursList(petitionId, page, size, agreementStatus))
    }

    @Operation(summary = "사용자 의견 조회 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "의견 리스트 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ConcurResponseDto::class)),
                        examples = [
                            ExampleObject(
                                name = "example1",
                                value = "[\n" +
                                        "  {\n" +
                                        "    \"id\": 1,\n" +
                                        "    \"petitionId\": 1,\n" +
                                        "    \"userId\": 1,\n" +
                                        "    \"content\": \"청원에 동의합니다\",\n" +
                                        "    \"agreementStatus\": \"AGREE\",\n" +
                                        "    \"createdAt\": \"2021-08-22T15:00:00\",\n" +
                                        "    \"updatedAt\": \"2021-08-22T15:00:00\"\n" +
                                        "  },\n" +
                                        "  {\n" +
                                        "    \"id\": 2,\n" +
                                        "    \"petitionId\": 1,\n" +
                                        "    \"userId\": 2,\n" +
                                        "    \"content\": \"청원에 반대합니다\",\n" +
                                        "    \"agreementStatus\": \"DISAGREE\",\n" +
                                        "    \"createdAt\": \"2021-08-22T15:00:00\",\n" +
                                        "    \"updatedAt\": \"2021-08-22T15:00:00\"\n" +
                                        "  }\n" +
                                        "]"
                            )
                        ]
                    )]
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청", content = [Content()]),
            ApiResponse(responseCode = "401", description = "인증 실패", content = [Content()]),
            ApiResponse(responseCode = "500", description = "서버 오류", content = [Content()])
        ]
    )
    @GetMapping("user/concur")
    fun getUserConcur(
        @Parameter(description = "사용자 ID", required = true)
        @RequestParam(required = true) userId: Int,
        @Parameter(description = "페이지 번호, default: 1")
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @Parameter(description = "페이지 크기, default: 10")
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): ResponseEntity<List<ConcurResponseDto>> {
        return ResponseEntity.ok(concurService.getUserConcursList(userId, page, size))
    }

}