package com.ieseljust.pmdm.apac_dam_browser

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class Menus : AppCompatActivity() {
    companion object {
        var activitatActual = 0;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.principal, menu)

        for (i in 0 until menu.size()) {
            menu.getItem(i).isVisible = i != activitatActual
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.goMainMenu -> {
                if (activitatActual != 0) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    activitatActual = 0;
                    startActivity(intent)
                }
                true
            }
            R.id.goFavouritesMenu -> {
                if (activitatActual != 1) {
                    val intent = Intent(this, Favourites::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    activitatActual = 1;
                    startActivity(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}