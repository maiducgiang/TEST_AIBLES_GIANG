package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query("SELECT * from data ORDER BY login ASC")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * from data WHERE id = :id")
    fun getUser(id: Int): Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}