package com.example.ticketbuilder.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ticketbuilder.dao.TicketDao
import com.example.ticketbuilder.model.Ticket

@Database(entities = [Ticket::class], version = 1, exportSchema = false)
abstract class TicketDatabase : RoomDatabase() {

    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var INSTANCE: TicketDatabase? = null

        fun getDatabase(context: Context): TicketDatabase {
           synchronized(this) {
               if (INSTANCE == null) {
                   INSTANCE = Room.databaseBuilder(
                       context,
                       TicketDatabase::class.java,
                       "ticket_database"
                   ).build()
               }
               return INSTANCE!!
           }
        }
    }
}
