package com.github.darderion.mundaneassignmentpolice.entities

import javax.persistence.*

@Entity
@Table(name = "projects")
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)
