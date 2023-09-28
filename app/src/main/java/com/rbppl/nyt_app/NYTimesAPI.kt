package com.rbppl.nyt_app

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTimesAPI {
    @GET("svc/books/v3/lists/names.json?api-key=4GnBYK2LGAnaWl3fi2DyKdDSG7eyqdVc")
    suspend fun getBookLists(): Response<CategoryResponse> // Создаем новую модель BookListResponse
    @GET("svc/books/v3/lists/{publishedDate}/{category}.json")
    suspend fun getBookList(
        @Path("publishedDate") publishedDate: String?,
        @Path("category") category: String?,
        @Query("offset") offset: Int = 0,
        @Query("api-key") apiKey: String
    ): Response<BookListResponse>
}
