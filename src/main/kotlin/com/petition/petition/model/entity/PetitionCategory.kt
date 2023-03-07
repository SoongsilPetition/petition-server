package com.petition.petition.model.entity

import javax.persistence.*


@Entity
@Table(name = "petition_category")
class PetitionCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var petitionCategoryId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petition_id")
    val petition: Petition,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category

): BaseEntity()