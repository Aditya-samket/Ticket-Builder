package com.example.ticketbuilder.ui.Activity

import Ticket
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ticketbuilder.R
import com.example.ticketbuilder.ViewModel.TicketViewModel
import com.example.ticketbuilder.databinding.ActivityAddEditTicketBinding

class AddEditTicketActivity : AppCompatActivity() {
    private lateinit var ticketViewModel: TicketViewModel
    private var ticketId: Int = -1  // Use for editing
    private lateinit var binding: ActivityAddEditTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTicketBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_edit_ticket)

        // Retrieve ViewModel
        ticketViewModel = ViewModelProvider(this)[TicketViewModel::class.java]

        val priorityDropdown = binding.prioritySpinner
        ArrayAdapter.createFromResource(
            this, R.array.priority_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            priorityDropdown.adapter = adapter
        }

        // Save button
        binding.saveButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val priority = priorityDropdown.selectedItem.toString()
            val dueDate = binding.dueDateInput.text.toString()

            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val ticket = Ticket(
                    id = ticketId,
                    name = name,
                    description = description,
                    priority = priority,
                    dueDate = dueDate
                )
                if (ticketId == -1) {
                    ticketViewModel.insert(ticket)
                } else {
                    ticketViewModel.update(ticket)
                }
                finish()
            }
        }
    }
}
