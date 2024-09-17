import androidx.lifecycle.LiveData

class TicketRepository (private val ticketDao: TicketDao) {

    val allTickets: LiveData<List<Ticket>> = ticketDao.getAllTickets()

    suspend fun insert(ticket: Ticket) {
        ticketDao.insert(ticket)
    }

    suspend fun update(ticket: Ticket) {
        ticketDao.update(ticket)
    }

    suspend fun delete(ticket: Ticket) {
        ticketDao.delete(ticket)
    }
}
