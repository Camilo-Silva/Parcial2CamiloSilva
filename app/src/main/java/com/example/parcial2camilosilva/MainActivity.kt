package com.example.parcial2camilosilva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var textViewChiste: TextView

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewChiste = findViewById(R.id.textViewJokesRandom)

        button = findViewById(R.id.buttonCategory)

        button.setOnClickListener {
            //El intent sirve para accionar y pasar de una actividad a otra
            val intent = Intent(this, CategoryActivity::class.java)
            //Agregar datos al enviar a la otra activity por la sentencia inten
            startActivity(intent)
        }

        getRandomJoke()
    }

    private fun getRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getJokes()
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val chiste = response?.value

                    textViewChiste.text = chiste

                } else {
                    val error = call.errorBody().toString()
                    Log.e("error", error)
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}