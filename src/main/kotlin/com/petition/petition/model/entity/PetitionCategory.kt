package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*


@Entity
@Table(name = "petition_category")
class PetitionCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var petitionCategoryId: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petition_id")
    @JsonManagedReference
    val petition: Petition,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    val category: Category

): BaseEntity(){
    constructor(petition: Petition, category: Category) : this(
        petitionCategoryId = 0,
        petition = petition,
        category = category
    )
}