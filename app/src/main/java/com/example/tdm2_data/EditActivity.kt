package com.example.tdm2_data

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tdm2_data.exercice2.MainActivity
import com.example.tdm2_data.exercice2.models.Intervention
import com.example.tdm2_data.exercice2.models.InterventionsList
import com.example.tdm2_data.exercice3.InterventionDB
import com.example.tdm2_data.exercice3.Main2Activity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.date
import kotlinx.android.synthetic.main.activity_edit.nom_plombier
import kotlinx.android.synthetic.main.activity_edit.numero
import java.io.BufferedReader
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val DATAFILE = cacheDir.absolutePath+"/data.json"
        val intent = intent
        val pos = intent.getIntExtra("pos", 0)
        if (intent.getStringExtra("exo") == "3") { // exercice 3 use room API
            val old = Main2Activity.Interventions?.get(pos)
            var new = old?.copy()

            //init text fields
            numero.setText(old?.numero.toString())
            nom_plombier.setText(old?.nom_plombier)
            intervention_type.setText(old?.type)
            var parsedDate = old?.date!!.split("-")
            Log.d("Lol", parsedDate.toString())
            date.init(parsedDate.get(2).toInt(), parsedDate[1].toInt() - 1, parsedDate[0].toInt()) { view, year, month, day ->
                val month = month + 1
                val msg = "$day-$month-$year"
                new?.date = msg
            }

            edit.setOnClickListener {
                new?.numero = java.lang.Long.valueOf(numero.text.toString())
                new?.nom_plombier = nom_plombier.text.toString()
                new?.type = intervention_type.text.toString()
                editInterventionRoomAPI(new)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("msg", "Intervention was edited!")
                startActivity(intent)
            }

        } else {
            val old = MainActivity.Interventions[pos]
            var new = old.copy()

            //init text fields
            numero.setText(old.numero.toString())
            nom_plombier.setText(old.nom_plombier)
            intervention_type.setText(old.type)
            var parsedDate = old.date.split("-")
            date.init(parsedDate[2].toInt(), parsedDate[1].toInt() - 1, parsedDate[0].toInt()) { view, year, month, day ->
                val month = month + 1
                val msg = "$day-$month-$year"
                new.date = msg
            }
            edit.setOnClickListener {
                new.numero = java.lang.Long.valueOf(numero.text.toString())
                new.nom_plombier = nom_plombier.text.toString()
                new.type = intervention_type.text.toString()
                editIntervention(DATAFILE, old, new)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("msg", "Intervention was edited!")
                startActivity(intent)
            }
        }
    }

    private fun editInterventionRoomAPI(new: com.example.tdm2_data.exercice3.models.Intervention?) {
        val activity = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = InterventionDB.getInstance(activity)
                val dao = db?.interventionDAO()
                if (new != null) {
                    dao?.modifier(new)
                }
                return null
            }


            override fun onPostExecute(result: Void?) {
                returnToList()
            }
        }.execute()
    }

    private fun editIntervention(file:String, old: Intervention, new: Intervention) {
        var gson = Gson()
        val bufferedReader: BufferedReader = File(file).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val interventionsList = gson.fromJson(inputString, InterventionsList::class.java)
        var interventions = interventionsList.interventions
        interventions.remove(old)
        interventions.add(new)
        interventionsList.interventions = interventions

        val jsonString:String = gson.toJson(interventionsList)
        val file= File(file)
        file.writeText(jsonString)
    }

    private fun returnToList() {
        val intent = Intent(this, Main2Activity::class.java)
        intent.putExtra("msg", "Intervention was edited!")
        startActivity(intent)
    }
}
