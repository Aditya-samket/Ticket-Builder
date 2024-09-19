package com.example.ticketbuilder.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketbuilder.databinding.TicketItemBinding
import com.example.ticketbuilder.model.Ticket
import java.text.SimpleDateFormat
import java.util.Locale

class TicketListAdapter(private val onItemClicked: OnItemClickedListener) :
    ListAdapter<Ticket,TicketListAdapter.ViewHolder>(COMPARATOR) {

    class ViewHolder(val binding: TicketItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TicketItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTicket = getItem(position)

        if (currentTicket != null) {
            // Bind ticket data to the view holder
            holder.binding.apply {
                ticketName.text = currentTicket.name
                ticketDescription.text = currentTicket.description
                ticketPriority.text = currentTicket.priority
                ticketDueDate.text = currentTicket.dueDate
            }
        } else {
            Toast.makeText(holder.itemView.context, "Ticket List is empty", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
            onItemClicked.onItemClick(currentTicket)
        }
    }

    interface OnItemClickedListener {
        fun onItemClick(ticket: Ticket)
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Ticket>() {
            override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
                return oldItem == newItem
            }
        }

    }
}

