package luca.app.mcjohn

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.recyclerviewevent_item.view.*
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.events.EventAdapter
import luca.app.mcjohn.viewModel.EventViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // instantiate ViewModel
        val model = ViewModelProviders.of(this).get(EventViewModel::class.java)
        //download events
        model.getEvents()


        viewManager = LinearLayoutManager(this)
        viewAdapter = EventAdapter(listOf())

        recyclerView = findViewById<RecyclerView>(R.id.event_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.setOnClickListener{
            Log.v("MAIN",it.venue.text.toString())
            Log.v("MAIN","CLICK")
        }

        model.eventsArray.observe(this, Observer<List<Event>> { eventslist ->
            eventslist?.let { (viewAdapter as EventAdapter).replaceData(it) }
        })

    }
}
