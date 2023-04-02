package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Petition")
@NoArgsConstructor
@AllArgsConstructor
class Petition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var petitionId: Int,

    @Column(nullable = false)
    var petitionTitle: String,

    @Column(nullable = false)
    var petitionContent: String,

    @Column(nullable = false)
    var petitionDueDate: LocalDateTime = LocalDateTime.now().plusDays(30),

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    var users: User?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: PetitionStatus = PetitionStatus.ONGOING, // 청원 상태

    @JsonBackReference //순환참조 방지
    @OneToMany(mappedBy = "petition", cascade = [CascadeType.ALL])
    var concur: MutableList<Concur> = mutableListOf(),

    //해쉬태그와 비슷한 기능으로.. 이해해달라
    @OneToMany(mappedBy = "petition", cascade = [CascadeType.ALL])
    var category: MutableList<PetitionCategory> = mutableListOf(),

    @Column(nullable = false)
    var likeCount: Long = 0, // 좋아요 갯수를 캐시를 이용해 처리

    //글 작성 버튼을 누르는 순간 글이 뻘글인지, 분란글인지, 정상글인지 인공지능 서버가
    // 판단하여 보내준다. enumerated 컬럼 넣기
    @Column(nullable = false)
    var petitionType: PetitionType,

    @Column
    var petitionImage: String? = null,

) : BaseEntity() {

    constructor(petitionTitle: String, petitionContent: String, users: User?, petitionType: PetitionType, petitionImage: String?) : this(
        petitionId = 0,
        petitionTitle = petitionTitle,
        petitionContent = petitionContent,
        users = users,
        petitionType = petitionType,
        petitionImage = petitionImage
    )
}

enum class PetitionStatus {
    ONGOING, ACCEPTED, REJECTED
}

enum class PetitionType {
    APPROPRIATE, CONTROVERSIAL, USELESS
}

