package com.petition.petition.service

import com.petition.petition.common.exception.InvalidUserException
import com.petition.petition.common.exception.UnauthenticatedException
import com.petition.petition.model.payload.auth.request.LoginRequestDto
import com.petition.petition.model.payload.auth.request.RegisterRequestDto
import com.petition.petition.model.entity.User
import com.petition.petition.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun saveUser(body: RegisterRequestDto): User {
        val user = User(
            password = body.password,
            name = body.name,
            email = body.email,
        )
        return userRepository.save(user)
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getById(id: Int): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun checkValidUser(body: LoginRequestDto) {
        val findUser: User = findByEmail(body.email) ?: throw InvalidUserException("User not found")
        if (!findUser.comparePassword(body.password)) {
            throw InvalidUserException("password is not correct")
        }

    }

    //TODO: secret키 하드코딩한거 수정
    fun generateJwt(body: LoginRequestDto): String? {
        val issuer = body.email
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        return jwt
    }

    fun getValidUser(jwt: String?): User? {
        if (jwt == null) {
            throw UnauthenticatedException("unauthenticated")
        }

        val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body

        return getById(body.issuer.toInt())
    }
}