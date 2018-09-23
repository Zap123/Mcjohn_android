package luca.app.mcjohn.network

import kotlinx.coroutines.experimental.Deferred
import luca.app.mcjohn.events.Event
import retrofit2.http.GET
import retrofit2.http.Path

interface EventAPI {

    @GET("{day}")
    fun loadEvents(@Path("day") day: String): Deferred<List<Event>>
}

data class EventRequest(val status: Status, val value: List<Event>, val message: String?)

enum class Status {
    SUCCESS,
    LOADING,
    ERROR
}

enum class Day(val text:String){
    TODAY("Today"),
    TOMORROW("Tomorrow")
}
