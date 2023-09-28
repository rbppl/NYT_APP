package com.rbppl.nyt_app
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        recyclerView = findViewById(R.id.bookRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BookAdapter(emptyList())
        recyclerView.adapter = adapter

        val categoryName = intent.getStringExtra("categoryName")
        val publishedDate = intent.getStringExtra("publishedDate")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NYTimesAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getBookList(publishedDate, categoryName, apiKey = "4GnBYK2LGAnaWl3fi2DyKdDSG7eyqdVc")
                if (response.isSuccessful) {
                    val bookListResponse = response.body()
                    val books = bookListResponse?.results?.books ?: emptyList()
                    launch(Dispatchers.Main) {
                        adapter = BookAdapter(books)
                        recyclerView.adapter = adapter
                    }
                } else {
                    // Обработка ошибки при запросе
                }
            } catch (e: Exception) {
                // Обработка исключений, например, отсутствия интернет-соединения
            }
        }
    }
}
