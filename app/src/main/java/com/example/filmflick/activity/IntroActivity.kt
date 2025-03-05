package com.example.filmflick.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.filmflick.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)


        val sloganText: TextView = findViewById(R.id.sloganText)


        val fadeIn = ObjectAnimator.ofFloat(sloganText, "alpha", 0f, 1f)
        fadeIn.duration = 2000 //
        fadeIn.start()


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 4000)
    }
}
