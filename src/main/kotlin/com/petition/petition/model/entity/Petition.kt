package com.petition.petition.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Petition")
class Petition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column(nullable = false)
    var petitionTitle:String,

    @Column(nullable = false)
    var petitionContent: String,

    @Column(nullable = false)
    var petitionDueDate: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var users: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: PetitionStatus // 청원 상태
) : BaseEntity()

enum class PetitionStatus {
    ONGOING, ACCEPTED, REJECTED
}