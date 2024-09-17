package com.example.ticketbuilder.ui.Adapter

import Ticket
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketbuilder.R

class TicketListAdapter(private var ticket: List<Ticket>,private val onItemClicked: (Ticket) -> Unit) :
    RecyclerView.Adapter<TicketListAdapter.TicketViewHolder>() {

    private var tickets = emptyList<Ticket>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticket_item,
            parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val currentTicket = tickets[position]
        holder.bind(currentTicket, onItemClicked)
    }

    override fun getItemCount() = tickets.size

    fun updateTickets(list: List<Ticket>) {
        tickets = list
        notifyDataSetChanged()
        DiffUtilCallback().areItemsTheSame(tickets[0], tickets[1])
    }
    class DiffUtilCallback : DiffUtil.ItemCallback<Ticket>(){
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket) = oldItem == newItem

    }
    inner class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var ticketName: TextView = itemView.findViewById(R.id.ticketName)
        private var ticketDescription: TextView = itemView.findViewById(R.id.ticketDescription)
        private var ticketPriority: TextView = itemView.findViewById(R.id.ticketPriority)
        private var ticketDueDate: TextView = itemView.findViewById(R.id.ticketDueDate)

        fun bind(ticket: Ticket, onItemClicked: (Ticket) -> Unit) {
            itemView.setOnClickListener {
                onItemClicked(ticket)
            }
            // Bind ticket data to itemView elements
            ticketName.text = ticket.name
            ticketDescription.text = ticket.description
            ticketPriority.text = ticket.priority
            ticketDueDate.text = ticket.dueDate
        }
    }
}

