package com.example.gamefirstpoc

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast

class ModeOfGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mode_of_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val numberButton: Button = findViewById(R.id.Number)
        val friendButton: Button = findViewById(R.id.Friend)
        val aiButton: Button = findViewById(R.id.AI)

        aiButton.setOnClickListener {


            Toast.makeText(this@ModeOfGame, "This mode is under development", Toast.LENGTH_LONG).show()
        }

        friendButton.setOnClickListener {

            val intent = Intent(this@ModeOfGame, FriendMode::class.java)
            startActivity(intent)
        }

        numberButton.setOnClickListener {

            val intent = Intent(this@ModeOfGame, mainPage::class.java)
            startActivity(intent)

        }
    }
}