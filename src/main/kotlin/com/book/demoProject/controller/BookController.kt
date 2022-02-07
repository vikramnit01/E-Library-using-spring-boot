package com.book.demoProject.controller

import com.book.demoProject.entity.Book
import com.book.demoProject.entity.BookDto
import com.book.demoProject.enums.BookCategory
import com.book.demoProject.service.BookService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/book")
class BookController (private val bookService: BookService) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/addBook", consumes = ["multipart/form-data", "application/x-www-form-urlencoded"])
    fun addBook(
        @RequestParam(value = "file") file: MultipartFile,
        @RequestParam pages: Long,
        @RequestParam author: String,
        @RequestParam isbnNo: String,
        @RequestParam bookName: String,
        @RequestParam bookCategory: ArrayList<String>
    ): Book {
        return bookService.saveBook(pages, author, isbnNo, bookName, bookCategory, file);
    }

    @GetMapping("/getBooks")
    fun getAllBook(@RequestParam page: Int, @RequestParam pageSize: Int): ResponseEntity<List<Book>> {

        return try {
            ResponseEntity(bookService.getAllBook(page, pageSize), HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/getBookByName/{name}")
    fun getBookByName(@PathVariable("name") bookName: String): ResponseEntity<Book>? {
        return try {
            ResponseEntity(bookService.getBookByName(bookName), HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
    @GetMapping("/getBook/download/{name}")
    fun getBookByDownload(@PathVariable("name") bookName: String): ResponseEntity<ByteArray>{
        return bookService.downloadBookByName(bookName);
    }

    @DeleteMapping("/deleteBookByName/{name}")
    fun deleteBookByName(@PathVariable("name") bookName: String): ResponseEntity<String> {
        return bookService.deleteBookByName(bookName)
    }

    @GetMapping("/getBooksByAuthorName/{name}")
    fun allBookByAuthor(@PathVariable("name") authorName: String): ResponseEntity<List<Book>> {
        return try {
            ResponseEntity(bookService.allBookByAuthor(authorName), HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/getBooksByCategory/{category}")
    fun getBookByCategory(@PathVariable("category") category: BookCategory): ResponseEntity<List<BookDto>> {
        return try {
            ResponseEntity(bookService.getBookByCategory(category.name), HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }

    }

    @GetMapping("/getAllAuthor")
    fun getAllAuthor(@RequestParam page: Int, @RequestParam pageSize: Int): ResponseEntity<List<String>> {
        return try {
            ResponseEntity(bookService.getAllAuthor(page, pageSize), HttpStatus.OK)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }
    }
}
