package com.example.movilescomputacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.*
import com.github.kittinunf.result.Result
class HHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_http)

        //  metodoGetAll()
       // metodoGet()
       // metodoPost()
       //  metodoUpdate(1)
       // metodoDelete(1)
        metodoBusquedaUserId(3)
    }

    fun metodoGetAll() {
        "https://jsonplaceholder.typicode.com/posts"
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
                                    Log.i("http-klaxon", "Titulos :${it?.title}")
                                }
                        }

                    }
                }
            }
    }

    fun metodoGet() {
        "https://jsonplaceholder.typicode.com/posts/1"
                .httpGet()
                .responseString { req, res, result ->
                    when (result) {
                        is Result.Failure -> {
                            val error = result.getException()
                            Log.i("http-klaxon", "Error: ${error}")
                        }
                        is Result.Success -> {
                            val postString = result.get()
                            val post = Klaxon()
                                    .parse<IPostHttp>(postString)
                            Log.i("http-klaxon", "Titulo del post:  ${post?.title}")
                        }
                    }
                }
    }

    fun metodoPost() {
        val parametros: List<Pair<String, String>> = listOf(
                "title" to "titulo moviles",
                "body" to "descripcion moviles",
                "userId" to "1"
        )

        "https://jsonplaceholder.typicode.com/posts"
                .httpPost(parametros)
                .responseString { req, res, result ->
                    when (result) {
                        is Result.Failure -> {
                            val error = result.getException()
                            Log.i("http-klaxon", "Error: ${error}")
                        }
                        is Result.Success -> {
                            val postString = result.get()
                            val post = Klaxon()
                                    .parse<IPostHttp>(postString)
                            Log.i("http-klaxon", "${post?.title}")
                        }
                    }
                }
    }

    fun  metodoUpdate(
            id: Int
    ) {

        val parametros: List<Pair<String, String>> = listOf(
                "title" to "Titulo moviles version actualiado",
                "body" to "descripcion moviles version actualizado",
                "userId" to "1"
        )
        "https://jsonplaceholder.typicode.com/posts/${id}"
                .httpPut(parametros)
                .responseString { req, res, result ->
                    when (result) {
                        is Result.Failure -> {
                            val error = result.getException()
                            Log.i("http-klaxon", "Error ${error}")
                        }
                        is Result.Success -> {
                            /*val usuarioString = result.get()
                            Log.i("http-klaxon", "${usuarioString}")*/
                            val postString = result.get()
                            Log.i("http-klaxon", "Post Actualizado:${postString}")
                            val posts = Klaxon()
                                    .parse<IPostHttp>(postString)
                            Log.i("http-klaxon", "titulo  de l post actualizado: ${posts?.title}")
                        }
                    }
                }
    }

    fun metodoDelete(
            id:Int
    ) {
        "https://jsonplaceholder.typicode.com/posts/${id}"
                .httpDelete()
                .responseString { req, res, result ->
                    when (result) {
                        is Result.Failure -> {
                            val error = result.getException()
                            Log.i("http-klaxon", "Error: ${error}")
                        }
                        is Result.Success -> {
                            val postString = result.get()
                            Log.i("http-klaxon", "${postString}")
                        }
                    }
                }
    }

    fun metodoBusquedaUserId(
            userId: Int
    ) {
        "https://jsonplaceholder.typicode.com/posts?userId=${userId}"
                .httpGet()
                .responseString{ req, res, result ->
                    when(result){
                        is Result.Failure -> {
                            val error = result.getException()
                            Log.i("http-klaxon", "Error ${error}")
                        }
                        is Result.Success -> {
                            val postString = result.get()
                            Log.i("http-klaxon", "${postString}")
                            val arregloPosts = Klaxon()
                                    .parseArray<IPostHttp>(postString)

                        }
                    }
                }
    }

    /* fun metodoPost() {
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
     }*/
}