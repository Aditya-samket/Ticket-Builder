import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ticketbuilder.ViewModel.TicketViewModel

class TicketViewModelFactory(private val ticketDao: TicketDao, private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            return TicketViewModel(ticketDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
