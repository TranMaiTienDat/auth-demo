package com.example.booktracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class BookListAdapter(private val context: Context, private val books: List<Book>) : BaseAdapter() {

    override fun getCount(): Int {
        return books.size
    }

    override fun getItem(position: Int): Any {
        return books[position]
    }

    override fun getItemId(position: Int): Long {
        return books[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val book = books[position]

        val titleTextView = view.findViewById<TextView>(android.R.id.text1)
        val authorTextView = view.findViewById<TextView>(android.R.id.text2)

        titleTextView.text = book.title
        authorTextView.text = book.author

        return view
    }
}
