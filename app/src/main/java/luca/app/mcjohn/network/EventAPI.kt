package luca.app.mcjohn.network

import luca.app.mcjohn.events.Event
import retrofit2.Call
import retrofit2.http.GET

interface EventAPI{

    @GET("Today")
    fun loadEvents() : Call<List<Event>>
}