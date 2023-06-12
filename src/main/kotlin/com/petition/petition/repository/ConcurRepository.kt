package com.petition.petition.repository

import com.petition.petition.model.entity.AgreementStatus
import com.petition.petition.model.entity.Concur
import com.petition.petition.model.entity.Petition
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ConcurRepository: JpaRepository<Concur, Int> {
    fun findAllByPetitionAndAgreementStatus(petition: Petition, agreementStatus: AgreementStatus, pageable: Pageable): Page<Concur>
    fun countByPetitionAndAgreementStatus(petitionId: Int, agreementStatus: AgreementStatus): Int

    fun findAllByUser_UserId(userId: Int, pageable: Pageable): Page<Concur>
    abstract fun findAllByPetition(petition: Petition, pageRequest: Pageable): Page<Concur>
}