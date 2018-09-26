package luca.app.mcjohn.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.network.Day
import luca.app.mcjohn.network.RequestSealed
import luca.app.mcjohn.repository.EventRepository

class EventViewModel(private val repo: EventRepository) : ViewModel() {
    private val eventsArray: MutableLiveData<RequestSealed> = MutableLiveData()

    fun getEventsArrayObserver(): MutableLiveData<RequestSealed> {
        return eventsArray
    }

    fun getEvents(day: Day) {
        val eventsPromise: Deferred<List<Event>> = repo.getEventsAsync(day.text)

        // set loading
        eventsArray.postValue(RequestSealed.Loading())

        async {
            try {
                // update live data
                val events: List<Event> = eventsPromise.await()
                Log.v(TAG, "Events downloaded")
                eventsArray.postValue(RequestSealed.Data(events))
            } catch (e: Exception) {
                // empty data and return error
                e.printStackTrace()
                eventsArray.postValue(RequestSealed.Error(e.toString()))
            }

        }
    }

    companion object {
        private val TAG = "ViewModel"
    }
}