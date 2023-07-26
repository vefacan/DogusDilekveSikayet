package com.dogusetiket.dogusdilekvesikayet.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DilekveSikayet::class], version = 1,
)

abstract class DilekveSikayetDatabase : RoomDatabase() {
    abstract fun getDilekveSikayetDao(): DilekveSikayetDao

}
