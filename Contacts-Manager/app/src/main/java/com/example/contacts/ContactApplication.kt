package com.example.contacts

import android.app.Application
import com.example.contacts.data.ContactDatabase
import com.example.contacts.data.ContactRepo

class ContactApplication: Application() {

    val database by lazy { ContactDatabase.getDatabase(this) }
    val repository by lazy { ContactRepo(database.contactDao()) }

}