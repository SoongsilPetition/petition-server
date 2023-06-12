package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

//TODO: 비동의 추가?

@Entity
@Table(name = "Concur")
class Concur(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var concurId: Int,

    @Column(nullable = false, length = 100000000)
    var concurContent: String,

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    var user: User?,

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "petition_id")
    var petition: Petition,

    @Column(nullable = false)
    var agreementStatus: AgreementStatus

) : BaseEntity() {
    constructor(concurContent: String, user: User?, petition: Petition?, agreementStatus: AgreementStatus) : this(
        concurId = 0,
        concurContent = concurContent,
        user = user!!,
        petition = petition!!,
        agreementStatus = agreementStatus
    )
}

enum class AgreementStatus {
    AGREE, DISAGREE, ALL
}