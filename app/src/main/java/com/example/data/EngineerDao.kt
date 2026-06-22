package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EngineerDao {
    @Query("SELECT * FROM engineers WHERE isOnline = 1 AND specialty = :specialty")
    fun getOnlineEngineersBySpecialty(specialty: String): Flow<List<Engineer>>

    @Query("SELECT * FROM engineers WHERE isOnline = 1")
    fun getAllOnlineEngineers(): Flow<List<Engineer>>

    @Query("SELECT * FROM engineers")
    fun getAllEngineers(): Flow<List<Engineer>>

    @Query("SELECT * FROM engineers WHERE id = :id LIMIT 1")
    suspend fun getEngineerById(id: Int): Engineer?

    @Query("SELECT * FROM engineers WHERE phone = :phone LIMIT 1")
    suspend fun getEngineerByPhone(phone: String): Engineer?

    @Query("SELECT * FROM engineers WHERE phone = :identifier OR email = :identifier LIMIT 1")
    suspend fun getEngineerByPhoneOrEmail(identifier: String): Engineer?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEngineer(engineer: Engineer): Long

    @Update
    suspend fun updateEngineer(engineer: Engineer)

    @Query("DELETE FROM engineers WHERE id = :id")
    suspend fun deleteEngineerById(id: Int)
}
