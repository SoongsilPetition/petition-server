package com.petition.petition.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Petition")
class Petition(
        petitionTitle: String,
        petitionContent: String,

        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User,

        petitionDueDate: LocalDateTime,
        createdAt: LocalDateTime,
        modifiedAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(nullable = false)
    var petitionTitle = petitionTitle

    @Column(nullable = false)
    var petitionContent = petitionContent

    @Column(nullable = false)
    var petitionDueDate = petitionDueDate

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