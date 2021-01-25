package com.example.makenotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesRecyclerViewAdapter(
    private val context: Context,
    private val listener: INotesRVAdapter
) : RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.noteText)!!
        val titleView = itemView.findViewById<TextView>(R.id.noteTitle)!!
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)!!
        val dateView = itemView.findViewById<TextView>(R.id.dateText)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder =
            NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
        viewHolder.deleteButton.setOnClickListener {
            listener.onDeleteItemCLicked(allNotes[viewHolder.adapterPosition])
        }

        viewHolder.itemView.setOnClickListener {
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.textView.text = currentNote.text
        holder.titleView.text = currentNote.title
        holder.dateView.text = "Created On:- ${currentNote.date}"


    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newsList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newsList)
        notifyDataSetChanged()
    }

}

interface INotesRVAdapter {
    fun onDeleteItemCLicked(note: Note)
    fun onItemClicked(note: Note)
}