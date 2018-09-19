package luca.app.mcjohn.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.Toast
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.repository.EventRepository

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = EventRepository()
    private val eventsArray : MutableLiveData<List<Event>> = MutableLiveData()

    fun getTestEvents(): Array<Event> {
        return repo.getTestEvents()
    }

    fun getEventsArrayObserver():MutableLiveData<List<Event>>{
        return eventsArray
    }

    fun getEvents() {
        repo.getEventsAsync { list: List<Event>, throwable: Throwable? ->
            eventsArray.value = list
            if(throwable != null) {
                Toast.makeText(getApplication(), throwable.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}