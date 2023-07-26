package com.dogusetiket.dogusdilekvesikayet.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DilekveSikayetDao {


    @Query("SELECT * FROM dilekvesikayet")
    fun getAllCase(): List<DilekveSikayet>

    @Insert
    fun addCase(dilekveSikayet: DilekveSikayet)

    @Delete
    fun deleteCase(dilekveSikayet: DilekveSikayet)

    @Update
    fun updateCase(dilekveSikayet: DilekveSikayet)

    @Query("DELETE FROM dilekvesikayet")
    fun deleteAllCase()

    @Query("SELECT * FROM dilekvesikayet WHERE case_group like '%' || :groupCases || '%'")
    fun getGroupPersons(groupCases: String): List<DilekveSikayet>


}