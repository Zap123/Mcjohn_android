package luca.app.mcjohn

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.events.EventAdapter
import luca.app.mcjohn.viewModel.EventViewModel
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    // lazy inject ViewModel
    private val model: EventViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // dependency injection
        startKoin(this, listOf(appModule))

        // download events
        refreshEvents()


        // set up recyclerView
        viewManager = LinearLayoutManager(this)
        viewAdapter = EventAdapter(listOf())

        recyclerView = findViewById<RecyclerView>(R.id.event_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // pull to refresh
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            // update events
            refreshEvents()
        }

        // Loading bar
        val loadingContent: ProgressBar = findViewById(R.id.loadingContent)

        // live update
        model.getEventsArrayObserver().observe(this, Observer<List<Event>> { eventslist ->
            // update recycler if data changes
            eventslist?.let { (viewAdapter as EventAdapter).replaceData(it) }
            swipeRefreshLayout.isRefreshing = false
            loadingContent.visibility = View.INVISIBLE
            // empty view message
            showViewEmptyMessage(eventslist)
        })

    }

    private fun showViewEmptyMessage(eventslist: List<Event>?) {
        val emptyMessage: TextView = findViewById(R.id.noEvents)
        if (eventslist?.size == 0) {
            emptyMessage.visibility = View.VISIBLE
        } else {
            emptyMessage.visibility = View.GONE
        }
    }

    private fun refreshEvents() {
        model.getEvents()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.menu_refresh -> {
            swipeRefreshLayout.isRefreshing = true
            refreshEvents()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
