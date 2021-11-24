package com.ieseljust.pmdm.apac_dam_browser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class AdaptadorFavoritos(var favouritesList: List<Favourite>, val eventListener: (Favourite, View) -> Unit, val longEventListener: (Favourite, View) -> Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vista = inflater.inflate(R.layout.favourite, parent, false)
        return favouriteViewHolder(vista)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as favouriteViewHolder).bind(favouritesList[position], eventListener, longEventListener)
    }

    override fun getItemCount(): Int {
        return favouritesList.size

    }

}