package com.example.contacts.data

import androidx.lifecycle.LiveData
import com.example.contacts.model.Contact
import kotlinx.coroutines.flow.Flow

class ContactRepo(private val contactDao: ContactDao) {

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    suspend fun delete(contact:Contact) {
        contactDao.delete(contact)
    }

    fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts()
    }

    fun searchContacts(searchQuery: String): Flow<List<Contact>> {
        return contactDao.searchContacts("%$searchQuery%")
    }

}