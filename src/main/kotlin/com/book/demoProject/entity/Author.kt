package com.book.demoProject.entity

import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Author(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @NotNull
    val name: String = ""
)