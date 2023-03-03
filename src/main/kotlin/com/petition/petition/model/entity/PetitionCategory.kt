package com.petition.petition.model.entity

import javax.persistence.*


@Entity
@Table(name = "petition_category")
class PetitionCategory(
        @ManyToOne(fetch = FetchType.LAZY)
        val petition: Petition,
        @ManyToOne(fetch = FetchType.LAZY)
        val category: Category
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}