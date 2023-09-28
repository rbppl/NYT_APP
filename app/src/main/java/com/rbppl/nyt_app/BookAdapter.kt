package com.rbppl.nyt_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class BookAdapter(private val books: List<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitleTextView: TextView = itemView.findViewById(R.id.bookTitleTextView)
        val bookAuthorTextView: TextView = itemView.findViewById(R.id.bookAuthorTextView)
        val bookDescriptionTextView: TextView = itemView.findViewById(R.id.bookDescriptionTextView)
        val bookPublisherTextView: TextView = itemView.findViewById(R.id.bookPublisherTextView)
        val bookRankTextView: TextView = itemView.findViewById(R.id.bookRankTextView)
        val bookImageView: ImageView = itemView.findViewById(R.id.bookImageView)
        val amazonLinkButton: Button = itemView.findViewById(R.id.amazonLinkButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]

        holder.bookTitleTextView.text = "Title: " + book.title
        holder.bookAuthorTextView.text = "Author: " +  book.author
        holder.bookDescriptionTextView.text = "Description: " + book.description
        holder.bookPublisherTextView.text = "Publisher: " + book.publisher
        holder.bookRankTextView.text = "Rank: " + book.rank.toString()

        // Загрузка изображения с использованием библиотеки Picasso (или другой)
        Picasso.get().load(book.book_image).into(holder.bookImageView)

        // Обработчик события нажатия на кнопку для открытия ссылки Amazon
        holder.amazonLinkButton.setOnClickListener {
            val amazonUrl = book.amazon_product_url
            if (!amazonUrl.isNullOrEmpty()) {
                openAmazonLinkInWebView(it.context, amazonUrl)
            }
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    // Метод для открытия ссылки в WebView
    private fun openAmazonLinkInWebView(context: Context, url: String) {
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra("url", url)
        context.startActivity(intent)
    }
}