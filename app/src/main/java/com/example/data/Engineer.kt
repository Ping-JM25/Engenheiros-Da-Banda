package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "engineers")
data class Engineer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String,
    val specialty: String, // "Electricista", "Informático", "Técnico de TI"
    val skills: String,
    val certId: String, // Generated unique credential (e.g. EB-2026-X84)
    val experienceYears: Int,
    val description: String,
    val isOnline: Boolean = true,
    val latitude: Double = -8.8368, // Default Luanda, Angola coords
    val longitude: Double = 13.2331,
    val rating: Float = 4.9f,
    val email: String = "",
    val password: String = "123456",
    val workHistory: String = ""
)
