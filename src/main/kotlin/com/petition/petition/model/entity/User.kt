package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Int = 0,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = true)
    var roleFlag: RoleFlag = RoleFlag.USER,

    @JsonBackReference //순환참조 방지
    @OneToMany(mappedBy = "users", cascade = [CascadeType.ALL])
    var petitions: MutableList<Petition> = mutableListOf(),

    @JsonBackReference //순환참조 방지
    @OneToMany(mappedBy = "users", cascade = [CascadeType.ALL])
    var petitionAnswers: MutableList<PetitionAnswer> = mutableListOf(),

) : BaseEntity() {

    init {
        this.roleFlag = RoleFlag.USER
        password = BCryptPasswordEncoder().encode(password)
    }

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}

enum class RoleFlag {
    ADMIN, USER
}