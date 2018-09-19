package luca.app.mcjohn.repository

import android.util.Log
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.network.EventAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class EventRepository {
    val TAG = "eventRepository"

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

    fun getEventsAsync(data: (List<Event>, t: Throwable?) -> Unit) {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://eventi-mcjohn.azurewebsites.net/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val eventAPI = retrofit.create<EventAPI>(EventAPI::class.java)
        val events: Call<List<Event>> = eventAPI.loadEvents()

        events.enqueue(object : Callback<List<Event>> {
            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                data(listOf(), t)
                Log.v(TAG, t.toString())
            }

            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                val respObj = response.body()
                Log.v(TAG, respObj?.joinToString())
                data(respObj!!, null)
            }

        })

    }
}