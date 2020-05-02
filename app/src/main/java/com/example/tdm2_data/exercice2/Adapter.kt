package com.example.tdm2_data.exercice2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.tdm2_data.R
import com.example.tdm2_data.exercice2.models.Intervention


class Adapter(val context: Context, val data: List<Intervention>):BaseAdapter() {

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position].numero
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        val holder:ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.interventions_list_item,parent,false)
            val numeroIntervention = view.findViewById(R.id.numero_intervention) as TextView
            val nomPlombier = view.findViewById(R.id.nom_plombier) as TextView
            val dateIntervention = view.findViewById(R.id.intervention_date) as TextView
            val typeIntrevention = view.findViewById(R.id.intervention_type) as TextView
            holder = ViewHolder(numeroIntervention,nomPlombier,dateIntervention, typeIntrevention)
            view.tag = holder
        }
        else {
            holder = view.tag as ViewHolder
        }
        holder.numeroIntervention.text = "Intervention #${data[position].numero}"
        holder.nomPlombier.text = data[position].nom_plombier
        holder.dateIntervention.text = data[position].date
        holder.typeIntrevention.text = data[position].type
        return view
    }

    private class ViewHolder(val numeroIntervention: TextView,val nomPlombier: TextView, val dateIntervention: TextView, val typeIntrevention: TextView)

}