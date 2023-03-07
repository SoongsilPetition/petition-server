package com.petition.petition.model.entity

import javax.persistence.*


@Entity
@Table(name = "Concur")
class Concur(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column(nullable = false)
    var concurContent: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "petition_id")
    var petition: Petition

): BaseEntity()