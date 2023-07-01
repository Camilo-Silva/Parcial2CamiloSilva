package com.example.parcial2camilosilva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryActivity : AppCompatActivity() {

    private var listOfCategories: MutableList<String> = mutableListOf()
    private lateinit var adapter: JokesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = JokesAdapter(listOfCategories)
        recyclerView.adapter = adapter

        getListOfCategories()
    }

    private fun getListOfCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getJokesCategory()
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val categorias = response
                    if (categorias != null) {
                        categorias?.forEach { categoria ->
                            listOfCategories.add(categoria)
                        }
                        adapter.notifyDataSetChanged()
                    }

                } else {
                    val error = call.errorBody().toString()
                    Log.e("error", error, )
                }
                adapter.setOnItemClickListener { category ->
                    val intent = Intent(this@CategoryActivity, JokeCategoryActivity::class.java)
                    intent.putExtra("category", category)
                    startActivity(intent)
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

