package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceRequestDao {
    @Query("SELECT * FROM service_requests ORDER BY requestTime DESC")
    fun getAllRequests(): Flow<List<ServiceRequest>>

    @Query("SELECT * FROM service_requests WHERE status != 'COMPLETED' ORDER BY requestTime DESC")
    fun getActiveRequests(): Flow<List<ServiceRequest>>

    @Query("SELECT * FROM service_requests WHERE matchedEngineerId = :engineerId AND status != 'COMPLETED' ORDER BY requestTime DESC")
    fun getActiveRequestsForEngineer(engineerId: Int): Flow<List<ServiceRequest>>

    @Query("SELECT * FROM service_requests WHERE clientPhone = :clientPhone ORDER BY requestTime DESC")
    fun getRequestsByClient(clientPhone: String): Flow<List<ServiceRequest>>

    @Query("SELECT * FROM service_requests WHERE id = :id LIMIT 1")
    suspend fun getRequestById(id: Int): ServiceRequest?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: ServiceRequest): Long

    @Update
    suspend fun updateRequest(request: ServiceRequest)

    @Query("DELETE FROM service_requests")
    suspend fun clearAll()
}
