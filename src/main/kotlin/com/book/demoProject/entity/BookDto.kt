package com.book.demoProject.entity

import java.io.Serializable

class BookDto(var pages:Long,var author:String,var isbnNo:String,var bookName:String,var bookCategory:ArrayList<String>) :
    Serializable {
    var id:Long=0
    constructor(book:Book): this(
        book.pages,
        book.author,
        book.isbnNo,
        book.bookName,
        book.bookCategory,
    ){
        id=book.id
    }
}