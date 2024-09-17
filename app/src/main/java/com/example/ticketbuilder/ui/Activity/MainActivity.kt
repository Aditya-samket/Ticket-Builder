package com.example.ticketbuilder.ui.Activity

import Ticket
import TicketDao
import TicketDatabase
import TicketViewModelFactory
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketbuilder.R
import com.example.ticketbuilder.ViewModel.TicketViewModel
import com.example.ticketbuilder.databinding.ActivityMainBinding
import com.example.ticketbuilder.ui.Adapter.TicketListAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var adapter: TicketListAdapter
    private var dao: TicketDao? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and Adapter
        initVars()

        // Initialize the Room database
        val database = TicketDatabase.getDatabase(this)
        val ticketDao = database.ticketDao()


//        GlobalScope.launch {
//            database.ticketDao().insert(Ticket(0,"Sample Ticket", "This is a sample description", "High", "2023-11-25"))
//        }
        // Observe data from Room DB
        ticketDao.getAllTickets().observe(this, Observer { tickets ->
            tickets?.let {
                adapter.updateTickets(it)
            }
        })

        // RecyclerView setup
      /* val recyclerView = binding.recyclerView
        adapter = TicketListAdapter(emptyList()) { ticket ->
            // On item click, navigate to detail screen
            val intent = Intent(this, TicketDetailActivity::class.java).apply {
                putExtra("ticket_id", ticket.id)
            }
            startActivity(intent)
        }*/

        // Add a new ticket to the DB
        binding.addTicketButton.setOnClickListener {
            val newTicket = Ticket(0,"Sample Ticket", "This is a sample description", "High", "2023-11-25")
            ticketViewModel.insert(newTicket)
//           startActivity(Intent(this, AddEditTicketActivity::class.java))
        }
    }

    private fun initVars() {
//        dao = TicketDatabase.getDatabase(this).ticketDao()
        adapter = TicketListAdapter(emptyList()) { ticket ->
            // Handle ticket click event (e.g., navigate to detail screen)
            val intent = Intent(this, TicketDetailActivity::class.java).apply {
                putExtra("ticket_id", ticket.id)
            }
            startActivity(intent)
        }

        // Set up the RecyclerView
        binding.recyclerView.apply {
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.recyclerView.adapter = adapter
        }

//        ticketViewModel = ViewModelProvider(this)[TicketViewModel::class.java]
//        val factory = TicketViewModelFactory(dao!!)
//        ticketViewModel = ViewModelProvider(this, factory)[TicketViewModel::class.java]
//        val application = requireNotNull(this).application
//        val ticketDatabase = TicketDatabase.getDatabase(application)
        // Initialize the ViewModel
//        val viewModelFactory = TicketViewModelFactory(ticketDatabase.ticketDao(), application)
//        ticketViewModel = ViewModelProvider(this, viewModelFactory)[TicketViewModel::class.java]
    }
}