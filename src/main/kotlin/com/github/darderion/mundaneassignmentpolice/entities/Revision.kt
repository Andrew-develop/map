package com.github.darderion.mundaneassignmentpolice.entities

import java.sql.Timestamp
import javax.persistence.*;

@Entity
@Table(name = "revisions")
data class Revision(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "filepath")
    val filepath: String,

    @Column(name = "rev_date")
    val revDate: Timestamp,

    @ManyToOne
    @JoinColumn(name = "project_id")
    val project: Project?,

    @ManyToOne
    @JoinColumn(name = "preset_id")
    val preset: Preset,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)
