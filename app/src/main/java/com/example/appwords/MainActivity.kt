package com.example.appwords


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var letterListView: ListView
    private lateinit var wordListView: ListView
    private lateinit var letterButtonContainer: LinearLayout

    private val wordList = hashMapOf(
        'A' to listOf("Apple", "Android", "Airplane"),
        'B' to listOf("Banana", "Book", "Ball"),
        'C' to listOf("Caramel", "Cheek", "Cook"),
        'D' to listOf("Dark", "Deep", "Dust"),
        'E' to listOf("Eagle", "Echo", "Endure"),
        'F' to listOf("Faint", "Far", "Fool"),
        'G' to listOf("Gap", "Give", "Grow"),
        'H' to listOf("Hall", "Hire", "Honk"),
        'I' to listOf("Ignore", "Ill", "Image"),
        'J' to listOf("Jam", "Jingle", "Juice"),
        'K' to listOf("Kale", "Keep", "Kind"),
        'L' to listOf("Lard", "Liquid", "Loud"),
        'M' to listOf("March", "Miss", "Month"),
        'N' to listOf("Nap", "Need", "Nice"),
        'O' to listOf("Oat", "Off", "Out"),
        'P' to listOf("Pan", "Peak", "Pinch"),
        'Q' to listOf("Quiet", "Quit", "Quite"),
        'R' to listOf("Roll", "Ranch", "Round"),
        'S' to listOf("Salary", "Sorry", "Sour"),
        'T' to listOf("Tart", "Tour", "Ten"),
        'U' to listOf("Up", "Under", "Use"),
        'V' to listOf("Vas", "View", "Valley"),
        'W' to listOf("Wall", "Wound", "Wonder"),
        'X' to listOf("Xylophone", "Xavier", "Xylem"),
        'Y' to listOf("Yawn", "Year", "You"),
        'Z' to listOf("Zip", "Zero", "Zoo"),
        // ... tambahkan huruf dan kata-kata lainnya
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)
        letterListView = findViewById(R.id.letterListView)
        wordListView = findViewById(R.id.wordListView)
        letterButtonContainer = findViewById(R.id.letterButtonContainer)

        val adapter = WordAdapter(this, android.R.layout.simple_list_item_1, emptyList())
        wordListView.adapter = adapter

        letterListView.setOnItemClickListener { _, _, position, _ ->
            val selectedLetter = (position + 'A'.toInt()).toChar()
            val wordsForLetter = wordList[selectedLetter] ?: emptyList()
            adapter.clear()
            adapter.addAll(wordsForLetter)
        }

        wordListView.setOnItemClickListener { _, _, position, _ ->
            val selectedWord = adapter.getItem(position)
            openBrowserForDefinition(selectedWord)
        }

        searchEditText.setOnKeyListener { _, _, _ ->
            val query = searchEditText.text.toString().toUpperCase()

            if (query.isNotEmpty()) {
                val filteredWords = wordList.filterKeys { it.toString() == query }
                    .values.flatten()
                adapter.clear()
                adapter.addAll(filteredWords)
            } else {
                adapter.clear()
            }

            true
        }

        setupLetterListView()
    }

    private fun setupLetterListView() {
        val letters = ('A'..'Z').map { it.toString() }.toList()
        val letterAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, letters)
        letterListView.adapter = letterAdapter

        letters.forEach { letter ->
            val letterButton = Button(this)
            letterButton.text = letter
            letterButton.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))
            letterButton.setOnClickListener { onLetterButtonClick(letter) }
            letterButtonContainer.addView(letterButton)
        }
    }

    private fun onLetterButtonClick(letter: String) {
        val selectedLetter = letter[0]
        val wordsForLetter = wordList[selectedLetter] ?: emptyList()
        (wordListView.adapter as WordAdapter).clear()
        (wordListView.adapter as WordAdapter).addAll(wordsForLetter)
    }

    private fun openBrowserForDefinition(word: String?) {
        word?.let {
            val uri = Uri.parse("https://www.google.com/search?q=define+$word")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}

