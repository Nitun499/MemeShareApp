package com.example.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    var currentImageUrl :String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

    private fun loadMeme(){
        progressBar.visibility=View.VISIBLE
        // Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"

    // Request a JSON response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,null,
            { response ->
                 currentImageUrl=response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(memeImageView)
            },
            { error: VolleyError? ->
                Toast.makeText(this,"Something went wrng",Toast.LENGTH_LONG).show()
            }
        )

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)
    }

    fun ShareMemeFun(view: View) {
       val sendIntent =Intent(Intent.ACTION_SEND)
        sendIntent.type="text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT,"Hey ,look i got cool meme"+"\n" +currentImageUrl)
        val chooser=Intent.createChooser(sendIntent,"Share this meme using..")
        startActivity(chooser)
    }
    fun NextMemeFun(view: View) {
        loadMeme()
    }
}