package com.example.contacts.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contacts.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY LOWER(name) ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contact WHERE name LIKE :searchQuery")
    fun searchContacts(searchQuery: String): Flow<List<Contact>>

}