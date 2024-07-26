package com.example.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.contacts.data.ContactRepo
import com.example.contacts.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepo): ViewModel() {

    val allContacts: LiveData<List<Contact>> = repository.getAllContacts().asLiveData()

    private val _searchedContactsFlow = MutableStateFlow<List<Contact>>(emptyList())
    val searchedContactsFlow: StateFlow<List<Contact>> get() = _searchedContactsFlow

    // Using StateFlow to expose the Flow as a LiveData
    fun searchContacts(query: String) = viewModelScope.launch {
        repository.searchContacts(query)
            .collect { _searchedContactsFlow.value = it }
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
    }

    class ContactViewModelFactory(private val repository: ContactRepo) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ContactViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}