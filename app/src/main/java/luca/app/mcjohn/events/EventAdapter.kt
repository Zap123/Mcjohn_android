package luca.app.mcjohn.events

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import luca.app.mcjohn.R

class EventAdapter(private var events: List<Event>) :
        RecyclerView.Adapter<EventViewHolder>() {

    lateinit var currentEvent: Event

    override fun getItemCount(): Int {
        return events.size
    }

    //replace elements
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.name.text = events[position].name
        holder.venue.text = events[position].venue
        holder.date.text = events[position].date
        holder.description.text = events[position].description
        holder.url.text = events[position].url

        currentEvent = events[position]
    }

    fun replaceData(evList: List<Event>) {
        events = evList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewevent_item,
                parent, false)
        return EventViewHolder(view)
    }

}