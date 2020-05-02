package com.example.tdm2_data.exercice4

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdm2_data.R
import com.example.tdm2_data.exercice4.models.Note
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        intent = intent
        val note = intent.getSerializableExtra("NOTE") as Note
        note_title.text = note.title
        note_date.text = note.date
        note_body.text = note.body
        note_card.setBackgroundColor(Color.parseColor("#30" + note.color))

        delete.setOnClickListener {
            deleteNote(note)
        }
    }


    private fun deleteNote(note: Note?) {
        val activity = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = NoteDB.getInstance(activity)
                val dao = db?.noteDAO()
                if (note != null) {
                    dao?.supprimer(note)
                }
                return null
            }


            override fun onPostExecute(result: Void?) {
                returnToList()
            }
        }.execute()
    }

    private fun returnToList() {
        val intent = Intent(this, Main3Activity::class.java)
        intent.putExtra("msg", "Note was deleted!")
        startActivity(intent)
    }

}
