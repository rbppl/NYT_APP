package com.rbppl.nyt_app

data class CategoryResponse(
    val status: String,
    val copyright: String,
    val num_results: Int,
    val results: List<Category>
)
