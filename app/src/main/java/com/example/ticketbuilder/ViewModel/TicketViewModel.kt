package com.example.ticketbuilder.ViewModel

import Ticket
import TicketDao
import TicketDatabase
import TicketRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TicketViewModel(ticketDao: TicketDao, application: Application) : AndroidViewModel(application) {
    private val repository: TicketRepository
    val allTickets: LiveData<List<Ticket>>

    init {
        val ticketDao = TicketDatabase.getDatabase(application).ticketDao()
        repository = TicketRepository(ticketDao)
        allTickets = repository.allTickets
    }

    fun insert(ticket: Ticket) = viewModelScope.launch {
        repository.insert(ticket)
    }

    fun update(ticket: Ticket) = viewModelScope.launch {
        repository.update(ticket)
    }

    fun delete(ticket: Ticket) = viewModelScope.launch {
        repository.delete(ticket)
    }
}