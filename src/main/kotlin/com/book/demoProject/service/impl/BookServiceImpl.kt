package com.book.demoProject.service.impl

import com.book.demoProject.entity.Book
import com.book.demoProject.entity.BookDto
import com.book.demoProject.repository.BookRepository
import com.book.demoProject.service.BookService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class BookServiceImpl @Autowired constructor(private val bookRepository: BookRepository) : BookService {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    @CacheEvict(value = ["BookCache"], allEntries = true)
    override fun saveBook(
        pages: Long,
        author: String,
        isbnNo:String,
        bookName: String,
        bookCategory: ArrayList<String>,
        file: MultipartFile): Book {
        var book: Book = Book();
        book.pdf = file.bytes;
        book.pages = pages;
        book.author = author;
        book.isbnNo = isbnNo;
        book.bookName = bookName;
        book.bookCategory = bookCategory;
        return bookRepository.save(book);
    }

    @Cacheable(value = ["BookCache"])
    override fun getAllBook(page: Int, pageSize: Int): List<Book> {
        logger.info("db is called")
        val pageable = PageRequest.of(page,pageSize);
        val result = bookRepository.findAll(pageable);
        return if(result.hasContent())
            result.content
        else
            emptyList()
    }


    @Cacheable(value = ["BookCache"])
    override fun getBookByName(bookName: String): Book? {
        return bookRepository.findByBookName(bookName)
    }


    @CacheEvict(value=["BookCache"], allEntries = true)
    override fun deleteBookByName(bookName: String) : ResponseEntity<String> {
        val book = bookRepository.findByBookName(bookName)
        if(book != null)
            bookRepository.delete(book)
        return ResponseEntity.ok("Deleted Successfully");
    }
    @Cacheable(value=["BookCache"])
    override fun allBookByAuthor(authorName: String): List<Book> {
        logger.info("db is called")
        return bookRepository.findAllByAuthor(authorName)
    }
    @Cacheable(value=["BookCache"])
    override fun getBookByCategory(category: String): List<BookDto> {
        return bookRepository.findAll().filter { it.bookCategory.contains(category) }.map { it-> BookDto(it) }

    }
    @Cacheable(value=["BookCache"])
    override fun getAllAuthor(page: Int, pageSize: Int): List<String> {
        val pageable = PageRequest.of(page,pageSize);
        val result = bookRepository.findAllAuthor(pageable)
        return if(result.hasContent())
            result.content
        else
            emptyList()
    }

    override fun downloadBookByName(bookName: String) : ResponseEntity<ByteArray> {
        val book = bookRepository.findByBookName(bookName);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book?.bookName + "\"")
            .body(book?.pdf);
    }
}