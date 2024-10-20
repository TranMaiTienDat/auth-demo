package com.example.booktracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private val bookList: List<Book>,
    private val onEdit: (Book) -> Unit,
    private val onDelete: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.textViewBookTitle)
        val textViewAuthor: TextView = view.findViewById(R.id.textViewBookAuthor)
        val textViewStatus: TextView = view.findViewById(R.id.textViewBookStatus)
        val buttonEdit: Button = view.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.textViewTitle.text = book.title
        holder.textViewAuthor.text = book.author
        holder.textViewStatus.text = book.status

        // // Set action for edit button
        holder.buttonEdit.setOnClickListener {
            onEdit(book)
        }

        // Set action for delete button
        holder.buttonDelete.setOnClickListener {
            onDelete(book)
        }
    }

    override fun getItemCount(): Int = bookList.size
}
