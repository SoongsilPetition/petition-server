package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "User")
class User(
        password: String,
        name: String,
        email: String,
        createdAt: LocalDateTime,
        modifiedAt: LocalDateTime,

        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
        val petitions: MutableList<Petition> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(nullable = false)
    var password = password
        @JsonIgnore
        get
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }


    @Column(nullable = false)
    var name = name
        protected set

    @Column(unique = true)
    var email = email
        protected set

    /*
    @Column(nullable = false)
    var roleFlag: Int = 0
     */


    @Column
    var createdAt = createdAt
        protected set

    @Column
    var modifiedAt = modifiedAt
        protected set


    @PrePersist
    fun onPrePersist() {
        createdAt = LocalDateTime.now()
        modifiedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun onPreUpdate() {
        modifiedAt = LocalDateTime.now()
    }


    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }

}