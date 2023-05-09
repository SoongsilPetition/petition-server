package com.petition.petition.controller


import com.petition.petition.model.payload.auth.request.LoginRequestDto
import com.petition.petition.model.payload.auth.request.RegisterRequestDto
import com.petition.petition.model.payload.auth.response.UserResponseDto
import com.petition.petition.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "UserController", description = "사용자 API")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "사용자 등록 API")
    @ApiResponse(
        responseCode = "200",
        description = "사용자 등록 성공",
        content = [
            Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = UserResponseDto::class)),
                examples = [ExampleObject(
                    value = "{\n" +
                            "    \"id\": 1,\n" +
                            "    \"username\": \"john\",\n" +
                            "    \"nickname\": \"John Doe\",\n" +
                            "    \"email\": \"john.doe@example.com\",\n" +
                            "    \"createdAt\": \"2021-05-09T12:34:56\",\n" +
                            "    \"updatedAt\": \"2021-05-09T12:34:56\"\n" +
                            "}"
                )]
            )]
    )
    @PostMapping("user/register")
    fun register(@RequestBody body: RegisterRequestDto): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(userService.saveUser(body))
    }

    @Operation(summary = "사용자 로그인 API")
    @ApiResponse(
        responseCode = "200",
        description = "사용자 로그인 성공",
        content = [
            Content(
                mediaType = "text/plain",
                schema = Schema(type = "string"),
                examples = [ExampleObject(value = "success")]
            )],
    )
    @PostMapping("user/login")
    fun login(@RequestBody body: LoginRequestDto, response: HttpServletResponse): ResponseEntity<Any> {
        userService.login(body, response)
        return ResponseEntity.ok("success")
    }
}
