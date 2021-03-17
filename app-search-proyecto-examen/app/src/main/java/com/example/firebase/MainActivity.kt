package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager.widget.ViewPager
import com.example.firebase.databinding.ActivityMainBinding
import com.example.firebase.fragments.*
import com.example.firebase.fragments.adapters.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mPager = findViewById(R.id.viewPager)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setUpTabBar()
    }

    private fun setUpTabBar() {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Home_fragment(), "Home Search")
        adapter.addFragment(Reserva_fragment(), "Reservas" )
        adapter.addFragment(Favorito_fragment(),"Favoritos")
        adapter.addFragment(Perfil_fragment(),"Perfil")
        mPager.adapter = adapter



        binding.bottonNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.home -> mPager.currentItem = 0
                R.id.reserva -> mPager.currentItem = 1
                R.id.favorito -> mPager.currentItem = 2
                R.id.perfile -> mPager.currentItem = 3
            }
        }



    }
}