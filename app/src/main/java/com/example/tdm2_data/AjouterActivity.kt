package com.example.tdm2_data

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdm2_data.exercice2.MainActivity
import com.example.tdm2_data.exercice3.models.Intervention as Int3
import com.example.tdm2_data.exercice2.models.Intervention
import com.example.tdm2_data.exercice2.models.InterventionsList
import com.example.tdm2_data.exercice3.InterventionDB
import com.example.tdm2_data.exercice3.Main2Activity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_ajouter.*
import java.io.BufferedReader
import java.io.File
import java.util.*

class AjouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter)
        val DATAFILE = cacheDir.absolutePath+"/data.json"
        val today = Calendar.getInstance()
        var dateInt = ""
        val intent = intent

        date.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH) ) { view, year, month, day ->
            val month = month + 1
            val msg = "$day-$month-$year"
            dateInt = msg
        }

        if (intent.getStringExtra("exo") == "3") { // exercice 3 use room API
            messageButton.setOnClickListener{
                addInterventionRoomAPI(Int3(java.lang.Long.valueOf(numero.text.toString()),
                    nom_plombier.text.toString(),
                    intervention_type.text.toString(),
                    dateInt))
            }
        } else {
            messageButton.setOnClickListener{
                addIntervention(DATAFILE, Intervention(java.lang.Long.valueOf(numero.text.toString()),
                    nom_plombier.text.toString(),
                    dateInt,
                    intervention_type.text.toString()))
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("msg", "Intervention was created!")
                startActivity(intent)
            }
        }
    }

    private fun addIntervention(file:String, intervention:Intervention) {
        var gson = Gson()
        val bufferedReader: BufferedReader = File(file).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val interventionsList = gson.fromJson(inputString, InterventionsList::class.java)
        var interventions = interventionsList.interventions
        interventions.add(intervention)
        interventionsList.interventions = interventions

        val jsonString:String = gson.toJson(interventionsList)
        val file= File(file)
        file.writeText(jsonString)
    }

    private fun addInterventionRoomAPI(intervention: Int3) {
        val activity = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = InterventionDB.getInstance(activity)
                val dao = db?.interventionDAO()
                dao?.ajouter(intervention)
                return null
            }


            override fun onPostExecute(result: Void?) {
                returnToList()
            }
        }.execute()
    }

    private fun returnToList() {
        val intent = Intent(this, Main2Activity::class.java)
        intent.putExtra("msg", "Intervention was created!")
        startActivity(intent)
    }
}
