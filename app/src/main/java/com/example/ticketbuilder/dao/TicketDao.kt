package com.example.ticketbuilder.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy
import com.example.ticketbuilder.model.Ticket

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ticket: Ticket)

    @Update
    suspend fun update(ticket: Ticket)

    @Delete
    suspend fun delete(ticket: Ticket)

    @Query("SELECT * FROM ticket_table ORDER BY id DESC")
    fun getAllTickets(): LiveData<List<Ticket>>

    @Query("SELECT * FROM ticket_table WHERE id = :ticketId")
    fun getTicketById(ticketId: Int): LiveData<Ticket>
}
