package com.example.module5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_edit_note.*

class AddEditNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        // Get intent data if editing an existing note
        val noteId = intent.getIntExtra("note_id", -1)
        val noteTitle = intent.getStringExtra("note_title")
        val noteDesc = intent.getStringExtra("note_desc")

        if (noteId != -1) {
            edit_text_title.setText(noteTitle)
            edit_text_description.setText(noteDesc)
        }

        button_save.setOnClickListener {
            val title = edit_text_title.text.toString().trim()
            val description = edit_text_description.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                edit_text_title.error = "Title is required"
                edit_text_description.error = "Description is required"
                return@setOnClickListener
            }

            val resultIntent = Intent().apply {
                putExtra("note_id", noteId)
                putExtra("note_title", title)
                putExtra("note_desc", description)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
