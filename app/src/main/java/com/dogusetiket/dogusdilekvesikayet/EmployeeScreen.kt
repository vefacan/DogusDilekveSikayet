package com.dogusetiket.dogusdilekvesikayet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.dogusetiket.dogusdilekvesikayet.databinding.ActivityEmployeeScreenBinding
import com.dogusetiket.dogusdilekvesikayet.room.DilekveSikayet
import com.dogusetiket.dogusdilekvesikayet.room.DilekveSikayetDatabase
import java.text.SimpleDateFormat
import java.util.*

class EmployeeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeScreenBinding

    // Room Database değer atama
    private lateinit var db: DilekveSikayetDatabase


    // onResume içine atandı çünkü sadee sayfa oluşurken değil uygulama devam ettiği sürece çalışsın istiyoruz.
    override fun onResume() {
        super.onResume()
        // DropDownMenu String Arrayini oluşturup atama
        val groups = resources.getStringArray(R.array.Gruplar)
        val arrayGroupAdapter = ArrayAdapter(this@EmployeeScreen, R.layout.dropdown_item, groups)
        binding.editTextGroup.setAdapter(arrayGroupAdapter)


        val names = resources.getStringArray(R.array.Kisiler)
        val arrayNameAdapter = ArrayAdapter(this@EmployeeScreen, R.layout.dropdown_item, names)
        binding.editTextName.setAdapter(arrayNameAdapter)

        val topics = resources.getStringArray(R.array.Konular)
        val arrayTopicAdapter = ArrayAdapter(this@EmployeeScreen, R.layout.dropdown_item, topics)
        binding.editTexTitle.setAdapter(arrayTopicAdapter)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Database'e erişim
        db = Room.databaseBuilder(
            this,
            DilekveSikayetDatabase::class.java,
            "appDataBase"
        ).build()

        binding.imgInfo.setOnClickListener {
            var titleView = layoutInflater.inflate(R.layout.custom_alert_info, null)
            var alert = AlertDialog.Builder(this)
            alert.setCustomTitle(titleView)
            alert.setMessage("Başka bir personelin sizin adınıza dilek veya şikayette bulunmaması için anonim olmadığınız sürece Tc kimlik numaranızın son 3 rakamını girmeniz gerekiyor.")
            alert.setPositiveButton("Tamam") { dialogInterface, i ->
            }
            alert.show()

        }

        binding.checkboxAnonim.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                // CheckBox işaretliyse, EditText'i koyulaştır ve etkinleştirme
                binding.editTextName.alpha = 0.3f
                binding.editTextName.isEnabled = false
                binding.editTextName.setText("Anonim")
                binding.editTextTcNo.alpha = 0.3f
                binding.editTextTcNo.isEnabled = false
            } else {
                // CheckBox işaretli değilse, EditText'i etkinleştir ve normal rengine getir
                binding.editTextName.alpha = 1.0f
                binding.editTextName.isEnabled = true
                binding.editTextTcNo.alpha = 1.00f
                binding.editTextTcNo.isEnabled = true
                binding.editTextName.text?.clear()
                binding.outlinedTextFieldName.hint = "İsim ve Soyisim Seçmek İçin Tıklayın"
            }
        }

        binding.btnSend.setOnClickListener {
            // Gönder tuşuna bastığımızda bütün EditTextler'de değer varsa başarılı olarak geri dönüş yap ve verileri ekle.
            if (binding.editTextGroup.text!!.isNotEmpty() && binding.editTextName.text!!.isNotEmpty() &&
                binding.editTexTitle.text!!.isNotEmpty() && binding.editTextBody.text!!.isNotEmpty()
            ) {

                val group = binding.editTextGroup.text.toString()
                val name = binding.editTextName.text.toString()
                val title = binding.editTexTitle.text.toString()
                val body = binding.editTextBody.text.toString()
                val tcNo = binding.editTextTcNo.text.toString()

                val currentDateTime = Calendar.getInstance().time

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(currentDateTime)

                val run = Runnable {
                    val dilekveSikayet = DilekveSikayet(
                        null, group, title, body, name, tcNo, formattedDate)
                    db.getDilekveSikayetDao().addCase(dilekveSikayet)
                }
                Thread(run).start()

                Toast.makeText(
                    this@EmployeeScreen,
                    "Başarıyla kaydedildi. Emeğiniz için teşekkürler :)",
                    Toast.LENGTH_SHORT
                ).show()

                binding.editTextGroup.text!!.clear()
                binding.editTextName.text!!.clear()
                binding.editTexTitle.text!!.clear()
                binding.editTextBody.text!!.clear()
                binding.editTextTcNo.text!!.clear()

                // Gönder tuşuna bastığımızda EditText'lerden en az birinde değer yoksa başarısız olarak geri dönüş yap.
            } else {
                Toast.makeText(
                    this@EmployeeScreen,
                    "Lütfen bütün alanları doldurunuz.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}