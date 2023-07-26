package com.dogusetiket.dogusdilekvesikayet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dogusetiket.dogusdilekvesikayet.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnEmployee.setOnClickListener {
            val intent = Intent(this@MainActivity,EmployeeScreen::class.java)
            startActivity(intent)
        }

        binding.btnAdmin.setOnClickListener {
            val intent = Intent(this@MainActivity,AdminLoginScreen::class.java)
            startActivity(intent)
        }


    }
}