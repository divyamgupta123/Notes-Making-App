package com.example.makenotes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.makenotes.databinding.ActivityFullNoteBinding

class FullNote : AppCompatActivity() {
    lateinit var binding: ActivityFullNoteBinding
    lateinit var gbtitle: String
    lateinit var gbtext: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var title = intent.getStringExtra("noteTitle")
        val text = intent.getStringExtra("noteText")

        if (title!!.isEmpty()) title = "Untitled"

        if (text != null) {
            binding.fullNoteTitle.setText(title)
            binding.fullNoteText.setText(text)
            gbtitle = title
            gbtext = text
        }


    }

    //Update the content when Back Button is pressed
    override fun onBackPressed() {
        val newTitle: String = binding.fullNoteTitle.text.toString()
        val newText: String = binding.fullNoteText.text.toString()
        if (newTitle != gbtitle || newText != gbtext) {
            val resultIntent = Intent()
            resultIntent.putExtra("newTitle", newTitle)
            resultIntent.putExtra("newText", newText)
            setResult(RESULT_OK, resultIntent)
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
            finish()
        }
        super.onBackPressed()
    }
}

