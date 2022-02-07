package com.book.demoProject.service

import com.book.demoProject.entity.Book
import com.book.demoProject.entity.BookDto
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface BookService {
    fun saveBook(
        pages: Long,
        author: String,
        isbnNo:String,
        bookName: String,
        bookCategory: ArrayList<String>,
        file: MultipartFile): Book
    fun getAllBook(page: Int, pageSize: Int): List<Book>
    fun getBookByName(bookName: String): Book?
    fun deleteBookByName(bookName: String): ResponseEntity<String>
    fun allBookByAuthor(authorName: String): List<Book>
    fun getBookByCategory(category: String): List<BookDto>
    fun getAllAuthor(page: Int, pageSize: Int): List<String>
    fun downloadBookByName(bookName: String): ResponseEntity<ByteArray>
}