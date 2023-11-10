package com.github.darderion.mundaneassignmentpolice.entities

import javax.persistence.*

@Entity
@Table(name = "rules")
data class Rule (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,

    @Column(name = "name")
    val name: String
)