package luca.app.mcjohn.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.repository.eventRepository
import java.util.*

class EventViewModel : ViewModel() {
    private val repo = eventRepository()
    var eventsArray : MutableLiveData<List<Event>> = MutableLiveData()

    fun getTestEvents(): Array<Event> {
        return repo.getTestEvents()
    }

    fun getEvents() {
        repo.getEventsAsync {
            eventsArray.value = it
        }
    }
}