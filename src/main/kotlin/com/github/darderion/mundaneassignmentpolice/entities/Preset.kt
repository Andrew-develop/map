package com.github.darderion.mundaneassignmentpolice.entities

import javax.persistence.*

@Entity
@Table(name = "presets")
data class Preset(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "name")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToMany
    @JoinTable(
        name = "presets_rules",
        joinColumns = [JoinColumn(name = "preset_id")],
        inverseJoinColumns = [JoinColumn(name = "rule_id")]
    )
    var rules: Collection<Rule>
)
