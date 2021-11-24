package com.ieseljust.pmdm.apac_dam_browser

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.ieseljust.pmdm.apac_dam_browser.Favourites.Companion.DIRECCION
import com.ieseljust.pmdm.apac_dam_browser.Favourites.Companion.favouritesList
import com.ieseljust.pmdm.apac_dam_browser.databinding.ActivityMainBinding

class MainActivity : Menus() {
    lateinit var binding:com.ieseljust.pmdm.apac_dam_browser.databinding.ActivityMainBinding
    private var currentUrl: String = ""

    companion object {
        val URL: String = "https://www.google.com/"
        val ICON: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Menus.activitatActual = 0
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.WebView.webViewClient = object : WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                currentUrl = url.toString()
                updateURL()
                binding.urlText.setText(url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                currentUrl = url.toString()
                updateURL()
                binding.urlText.setText(url)
            }
        }

        binding.WebView.settings.javaScriptEnabled = true

        currentUrl = savedInstanceState?.getString(URL) ?: URL
        val cargar:String? = intent.getStringExtra(DIRECCION)
        if (cargar != null)
            currentUrl = cargar
        binding.WebView.loadUrl(currentUrl)

        binding.urlText.setOnEditorActionListener { v, actionId, event ->
            currentUrl = checkUrl(binding.urlText.text.toString())
            updateURL()
            binding.WebView.loadUrl(currentUrl)
            true
        }

        binding.imageButton.setOnClickListener {
            if (!isFavorite(currentUrl))
                favouritesList.add(Favourite(binding.WebView.title.toString(), currentUrl))
            else
                Toast.makeText(this, "Entra a Favoritos para borrar la página.", Toast.LENGTH_SHORT).show()
            updateURL()
        }

    }

    override fun onBackPressed() {
        if (binding.WebView.canGoBack()){
            binding.WebView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun updateURL() {
        if (isFavorite(currentUrl))
            binding.imageButton.setImageResource(android.R.drawable.btn_star_big_on)
        else
            binding.imageButton.setImageResource(android.R.drawable.btn_star_big_off)
    }

    private fun checkUrl(text: String): String {
        var validUrl = ""
        val http = text.split(":")
        if (http[0] == "http" || http[0] == "https") {
            if (http[0] == "http") {
                validUrl += "https://"
                validUrl += text.substring(7)
            } else {
                validUrl = text
            }
        } else {
            validUrl += "https://"
            validUrl += text
        }
        return validUrl
    }

    private fun isFavorite(url: String): Boolean{
        for (f:Favourite in favouritesList){
            if (f.url == url)
                return true
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(URL, currentUrl)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        Menus.activitatActual = 0
        updateURL()
    }
}