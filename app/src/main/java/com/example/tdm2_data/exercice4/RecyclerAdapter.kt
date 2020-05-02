package com.example.tdm2_data.exercice4

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tdm2_data.R
import com.example.tdm2_data.exercice4.models.Note
import kotlinx.android.synthetic.main.note_list_item.view.*

class RecyclerAdapter(private val notes: List<Note>?)
    : RecyclerView.Adapter<RecyclerAdapter.NoteHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.NoteHolder {
        val inflatedView = parent.inflate(R.layout.note_list_item, false)
        return NoteHolder(inflatedView)
    }

    override fun getItemCount(): Int = notes?.size!!

    override fun onBindViewHolder(holder: RecyclerAdapter.NoteHolder, position: Int) {
        val itemNote = notes?.get(position)
        if (itemNote != null) {
            holder.bindNote(itemNote)
        }
    }


    class NoteHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var note: Note? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
            val context = itemView.context
            val showPhotoIntent = Intent(context, NoteActivity::class.java)
            showPhotoIntent.putExtra(NOTE_KEY, note)
            context.startActivity(showPhotoIntent)
        }

        fun bindNote(note: Note) {
            Log.d("note", note.toString())
            this.note = note
            view.note_card.setBackgroundColor(Color.parseColor("#50" + note.color))
            view.note_title.text = note.title
            view.note_date.text = note.date
            view.note_body.text = note.body
        }

        companion object {
            private const val NOTE_KEY = "NOTE"
        }
    }

}
