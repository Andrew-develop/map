package com.github.darderion.mundaneassignmentpolice.entities

import javax.persistence.*

@Entity
@Table(name = "subscriptions")
data class Subscription (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "price")
    val price: Int,

    @Column(name = "projects")
    val projects: Int,

    @Column(name = "revisions_per_day")
    val revisionsPerDay: Int,

    @Column(name = "presets")
    val presets: Int
)