package luca.app.mcjohn.repository

import kotlinx.coroutines.experimental.Deferred
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.network.EventAPI
import retrofit2.Retrofit

class EventRepository(private val retrofit: Retrofit) {

    fun getTestEvents(): Array<Event> {
        val testEvent = Event("Today, 10 PM",
                "The forest calling sabato 10 dalle alle suonano", 22f,
                "Loras + Fried", "http://google.it",
                "Bebop Como, Como, Italy")
        return arrayOf(testEvent, testEvent, testEvent, testEvent, testEvent,
                testEvent, testEvent, testEvent, testEvent, testEvent, testEvent, testEvent)
    }

    fun getTestEmptyEvents(): Array<Event> {
        return arrayOf()
    }

    // return promise
    fun getEventsAsync(): Deferred<List<Event>> {
        val eventAPI = retrofit.create(EventAPI::class.java)
        return eventAPI.loadEvents()
    }
//        events.enqueue(object : Callback<List<Event>> {
//            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
//                // on errors return empty data + exception
//                data(listOf(), t)
//                Log.v(TAG, t.toString())
//            }
//
//            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
//                val respObj = response.body()
//                Log.v(TAG, respObj?.joinToString())
//
//                // return data or empty
//                data(respObj ?: listOf(), null)
//            }
//
//        })


    companion object {
        private const val TAG = "eventRepository"
    }
}