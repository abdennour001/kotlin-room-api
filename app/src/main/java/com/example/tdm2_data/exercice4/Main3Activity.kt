package com.example.tdm2_data.exercice4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdm2_data.R
import com.example.tdm2_data.exercice4.models.Note
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    private var db: NoteDB? = null
    private var dao: NoteDAO? = null

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var adapter: RecyclerAdapter
    private lateinit var noteList:RecyclerView
    companion object {
        internal var notesList: List<Note>? = null

//        listOf(
//        Note(1, "TDM2 project", "2-5-2020", "red", "don't forget to put the project in the classroom platform"),
//        Note(2, "TDM2 project", "2-5-2020", "red", "don't forget to put the project in the classroom platform"),
//        Note(3, "TDM2 project", "2-5-2020", "red", "don't forget to put the project in the classroom platform"),
//        Note(4, "TDM2 project", "2-5-2020", "red", "don't forget to put the project in the classroom platform")
//        )
    }

    override fun onStart() {
        super.onStart()

        getNotes()

    }

    private fun getNotes() {
        initDB()
    }

    private fun changeLayoutManager() {
        if (noteList.layoutManager == linearLayoutManager) {
            noteList.layoutManager = gridLayoutManager
            if (notesList?.size == 1) {
                getNotes()
            }
        } else {
            noteList.layoutManager = linearLayoutManager
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(toolbar)

        val intent = intent
        if (intent.getStringExtra("msg") != null) {
            Toast.makeText(this, intent.getStringExtra("msg"), Toast.LENGTH_LONG).show()
        }

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initDB() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                act.db = NoteDB.getInstance(act)
                act.dao = db?.noteDAO()
                notesList = act.dao?.getNotes() as ArrayList<Note>
                return null
            }

            override fun onPostExecute(result: Void?) {
                noteList = findViewById<RecyclerView>(R.id.note_list)
                linearLayoutManager = LinearLayoutManager(act)
                gridLayoutManager = GridLayoutManager(act, 2)
                noteList.layoutManager = gridLayoutManager
                adapter = RecyclerAdapter(notesList)
                noteList.adapter = adapter
                adapter.notifyItemRangeInserted(noteList.size-1, noteList.size)

            }
        }.execute()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            changeLayoutManager()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
