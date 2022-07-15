package com.myproject.hse

import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso

class CharacterInfo : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        val image = findViewById<ImageView>(R.id.imageView);
        val planet = findViewById<TextView>(R.id.planet);
        val location = findViewById<TextView>(R.id.location);


        val body  = intent.extras?.getString("character");
        val gson = GsonBuilder().create();

        val character = gson.fromJson(body, Characters::class.java);

        if (character != null){
            Picasso.with(applicationContext).load(character.results[0].image).into(image)
            planet.text = character!!.results[0].origin.name;
            location.text = character!!.results[0].location.name;

        }

    }



}