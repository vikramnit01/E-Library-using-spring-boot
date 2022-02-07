package com.book.demoProject.repository

import com.book.demoProject.entity.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByBookName(bookName :String) : Book?
    fun findAllByAuthor(author :String) : List<Book>
    fun findAllByBookCategory(bookCategory: String): List<Book>
    //    countQuery ="select count(distinct author) from book"
    @Query("select distinct author from book", nativeQuery = true)
    fun findAllAuthor(pageable: Pageable): Page<String>

}