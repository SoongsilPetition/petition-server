package com.petition.petition.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
class User(
        password: String,
        name: String,
        email: String,

        @OneToMany(mappedBy = "users", cascade = [CascadeType.ALL])
        val petitions: MutableList<Petition> = mutableListOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(nullable = false)
    var password = BCryptPasswordEncoder().encode(password)

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
    var createdAt: LocalDateTime? = null
        protected set

    @Column
    var modifiedAt: LocalDateTime? = null
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