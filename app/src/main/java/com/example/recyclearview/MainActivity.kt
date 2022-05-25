package com.example.recyclearview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recyclearview.adaptador.PersonajeAdapter
import com.example.recyclearview.modelo.Personaje

class MainActivity : AppCompatActivity() {
    private lateinit var miRecycler: RecyclerView
    private lateinit var listaPersonajes:ArrayList<Personaje>
    private lateinit var adaptador: PersonajeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaPersonajes = ArrayList()
        miRecycler = findViewById(R.id.RecyclerView)
        adaptador = PersonajeAdapter(listaPersonajes)
        miRecycler.adapter = adaptador
        getPersonajes()
        miRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPersonajes(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://rickandmortyapi.com/api/character"
        val objectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            { respuesta ->
                val personajesJson = respuesta.getJSONArray("results")
                for (indice in 0 until personajesJson.length()-1){
                    val personajeIndJson = personajesJson.getJSONObject(indice)
                    val personaje = Personaje(  personajeIndJson.getString("name"),
                        personajeIndJson.getString("image"))
                    listaPersonajes.add(personaje)
                }

                adaptador.notifyDataSetChanged()
            },
            {
                Log.e("PersonajesApi", "Error")
            })

        queue.add(objectRequest)

    }
}