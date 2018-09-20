package luca.app.mcjohn.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.network.EventRequest
import luca.app.mcjohn.network.Status
import luca.app.mcjohn.repository.EventRepository

class EventViewModel(private val repo: EventRepository) : ViewModel() {
    private val eventsArray: MutableLiveData<EventRequest> = MutableLiveData()

    fun getEventsArrayObserver(): MutableLiveData<EventRequest> {
        return eventsArray
    }

    fun getEvents(){
        val eventsPromise: Deferred<List<Event>> = repo.getEventsAsync()
        // set loading
        //eventsArray.postValue(EventRequest(Status.LOADING, null))
        async {
            try {
                // update live data
                val events: List<Event> = eventsPromise.await()
                eventsArray.postValue(EventRequest(Status.SUCCESS, events))
            } catch (e: Exception) {
                // empty data and return error
                e.printStackTrace()
                eventsArray.postValue(EventRequest(Status.ERROR, listOf()))
            }

        }
    }
}