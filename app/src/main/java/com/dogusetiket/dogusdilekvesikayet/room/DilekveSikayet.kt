package com.dogusetiket.dogusdilekvesikayet.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dilekvesikayet")
data class DilekveSikayet(
    @PrimaryKey(autoGenerate = true) var case_id: Int?,
    var case_group: String,
    var case_name: String,
    var case_body: String,
    var person_name: String,
    var tc_no: String,
    var date: String,
    var isChecked: String = "Kontrol Ediliyor...",
    var isExpandable: Boolean = false
)
