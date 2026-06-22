package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Engineer::class, ServiceRequest::class], version = 2, exportSchema = false)
abstract class BandaDatabase : RoomDatabase() {
    abstract fun engineerDao(): EngineerDao
    abstract fun serviceRequestDao(): ServiceRequestDao

    companion object {
        @Volatile
        private var INSTANCE: BandaDatabase? = null

        fun getDatabase(context: Context): BandaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BandaDatabase::class.java,
                    "banda_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
