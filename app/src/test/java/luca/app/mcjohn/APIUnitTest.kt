package luca.app.mcjohn

import luca.app.mcjohn.events.Event
import luca.app.mcjohn.network.EventAPI
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.CountDownLatch


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class APIUnitTest {
    @Test
    fun test_Today_Call() {
        val latch = CountDownLatch(1)
        var eventsList:List<Event>? = listOf(Event("t","t",
                0f,"t","t","t"))

        val retrofit = Retrofit.Builder()
                .baseUrl("http://eventi-mcjohn.azurewebsites.net/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val eventAPI = retrofit.create<EventAPI>(EventAPI::class.java)
        val events: Call<List<Event>> = eventAPI.loadEvents()

        events.enqueue(object: Callback<List<Event>> {
            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                latch.countDown()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                eventsList = response.body()
                print(eventsList?.joinToString())
                latch.countDown()
            }

        })

        latch.await()

        assertNotEquals(eventsList?.size, 1)
    }
}
