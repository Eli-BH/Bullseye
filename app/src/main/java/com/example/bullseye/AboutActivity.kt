package com.example.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bullseye.databinding.ActivityAboutBinding
import com.example.bullseye.databinding.ActivityMainBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title= getString(R.string.about_page_title)

        binding.backButton?.setOnClickListener{
            finish()
        }
    }
}