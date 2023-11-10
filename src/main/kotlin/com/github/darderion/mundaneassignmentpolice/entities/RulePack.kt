package com.github.darderion.mundaneassignmentpolice.entities

import javax.persistence.*

@Entity
@Table(name = "rule_pack")
data class RulePack (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int,

    @Column(name = "name")
    val name: String,

    @Column(name = "price")
    val price: Int,

    @ManyToMany
    @JoinTable(
        name = "rule_packs_rules",
        joinColumns = [JoinColumn(name = "rule_pack_id")],
        inverseJoinColumns = [JoinColumn(name = "rule_id")]
    )
    var rules: Collection<Rule>
)