package com.petition.petition.model.payload.auth.response

import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class JwtResponseDto(
    val userId:Int?=null,
    val jwt:String?=""
)