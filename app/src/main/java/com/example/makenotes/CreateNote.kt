package com.example.makenotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.makenotes.databinding.ActivityCreateNoteBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CreateNote : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @SuppressLint("SimpleDateFormat")
    fun submitData(view: View) {
        var noteTitle = binding.noteTitleInput.text.toString()
        val noteText = binding.noteTextInput.text.toString()

        //Getting the current Date
        date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            current.format(formatter)
        } else {
            val currentDate = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy")
            formatter.format(currentDate)
        }
        // if the Title is empty make it "Untitled"
        if (noteTitle.isEmpty()) noteTitle = "Untitled"
        if (noteText.isNotEmpty()) {
            val resultIntent = Intent()
            resultIntent.putExtra("Title", noteTitle)
            resultIntent.putExtra("Text", noteText)
            resultIntent.putExtra("Date", date)
            setResult(RESULT_OK, resultIntent)
            Toast.makeText(this, "$noteText Inserted", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(
                this@CreateNote,
                "PLease Enter the content of the Note",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}