package luca.app.mcjohn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event.*
import luca.app.mcjohn.events.EventPageAdapter
import luca.app.mcjohn.network.Day
import luca.app.mcjohn.viewModel.EventViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    // lazy inject ViewModel
    private val model: EventViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabAdapter = EventPageAdapter(supportFragmentManager)
        pager.adapter = tabAdapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.menu_refresh -> {
            refreshTabEvents()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun refreshTabEvents() {
        swipeRefreshLayout.isRefreshing = true
        val selectedDay = pager.currentItem.let { pager.adapter?.getPageTitle(it) }
        model.getEvents(Day.valueOf(selectedDay.toString()))
    }
}
