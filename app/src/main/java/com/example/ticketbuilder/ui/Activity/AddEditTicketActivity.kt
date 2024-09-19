package com.example.ticketbuilder.ui.Activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ticketbuilder.R
import com.example.ticketbuilder.ViewModel.TicketViewModel
import com.example.ticketbuilder.ViewModel.TicketViewModelFactory
import com.example.ticketbuilder.databinding.ActivityAddEditTicketBinding
import com.example.ticketbuilder.db.TicketDatabase
import com.example.ticketbuilder.model.Ticket
import com.example.ticketbuilder.repository.TicketRepository
import java.text.SimpleDateFormat
import java.util.*

class AddEditTicketActivity : AppCompatActivity() {
    private lateinit var ticketViewModel: TicketViewModel
    private var ticketId: Int = -1  // Use for editing
    private lateinit var binding: ActivityAddEditTicketBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
             ticketId = bundle.getInt("ticket_id",-1)
        }

        // Get the repository
        val repository = TicketRepository(TicketDatabase.getDatabase(applicationContext).ticketDao())
        // Create the ViewModelFactory
        val viewModelFactory = TicketViewModelFactory(repository)
        // Initialize the ViewModel
        ticketViewModel = ViewModelProvider(this, viewModelFactory).get(TicketViewModel::class.java)

        val priorityDropdown = binding.prioritySpinner
        ArrayAdapter.createFromResource(
            this, R.array.priority_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            priorityDropdown.adapter = adapter
        }

        // If editing, fetch the ticket details and populate the fields
        if (ticketId != -1) {
            ticketViewModel.getTicketById(ticketId).observe(this) { ticket ->
                if (ticket != null) {
                    binding.nameInput.setText(ticket.name)
                    binding.descriptionInput.setText(ticket.description)
                    priorityDropdown.setSelection(
                        (priorityDropdown.adapter as ArrayAdapter<String>)
                            .getPosition(ticket.priority)
                    )
                    binding.dueDateInput.setText(ticket.dueDate)
                }
            }
        }

        // DatePicker logic for the due date input field
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDueDateLabel()
        }

        // Open DatePicker when due date input is clicked
        binding.dueDateInput.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Save button
        binding.saveButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val priority = priorityDropdown.selectedItem.toString()
            val dueDate = binding.dueDateInput.text.toString()

            if (name.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val ticket = Ticket(
                    id = if (ticketId == -1) 0 else ticketId, // If it's a new ticket, id is 0 (Room auto-generates the ID)
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
    // Method to update the due date field with the selected date
    private fun updateDueDateLabel() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        binding.dueDateInput.setText(sdf.format(calendar.time))
    }
}
