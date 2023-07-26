package com.dogusetiket.dogusdilekvesikayet

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dogusetiket.dogusdilekvesikayet.databinding.ActivityChangePasswordScreenBinding


class ChangePasswordScreen : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.btnChangePassword.setOnClickListener {

            val adminID = binding.editTextID.text.toString()
            val currentPassword = binding.editTextPassword.text.toString()
            val newPassword = binding.editTextNewPassword.text.toString()
            val confirmPassword = binding.editTextRepeatNewPassword.text.toString()

            val savedAdminID = sharedPreferences.getString("adminID", "")
            val savedAdminPW = sharedPreferences.getString("adminPW", "")

            if (adminID.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show()
            } else if (savedAdminPW != currentPassword) {
                Toast.makeText(this, "Mevcut şifrenizi yanlış girdiniz.", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(this, "Yeni şifreler eşleşmiyor.", Toast.LENGTH_SHORT).show()
            } else if (savedAdminID != adminID) {
                Toast.makeText(this, "Mevcut kullanıcı adı yanlış.", Toast.LENGTH_SHORT).show()
            } else {
                // Yeni şifreyi shared preferences'e kaydedin
                val editor = sharedPreferences.edit()
                editor.putString("adminPW", newPassword)
                editor.apply()

                Toast.makeText(this, "Şifre başarıyla değiştirildi.", Toast.LENGTH_SHORT).show()

                // Şifre değiştirme işlemi tamamlandıktan sonra bu activity'i sonlandırın
                finish()
            }

        }


    }
}