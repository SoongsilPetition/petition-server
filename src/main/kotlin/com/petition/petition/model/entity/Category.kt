package com.petition.petition.model.entity

import javax.persistence.*

@Entity
@Table(name = "category")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoryId: Int,

    @Column
    var categoryName:String

):BaseEntity(){
    constructor(categoryName: String) : this(
        categoryId = 0,
        categoryName = categoryName
    )
}