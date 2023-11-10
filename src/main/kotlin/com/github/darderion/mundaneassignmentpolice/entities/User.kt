package com.github.darderion.mundaneassignmentpolice.entities

import javax.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "firstname")
    var firstname: String,

    @Column(name = "lastname")
    var lastname: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "email")
    var email: String,

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    var subscription: Subscription,

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Collection<Role>,

    @ManyToMany
    @JoinTable(
        name = "users_rights",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "right_id")]
    )
    var rulePacks: Collection<RulePack>
)