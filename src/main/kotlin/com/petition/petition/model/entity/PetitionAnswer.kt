package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Table(name = "Petition_Answer")
@NoArgsConstructor
@AllArgsConstructor
data class PetitionAnswer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var petitionAnswerId: Int,

    @Column(nullable = false)
    var petitionAnswerTitle: String,
    @Column(nullable = false, length = 100000000)
    var petitionAnswerContent: String,
    @Column
    var petitionAnswerImage: String? = null,

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    var users: User?,

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JsonManagedReference
    @JoinColumn(name = "petition_id")
    var petition: Petition,

    ) : BaseEntity() {
    constructor(
        petitionAnswerTitle: String,
        petitionAnswerContent: String,
        petition: Petition,
        user: User?,
        petitionAnswerImage: String?
    ) : this(
        0,
        petitionAnswerTitle,
        petitionAnswerContent,
        petitionAnswerImage,
        user,
        petition,
    )
}