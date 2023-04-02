package com.petition.petition.controller

import com.petition.petition.common.exception.UnauthenticatedException
import com.petition.petition.model.entity.User
import com.petition.petition.model.payload.auth.request.LoginRequestDto
import com.petition.petition.model.payload.auth.request.RegisterRequestDto
import com.petition.petition.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("user/register")
    fun register(@RequestBody body: RegisterRequestDto): ResponseEntity<User> {

        //Service에서 User 저장 로직 수행
        return ResponseEntity.ok(userService.saveUser(body))
    }

    @PostMapping("user/login")
    fun login(@RequestBody body: LoginRequestDto, response: HttpServletResponse): ResponseEntity<Any> {
        userService.login(body,response)
        return ResponseEntity.ok("success")
    }
    //TODO: myprofile 구현
}