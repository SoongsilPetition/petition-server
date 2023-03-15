package com.petition.petition.model.entity

import javax.persistence.*

//TODO: 비동의 추가?

@Entity
@Table(name = "Concur")
class Concur(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var concurId: Int,

    @Column(nullable = false)
    var concurContent: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "petition_id")
    var petition: Petition,

    @Column(nullable = false)
    var agreementStatus:AgreementStatus

): BaseEntity()

enum class AgreementStatus{
    AGREE,DISAGREE
}