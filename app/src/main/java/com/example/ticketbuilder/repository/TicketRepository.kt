package com.example.ticketbuilder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.ticketbuilder.dao.TicketDao
import com.example.ticketbuilder.db.TicketDatabase
import com.example.ticketbuilder.model.Ticket

class TicketRepository (val ticketDao: TicketDao) {

    val allTickets: LiveData<List<Ticket>> = ticketDao.getAllTickets()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ticket: Ticket) = ticketDao.insert(ticket)

    suspend fun update(ticket: Ticket) = ticketDao.update(ticket)

    suspend fun delete(ticket: Ticket) = ticketDao.delete(ticket)

    fun getTicketById(ticketId: Int): LiveData<Ticket> {
        return ticketDao.getTicketById(ticketId)
    }

    companion object {
        @Volatile
        private var INSTANCE: TicketRepository? = null

        fun getRepository(context: Context): TicketRepository {
            return INSTANCE ?: synchronized(this) {
                val database = TicketDatabase.getDatabase(context)
                val instance = TicketRepository(database.ticketDao())
                INSTANCE = instance
                instance
            }
        }
    }
}
