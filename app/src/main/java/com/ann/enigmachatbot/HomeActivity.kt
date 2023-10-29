package com.ann.enigmachatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ann.enigmachatbot.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chatCardview.setOnClickListener {
            startActivity(Intent(this,ChatActivity::class.java))
        }
        binding.imageCardview.setOnClickListener {
            startActivity(Intent(this,ImageActivity::class.java))
        }
    }
}