package com.dogusetiket.dogusdilekvesikayet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.dogusetiket.dogusdilekvesikayet.adapter.DogusAdapter
import com.dogusetiket.dogusdilekvesikayet.databinding.ActivityDataResultScreenBinding
import com.dogusetiket.dogusdilekvesikayet.room.DilekveSikayet
import com.dogusetiket.dogusdilekvesikayet.room.DilekveSikayetDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DataResultScreen : AppCompatActivity() {

    private lateinit var binding: ActivityDataResultScreenBinding
    private lateinit var db: DilekveSikayetDatabase
    private var adapter: DogusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataResultScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Room.databaseBuilder(
            this,
            DilekveSikayetDatabase::class.java,
            "appDataBase"
        ).build()

        binding.dataResultRecyclerview.setHasFixedSize(true)
        binding.dataResultRecyclerview.layoutManager = LinearLayoutManager(this)
        adapter = DogusAdapter()
        binding.dataResultRecyclerview.adapter = adapter


        GlobalScope.launch(Dispatchers.IO) {
            val status = db.getDilekveSikayetDao().getAllCase()
            withContext(Dispatchers.Main) {
                adapter?.apply {
                    addItems(status as ArrayList<DilekveSikayet>)
                    notifyDataSetChanged()
                }
            }
        }

        binding.btnDilek.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val status = db.getDilekveSikayetDao().getGroupPersons("Dilek")
                withContext(Dispatchers.Main) {
                    adapter?.apply {
                        addItems(status as ArrayList<DilekveSikayet>)
                        notifyDataSetChanged()
                    }
                }
            }


        }

        binding.btnSikayet.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val status = db.getDilekveSikayetDao().getGroupPersons("Şikayet")
                withContext(Dispatchers.Main) {
                    adapter?.apply {
                        addItems(status as ArrayList<DilekveSikayet>)
                        notifyDataSetChanged()
                    }
                }
            }

        }

        /* "Bütün Verileri Sil" tuşuna bastığımızda önce bir alert dialog ile koşul sunulur.
            Daha sonra koşul "Evet" ise database'de bulunan bütün veriler silinir.
            Koşul "Hayır" ise hiçbir işlem yapılmaz.
         */
        binding.btnDeleteAllData.setOnClickListener {

            var titleView = layoutInflater.inflate(R.layout.custom_alert, null)
            var alert = AlertDialog.Builder(this)
            alert.setCustomTitle(titleView)
            alert.setMessage("Bu işlemle bütün veriler silinecektir. Onaylıyor musunuz?")
            alert.setPositiveButton("Evet") { dialogInterface, i ->

                //Database'den veriler silinir
                GlobalScope.launch(Dispatchers.IO) {
                    db.getDilekveSikayetDao().deleteAllCase()
                    withContext(Dispatchers.Main) {
                        adapter?.notifyDataSetChanged()
                    }
                }
                Toast.makeText(
                    this@DataResultScreen,
                    "Bütün veriler siliniyor...",
                    Toast.LENGTH_SHORT
                )
                    .show()

                val intent = Intent(this, DataResultScreen::class.java)
                finish()
                startActivity(intent)
            }
            alert.setNegativeButton("Hayır") { dialogInterface, i ->
            }
            alert.show()
        }
    }
}
