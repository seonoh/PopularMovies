package com.example.seonoh.popularmovies.ui.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.seonoh.popularmovies.R
import com.example.seonoh.popularmovies.databinding.ActivityMainBinding
import com.example.seonoh.popularmovies.ui.single_movie_details.SingleMovie

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.run {
//            btn.setOnClickListener {
//                val intent = Intent(this@MainActivity, SingleMovie::class.java)
//                intent.putExtra("id",299534)
//                startActivity(intent)
//            }
        }
    }
}