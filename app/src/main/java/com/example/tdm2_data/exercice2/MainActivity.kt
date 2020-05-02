package com.example.tdm2_data.exercice2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import com.example.tdm2_data.InterventionActivity
import com.example.tdm2_data.R
import com.example.tdm2_data.exercice2.models.Intervention
import com.example.tdm2_data.exercice2.models.InterventionsList
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import kotlin.collections.ArrayList
import com.example.tdm2_data.AjouterActivity


class MainActivity : AppCompatActivity() {

    companion object {
        internal var Interventions: List<Intervention> = ArrayList<Intervention>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val intent = intent
        if (intent.getStringExtra("msg") != null) {
            Toast.makeText(this, intent.getStringExtra("msg"), Toast.LENGTH_LONG).show()
        }

        val DATAFILE = cacheDir.absolutePath+"/data.json"


        //get data
        Interventions = getData(DATAFILE)

        //init adapter
        val adapter = Adapter(this, Interventions)
        val interventionsList = this.findViewById<ListView>(R.id.interventions_list)
        interventionsList.adapter = adapter
        interventionsList.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, InterventionActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }

        fab.setOnClickListener { view ->
            val intent = Intent(this, AjouterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData(file:String): List<Intervention> {
        val interventions = readInterventions(file)

        return interventions
    }

    private fun readInterventions(file: String):ArrayList<Intervention> {

        var gson = Gson()
        val bufferedReader: BufferedReader = File(file).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        var interventionsList = gson.fromJson(inputString, InterventionsList::class.java)

        Log.v("Read file", interventionsList.toString())

        return interventionsList.interventions
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
