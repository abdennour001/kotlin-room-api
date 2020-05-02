package com.example.tdm2_data.exercice4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdm2_data.*
import com.skydoves.colorpickerview.listeners.ColorListener
import kotlinx.android.synthetic.main.activity_add_note.*
import android.widget.LinearLayout
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.AsyncTask
import com.example.tdm2_data.exercice4.models.Note
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.activity_add_note.*
import java.util.*


class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        var dateInt = ""
        var color_ = ""
        val today = Calendar.getInstance()

        note_date.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH) ) { view, year, month, day ->
            val month = month + 1
            val msg = "$day-$month-$year"
            dateInt = msg
        }

        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, fromUser ->
            note_card.setBackgroundColor(envelope.color)
            color_ = envelope.hexCode.removePrefix("FF")
        })

        add.setOnClickListener {
            addNote(
                Note(0, note_title.text.toString(), dateInt, color_, note_body.text.toString())
            )
        }
    }

    private fun addNote(note: Note) {
        val activity = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = NoteDB.getInstance(activity)
                val dao = db?.noteDAO()
                dao?.ajouter(note)
                return null
            }


            override fun onPostExecute(result: Void?) {
                returnToList()
            }
        }.execute()
    }

    private fun returnToList() {
        val intent = Intent(this, Main3Activity::class.java)
        intent.putExtra("msg", "Note was created!")
        startActivity(intent)
    }
}
