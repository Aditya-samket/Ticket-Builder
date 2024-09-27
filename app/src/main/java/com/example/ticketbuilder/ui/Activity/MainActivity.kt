package com.example.ticketbuilder.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticketbuilder.R
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

        // Set up sorting Spinner
        ArrayAdapter.createFromResource(this, R.array.sorting_options, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sortSpinner.adapter = adapter
        }

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedOption = parent.getItemAtPosition(position) as String
                when (selectedOption) {
                    "Date Ascending" -> sortTicketsAscending()
                    "Date Descending" -> sortTicketsDescending()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No-op
            }
        }
    }

    private fun sortTicketsAscending() {
        val sortedList = ticketViewModel.allTickets.value?.sortedBy { it.dueDate }
        adapter.submitList(sortedList)
    }

    private fun sortTicketsDescending() {
        val sortedList = ticketViewModel.allTickets.value?.sortedByDescending { it.dueDate }
        adapter.submitList(sortedList)
    }

    override fun onItemClick(ticket: Ticket) {
        val intent = Intent(this, TicketDetailActivity::class.java).apply {
            putExtra("ticket_id", ticket.id)
        }
        startActivity(intent)
    }
}