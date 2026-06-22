package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_requests")
data class ServiceRequest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientName: String,
    val clientPhone: String,
    val category: String, // "Electricista", "Informático", "Técnico de TI"
    val problemDescription: String,
    val status: String = "PENDING", // "PENDING", "ACCEPTED", "IN_PROGRESS", "COMPLETED"
    val matchedEngineerId: Int? = null,
    val requestTime: Long = System.currentTimeMillis(),
    val clientLatitude: Double = -8.8400, // Client Luanda coordinate
    val clientLongitude: Double = 13.2300,
    val engineerLatitude: Double = -8.8400, // Simulated current moving location
    val engineerLongitude: Double = 13.2300,
    val aiAnalysis: String? = null, // Store Gemini-driven safety info & tips
    val rating: Int? = null,
    val feedback: String? = null
)
