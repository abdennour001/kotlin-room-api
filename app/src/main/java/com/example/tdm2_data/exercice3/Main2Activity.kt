package com.example.tdm2_data.exercice3

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import com.example.tdm2_data.AjouterActivity
import com.example.tdm2_data.InterventionActivity
import com.example.tdm2_data.R
import com.example.tdm2_data.exercice2.Adapter
import com.example.tdm2_data.exercice3.models.Intervention
import kotlinx.android.synthetic.main.activity_main.*

class Main2Activity : AppCompatActivity() {

    private var db: InterventionDB? = null
    private var dao: InterventionDAO? = null
    companion object {
        internal var Interventions: List<com.example.tdm2_data.exercice3.models.Intervention>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        val intent = intent
        if (intent.getStringExtra("msg") != null) {
            Toast.makeText(this, intent.getStringExtra("msg"), Toast.LENGTH_LONG).show()
        }

        initDB()

        fab.setOnClickListener { view ->
            val intent = Intent(this, AjouterActivity::class.java)
            intent.putExtra("exo","3")
            startActivity(intent)
        }
    }

    private fun initDB() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                act.db = InterventionDB.getInstance(act)
                act.dao = db?.interventionDAO()
                Interventions = act.dao?.getInterventions() as ArrayList<Intervention>
                return null
            }

            override fun onPostExecute(result: Void?) {
                if(Interventions != null) {
                    val adapter = Main2Activity.Interventions?.let { AdapterRoomAPI(act, it) }
                    val interventionsList = act.findViewById<ListView>(R.id.interventions_list)
                    interventionsList.adapter = adapter
                    interventionsList.setOnItemClickListener { parent, view, position, id ->
                        val intent = Intent(act, InterventionActivity::class.java)
                        intent.putExtra("position", position)
                        intent.putExtra("exo", "3")
                        startActivity(intent)
                    }
                    //Toast.makeText(act, "Interventions ready!", Toast.LENGTH_SHORT).show()
                }
            }
        }.execute()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
