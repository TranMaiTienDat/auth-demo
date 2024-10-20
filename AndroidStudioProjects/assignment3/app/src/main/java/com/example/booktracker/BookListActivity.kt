package com.example.booktracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookListActivity : AppCompatActivity() {
    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var buttonAddBook: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookList: MutableList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)
        buttonAddBook = findViewById(R.id.buttonAddBook)
        databaseHelper = DatabaseHelper(this)

        //RecyclerView Setup
        recyclerViewBooks.layoutManager = LinearLayoutManager(this)

        // Download book list
        loadBooks()

        // Set action for add book button
        buttonAddBook.setOnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        }
    }

    private fun loadBooks() {
        bookList = databaseHelper.getAllBooks()

        // Create adapter and assign to RecyclerView
        bookAdapter = BookAdapter(bookList, { book ->
            // Book correction processing
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("BOOK_ID", book.id)
            startActivity(intent)
        }, { book ->
            // Book deletion processing
            databaseHelper.deleteBook(book.id)
            loadBooks() // Reload book list
            Toast.makeText(this, "Deleted ${book.title}", Toast.LENGTH_SHORT).show()
        })

        recyclerViewBooks.adapter = bookAdapter
    }
}
