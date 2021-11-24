package com.ieseljust.pmdm.apac_dam_browser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ieseljust.pmdm.apac_dam_browser.databinding.ActivityFavouritesBinding

data class Favourite(val nombre: String, val url: String)

class Favourites : Menus() {
    lateinit var binding: ActivityFavouritesBinding

    companion object {
        val DIRECCION: String = ""
        var favouritesList = ArrayList<Favourite>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Menus.activitatActual = 1
        super.onCreate(savedInstanceState)

        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (favouritesList.size == 0){
            favouritesList = defaultFavourites()
        }

        binding.FavouritesRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.FavouritesRecyclerView.setHasFixedSize(true)

        binding.FavouritesRecyclerView.adapter = AdaptadorFavoritos(favouritesList, {fav: Favourite, v: View -> favouriteClicked(fav, v)},{fav: Favourite, v: View -> longFavouriteClicked(fav, v)})
    }

    fun defaultFavourites(): ArrayList<Favourite> {
        var favouritesList = ArrayList<Favourite>()

        favouritesList.add(Favourite("Moodle", "https://moodle.ieseljust.com/"))
        favouritesList.add(Favourite("Google", "https://www.google.com/"))
        favouritesList.add(Favourite("YouTube", "https://youtube.com/"))

        return favouritesList
    }

    private fun favouriteClicked(fav: Favourite, v: View){
        var dir = fav.url
        val intent = Intent(this, MainActivity::class.java).apply{
            putExtra(DIRECCION, dir)
        }
        startActivity(intent)
    }

    private fun longFavouriteClicked(fav: Favourite, v: View): Boolean{
        var confirmacion = AlertDialog.Builder(this)
        confirmacion.setTitle("Confirm")
        confirmacion.setMessage("¿Quieres borrar este marcador de favoritos?")
        confirmacion.setPositiveButton(android.R.string.ok) { _, _ ->
            favouritesList.remove(fav)
            var pos = binding.FavouritesRecyclerView.getChildAdapterPosition(v)
            binding.FavouritesRecyclerView.adapter?.notifyItemRemoved(pos)

            Toast.makeText(this, "Eliminado ${fav.nombre}", Toast.LENGTH_SHORT).show()
        }
        confirmacion.setNegativeButton(android.R.string.cancel) { _, _ ->
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
        }

        confirmacion.show()

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {

        }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        Menus.activitatActual = 1
    }
}