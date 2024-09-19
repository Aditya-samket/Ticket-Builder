package com.example.ticketbuilder.ui.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticketbuilder.ViewModel.TicketViewModel
import com.example.ticketbuilder.ViewModel.TicketViewModelFactory
import com.example.ticketbuilder.databinding.ActivityMainBinding
import com.example.ticketbuilder.db.TicketDatabase
import com.example.ticketbuilder.model.Ticket
import com.example.ticketbuilder.repository.TicketRepository
import com.example.ticketbuilder.ui.Adapter.TicketListAdapter

class MainActivity : AppCompatActivity(), TicketListAdapter.OnItemClickedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ticketViewModel: TicketViewModel
    private lateinit var adapter: TicketListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and Adapter
        adapter = TicketListAdapter(this)

        // Initialize the Room database
        val database = TicketDatabase.getDatabase(this)
        val ticketDao = database.ticketDao()
        val repository = TicketRepository(ticketDao)

        ticketViewModel = ViewModelProvider(this, TicketViewModelFactory(repository))[TicketViewModel::class.java]
        ticketViewModel.allTickets.observe(this){
            adapter.submitList(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Add a new ticket to the DB
        binding.addTicketButton.setOnClickListener {
           startActivity(Intent(this, AddEditTicketActivity::class.java))
        }
    }

    override fun onItemClick(ticket: Ticket) {
        val intent = Intent(this, TicketDetailActivity::class.java).apply {
            putExtra("ticket_id", ticket.id)
        }
        startActivity(intent)
    }
}