package com.petition.petition.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "category")
class Category(
        categoryName: String,
        createdAt: LocalDateTime,
        modifiedAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column
    var categoryName = categoryName

    @Column
    var createdAt = createdAt

    @Column
    var modifiedAt = modifiedAt

    @PrePersist
    fun onPrePersist() {
        createdAt = LocalDateTime.now()
        modifiedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun onPreUpdate() {
        modifiedAt = LocalDateTime.now()
    }

}
