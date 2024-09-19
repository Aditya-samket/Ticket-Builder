package com.example.ticketbuilder.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ticketbuilder.db.TicketDatabase
import com.example.ticketbuilder.model.Ticket
import com.example.ticketbuilder.repository.TicketRepository
import kotlinx.coroutines.launch

class TicketViewModel(private val repository: TicketRepository) : ViewModel() {

    val allTickets = repository.allTickets

    fun insert(ticket: Ticket) = viewModelScope.launch {
        repository.insert(ticket)
    }

    fun update(ticket: Ticket) = viewModelScope.launch {
        repository.update(ticket)
    }

    fun delete(ticket: Ticket) = viewModelScope.launch {
        repository.delete(ticket)
    }

    fun getTicketById(ticketId: Int): LiveData<Ticket> {
        return repository.getTicketById(ticketId)
    }
}

class TicketViewModelFactory(private val repository: TicketRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TicketViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
