package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
class HHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_http)
        //metodoGet()
        metodoPost()
    }

    fun metodoGet() {
        "https://jsonplaceholder.typicode.com/posts/"
            .httpGet()
            .responseString{
                    request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error :${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "Error :${postString}")
                        val arreglo = Klaxon()
                            .parseArray<IPostHttp>(postString)
                        if (arreglo != null) {
                            arreglo
                                .forEach{
                                    Log.i("http-klaxon", "Error :${it?.title}")
                                }
                        }

                    }
                }
            }
    }
    fun metodoPost() {
        val parametros: List<Pair<String, *>> = listOf(
            "title" to "titullo Moviles",
            "body" to "descripcion moviles",
            "userId" to 1
        )
        "https://jsonplaceholder.typicode.com/posts/"
            .httpPost(parametros)
            .responseString{
                    request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Error :${error}")
                    }
                    is Result.Success -> {
                        val postString = result.get()
                        Log.i("http-klaxon", "Error :${postString}")
                        val post = Klaxon()
                            .parseArray<IPostHttp>(postString)
                        Log.i("http-klaxon", "Error :${post}")
                    }
                }
            }
    }
}