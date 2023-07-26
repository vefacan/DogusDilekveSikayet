package com.dogusetiket.dogusdilekvesikayet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private val splashScreen = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        GlobalScope.launch(Dispatchers.Main) {
            delay(splashScreen.toLong())
            val intent = Intent(this@SplashScreen,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}