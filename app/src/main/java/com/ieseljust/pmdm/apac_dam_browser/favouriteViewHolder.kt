package com.ieseljust.pmdm.apac_dam_browser

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class favouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nombre = itemView.findViewById(R.id.nombre) as TextView
    val url = itemView.findViewById(R.id.urlRV) as TextView
    val myView = itemView

    fun bind(fv: Favourite, eventListener: (Favourite, View) -> Unit, longEventListener: (Favourite, View) -> Boolean) {
        nombre.text = fv.nombre
        url.text = fv.url
        myView.setOnLongClickListener {longEventListener(fv, myView)}
        myView.setOnClickListener{eventListener(fv, myView)}
    }
}