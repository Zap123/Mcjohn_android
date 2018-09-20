package luca.app.mcjohn.network

import kotlinx.coroutines.experimental.Deferred
import luca.app.mcjohn.events.Event
import retrofit2.http.GET

interface EventAPI {

    @GET("Today")
    fun loadEvents(): Deferred<List<Event>>
}

data class EventRequest(val status : Status, val value: List<Event>)

enum class Status {
    SUCCESS,
    LOADING,
    ERROR
}
