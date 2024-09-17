package com.example.ticketbuilder.ui.Activity

import Ticket
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ticketbuilder.R.layout
import com.example.ticketbuilder.ViewModel.TicketViewModel
import com.example.ticketbuilder.databinding.ActivityTicketDetailBinding

class TicketDetailActivity : AppCompatActivity() {

    private lateinit var ticketViewModel: TicketViewModel
    private var ticketId: Int = -1  // Store ticket ID
    private lateinit var binding: ActivityTicketDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(layout.activity_ticket_detail)

        // Retrieve the ticket ID passed from the MainActivity
        ticketId = intent.getIntExtra("ticket_id", -1)

        // Initialize the ViewModel
        ticketViewModel = ViewModelProvider(this)[TicketViewModel::class.java]

        // Observe ticket details and display them
        ticketViewModel.allTickets.observe(this, Observer { tickets ->
            val ticket = tickets.find { it.id == ticketId }
            if (ticket != null) {
                displayTicketDetails(ticket)
            }
        })

        // Edit button click listener
        binding.editButton.setOnClickListener {
            val intent = Intent(this, AddEditTicketActivity::class.java).apply {
                putExtra("ticket_id", ticketId)
            }
            startActivity(intent)
        }

        // Delete button click listener
        binding.deleteButton.setOnClickListener {
            ticketViewModel.allTickets.observe(this, Observer { tickets ->
                val ticketToDelete = tickets.find { it.id == ticketId }
                if (ticketToDelete != null) {
                    ticketViewModel.delete(ticketToDelete)
                    finish()  // Close the activity after deletion
                }
            })
        }
    }

    // Function to display the details in the UI
    private fun displayTicketDetails(ticket: Ticket) {
        binding.ticketDetailName.text = ticket.name
        binding.ticketDetailDescription.text = ticket.description
        binding.ticketDetailPriority.text = ticket.priority.capitalize()
        binding.ticketDetailDueDate.text = if (ticket.dueDate.isNullOrEmpty()) "No Due Date" else ticket.dueDate
    }
}
