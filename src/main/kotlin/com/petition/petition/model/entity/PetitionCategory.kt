package com.petition.petition.model.entity

import javax.persistence.*


@Entity
@Table(name = "petition_category")
class PetitionCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    val petition: Petition,
    @ManyToOne(fetch = FetchType.LAZY)
    val category: Category
): BaseEntity()