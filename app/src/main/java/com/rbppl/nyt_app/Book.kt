package com.rbppl.nyt_app

data class Book(
    val title: String,
    val description: String,
    val author: String,
    val publisher: String,
    val book_image: String,
    val rank: Int,
    val amazon_product_url: String
) {

}
