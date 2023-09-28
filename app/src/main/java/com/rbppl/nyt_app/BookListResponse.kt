package com.rbppl.nyt_app

data class BookListResponse(
    val status: String,
    val copyright: String,
    val num_results: Int,
    val results: BookListResults
)

data class BookListResults(
    val books: List<Book>
)
