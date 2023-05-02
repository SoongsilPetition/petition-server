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
    @Column(nullable = false)
    var petitionAnswerContent: String,
    @Column
    var petitionAnswerImage: String? = null,

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    var users: User?,

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "petition_id")
    var petition: Petition,

) : BaseEntity()