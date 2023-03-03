package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column
    var password = ""
        @JsonIgnore
        get() = field
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    var name: String? = null

    @Column(unique = true)
    var email: String =""

    @Column
    var createdAt: LocalDateTime? = null
    @Column
    var modifiedAt: LocalDateTime? = null

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