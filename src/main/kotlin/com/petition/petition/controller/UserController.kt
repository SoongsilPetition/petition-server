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


    //이거 이렇게 하면 안되고 userid를 받아서 그 id에 해당되는 유저를 리턴하는 방식으로 수정해야함함
    @GetMapping("myprofile")
    fun user(
        @RequestHeader("Authorization") jwt: String?
    ): ResponseEntity<Any> {
        return try {
            val user = userService.getValidUser(jwt)
            ResponseEntity.ok(user)
        } catch (e: Exception) {
            ResponseEntity.status(401).body(UnauthenticatedException("unauthenticated"))
        }
    }

}