package com.dogusetiket.dogusdilekvesikayet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dogusetiket.dogusdilekvesikayet.databinding.ActivityAdminLoginScreenBinding

class AdminLoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAdminLoginScreenBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onResume() {
        super.onResume()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        var getAdminID = sharedPreferences.getString("adminID", "")
        var getAdminPW = sharedPreferences.getString("adminPW", "")

        if (getAdminID!!.isEmpty() && getAdminPW!!.isEmpty()) {
            editor.putString("adminID", "admin")
            editor.putString("adminPW", "123")
            editor.apply()
        } else {
            getAdminID = sharedPreferences.getString("adminID", "")
            getAdminPW = sharedPreferences.getString("adminPW", "")
        }


        binding.btnLogin.setOnClickListener {

            if (binding.editTextID.text.toString() == getAdminID && binding.editTextPassword.text.toString() == getAdminPW) {

                binding.editTextID.text!!.clear()
                binding.editTextPassword.text!!.clear()

                val intent = Intent(this@AdminLoginScreen, DataResultScreen::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@AdminLoginScreen,
                    "Lütfen kullanıcı adı ve şifrenizi doğru girin.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        binding.txtLoginScreenChangePassword.setOnClickListener {
            val intent = Intent(this@AdminLoginScreen, ChangePasswordScreen::class.java)
            startActivity(intent)
        }
    }
}