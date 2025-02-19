package com.example.module5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NoteAdapter { note ->
            val intent = Intent(this, AddEditNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
                putExtra("note_title", note.title)
                putExtra("note_desc", note.description)
            }
            startActivityForResult(intent, EDIT_NOTE_REQUEST)
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        noteViewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }

        fab_add_note.setOnClickListener {
            startActivityForResult(Intent(this, AddEditNoteActivity::class.java), ADD_NOTE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val id = data.getIntExtra("note_id", -1)
            val title = data.getStringExtra("note_title") ?: return
            val description = data.getStringExtra("note_desc") ?: return

            if (requestCode == ADD_NOTE_REQUEST) {
                val newNote = Note(title = title, description = description)
                noteViewModel.insert(newNote)
            } else if (requestCode == EDIT_NOTE_REQUEST && id != -1) {
                val updatedNote = Note(id = id, title = title, description = description)
                noteViewModel.update(updatedNote)
            }
        }
    }

    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }
}
