package com.rbppl.nyt_app
// CategoryActivity.kt
import CategoryAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbppl.nyt_app.Category
import com.rbppl.nyt_app.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CategoryAdapter(emptyList(), object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Обработка нажатия на элемент списка
                val selectedCategory = adapter.getItem(position)
                val intent = Intent(this@CategoryActivity, BookListActivity::class.java)
                intent.putExtra("categoryName", selectedCategory.list_name)
                intent.putExtra("publishedDate", selectedCategory.newest_published_date)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NYTimesAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getBookLists()
                if (response.isSuccessful) {
                    val bookListResponse = response.body()
                    val bookLists = bookListResponse?.results ?: emptyList()
                    launch(Dispatchers.Main) {
                        adapter = CategoryAdapter(bookLists, object : CategoryAdapter.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                // Обработка нажатия на элемент списка
                                val selectedCategory = adapter.getItem(position)
                                val intent = Intent(this@CategoryActivity, BookListActivity::class.java)
                                intent.putExtra("categoryName", selectedCategory.list_name)
                                intent.putExtra("publishedDate", selectedCategory.newest_published_date)
                                startActivity(intent)
                            }
                        })
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
