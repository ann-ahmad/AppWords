package com.example.appwords

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class WordAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val word = getItem(position)

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)

        val wordTextView: TextView = view.findViewById(android.R.id.text1)
        wordTextView.text = word

        return view
    }
}
