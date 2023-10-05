package com.example.emotionalface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emotionalface.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emoFace4.setOnClickListener {
            binding.emoFace.happinessState = EmoFace.SAD
        }
        binding.emoFace3.setOnClickListener {
            binding.emoFace.happinessState = EmoFace.HAPPY
        }
    }
}