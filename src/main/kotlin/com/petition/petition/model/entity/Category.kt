package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
@Table(name = "category")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoryId: Int,

    @Column
    var categoryName: String,

    @JsonBackReference
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    var category: List<PetitionCategory>? = mutableListOf(),


    ) : BaseEntity() {
    constructor(categoryName: String) : this(
        categoryId = 0,
        categoryName = categoryName
    )
}