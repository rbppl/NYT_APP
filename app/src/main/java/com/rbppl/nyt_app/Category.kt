package com.rbppl.nyt_app


data class Category(
    val list_name: String,
    val newest_published_date: String,
    val display_name: String // Добавляем поле display_name
)