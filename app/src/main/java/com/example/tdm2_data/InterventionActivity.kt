package com.example.tdm2_data

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tdm2_data.exercice2.MainActivity
import com.example.tdm2_data.exercice2.models.Intervention
import com.example.tdm2_data.exercice2.models.InterventionsList
import com.example.tdm2_data.exercice3.InterventionDB
import com.example.tdm2_data.exercice3.Main2Activity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_intervention.*
import java.io.BufferedReader
import java.io.File

class InterventionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intervention)
        val DATAFILE = cacheDir.absolutePath+"/data.json"

        val intent = intent
        val position = intent.getIntExtra("position",0)

        if (intent.getStringExtra("exo") == "3") { // exercice 3 use room API
            numero_intervention.text =
                """Intervention #${Main2Activity.Interventions?.get(position)?.numero}"""
            nom_plombier.text = Main2Activity.Interventions?.get(position)?.nom_plombier
            intervention_date.text = Main2Activity.Interventions?.get(position)?.date
            intervention_type.text = Main2Activity.Interventions?.get(position)?.type


            delete.setOnClickListener {
                deleteInterventionRoomAPI(Main2Activity.Interventions?.get(position))
            }

            edit.setOnClickListener {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("pos",  position)
                intent.putExtra("exo",  "3")
                startActivity(intent)
            }




        } else {
            numero_intervention.text =
                """Intervention #${MainActivity.Interventions[position].numero}"""
            nom_plombier.text = MainActivity.Interventions[position].nom_plombier
            intervention_date.text = MainActivity.Interventions[position].date
            intervention_type.text = MainActivity.Interventions[position].type


            delete.setOnClickListener {
                deleteIntervention(DATAFILE, MainActivity.Interventions[position])
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("msg", "Intervention was deleted!")
                startActivity(intent)
            }

            edit.setOnClickListener {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("pos",  position)
                startActivity(intent)
            }
        }
    }

    private fun deleteInterventionRoomAPI(intervention: com.example.tdm2_data.exercice3.models.Intervention?) {
        val activity = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val db = InterventionDB.getInstance(activity)
                val dao = db?.interventionDAO()
                if (intervention != null) {
                    dao?.supprimer(intervention)
                }
                return null
            }


            override fun onPostExecute(result: Void?) {
                returnToList()
            }
        }.execute()
    }

    private fun deleteIntervention(file:String, i: Intervention) {
        var gson = Gson()
        val bufferedReader: BufferedReader = File(file).bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        val interventionsList = gson.fromJson(inputString, InterventionsList::class.java)
        var interventions = interventionsList.interventions
        interventions.remove(i)
        interventionsList.interventions = interventions

        val jsonString:String = gson.toJson(interventionsList)
        val file= File(file)
        file.writeText(jsonString)
    }

    private fun returnToList() {
        val intent = Intent(this, Main2Activity::class.java)
        intent.putExtra("msg", "Intervention was deleted!")
        startActivity(intent)
    }
}
