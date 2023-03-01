package com.petition.petition.mapper

import com.petition.petition.model.dto.auth.LoginDto
import com.petition.petition.model.dto.auth.RegisterDto
import com.petition.petition.model.entity.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    abstract fun registerDtoToUser(body: RegisterDto): User
    abstract fun LoginDtoToUser(body: LoginDto): User

}