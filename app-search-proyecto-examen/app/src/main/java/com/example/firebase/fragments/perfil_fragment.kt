package com.example.firebase.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.firebase.Negocio
import com.example.firebase.R


class Perfil_fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_perfil_fragment, container, false)
        val btnNegocios: Button  = view.findViewById(R.id.btn_negocios) as Button
        btnNegocios.setOnClickListener {

            activity?.let{
                val intent = Intent (it, Negocio::class.java)
                it.startActivity(intent)
            }
            Log.i("perfile", "click button perfile")
        }
        return view
    }
}