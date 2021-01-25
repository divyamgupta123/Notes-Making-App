package com.example.makenotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.makenotes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), INotesRVAdapter {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Grid Layout with 2 columns in each row
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)
        val adapter = NotesRecyclerViewAdapter(this, this)
        binding.recyclerview.adapter = adapter

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                NoteViewModel::class.java
            )
        viewModel.allNotes.observe(this@MainActivity, { list ->
            list?.let {
                adapter.updateList(it)
            }
        })
    }

    //Deleting the note
    override fun onDeleteItemCLicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_SHORT).show()
    }

    // Activity for viewing full Note and editing it
    override fun onItemClicked(note: Note) {
        val intent = Intent(this, FullNote::class.java)
        intent.putExtra("noteTitle", note.title)
        intent.putExtra("noteText", note.text)
        id = note.id
        startActivityForResult(intent, 2)
    }

    //Button CLick for making a new note
    fun fabSubmit(view: View) {
        val intent = Intent(this, CreateNote::class.java)
        startActivityForResult(intent, 1)
    }

    //Getting the result back from the intents
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val noteTitle = data!!.getStringExtra("Title")
                val noteText = data.getStringExtra("Text")
                val date = data.getStringExtra("Date")
                if (noteText != null && noteTitle != null && date != null) {
                    viewModel.insertNote(Note(noteTitle, noteText, date))
                }

            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                var noteTitle = data!!.getStringExtra("newTitle")
                val noteText = data.getStringExtra("newText")
                if (noteText != null && noteTitle != null) {
                    if (noteTitle.isEmpty()) {
                        noteTitle = "Untitled"
                    }
                    viewModel.updateNote(noteTitle, noteText, id)
                }
            }
        }
    }
}