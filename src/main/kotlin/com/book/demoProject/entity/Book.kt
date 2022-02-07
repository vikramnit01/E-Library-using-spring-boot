package com.book.demoProject.entity

import com.book.demoProject.enums.BookCategory
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long =0,
    @CreatedDate
    val addedOn: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @NotNull
    var pages: Long=0,
    @NotNull
    var author: String="",
    @NotNull
    var isbnNo: String="",
    @NotNull
    @Column(unique = true)
    var bookName:String="",
    @NotNull
    var bookCategory : ArrayList<String>,
    @Lob
    var pdf: ByteArray
) : Serializable {
    constructor() : this(
        0, LocalDateTime.now(), LocalDateTime.now(), 0, "", "", "",ArrayList(), ByteArray(Int.SIZE_BITS)
    )
}