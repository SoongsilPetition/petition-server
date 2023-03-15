package com.petition.petition.repository

import com.petition.petition.model.entity.Concur
import org.springframework.data.jpa.repository.JpaRepository

interface ConcurRepository: JpaRepository<Concur, Int> {

}