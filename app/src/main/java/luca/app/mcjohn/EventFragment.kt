package luca.app.mcjohn

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_event.*
import luca.app.mcjohn.databinding.FragmentEventBinding
import luca.app.mcjohn.events.Event
import luca.app.mcjohn.events.EventAdapter
import luca.app.mcjohn.network.Day
import luca.app.mcjohn.network.RequestSealed
import luca.app.mcjohn.viewModel.EventViewModel
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_DAY = "day"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var tabDay: String? = Day.TODAY.text
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val model: EventViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabDay = it.getString(ARG_DAY)
        }

        // set up recyclerView
        viewManager = LinearLayoutManager(activity)
        viewAdapter = EventAdapter(listOf())

        // update events
        refreshEvents()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = event_recycler_view.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            // update events
            refreshEvents()
        }

        // live update
        model.getEventsArrayObserver().observe(this, Observer<RequestSealed> { request ->
            // update recycler if data changes
            when (request) {
                is RequestSealed.Data -> {
                    (viewAdapter as EventAdapter).replaceData(request.events)

                    // empty view message
                    showViewEmptyMessage(noEvents, request.events)

                    //TODO: Move check to the View
                    hideLoading()
                }
                is RequestSealed.Error -> {
                    showErrors(request.error, swipeRefreshLayout)

                    //TODO: Move check to the View
                    hideLoading()
                }
                is RequestSealed.Loading -> {
                    loadingContent.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
        loadingContent.visibility = View.INVISIBLE
    }

    private fun refreshEvents() {
        tabDay?.let { model.getEvents(Day.valueOf(it.toUpperCase())) }
    }

    private fun showErrors(error: String, swipeRefreshLayout: SwipeRefreshLayout) {
        Snackbar.make(swipeRefreshLayout, error,
                Snackbar.LENGTH_LONG).show()
    }

    private fun showViewEmptyMessage(emptyMessage: TextView, eventsList: List<Event>) {
        if (eventsList.isEmpty()) {
            emptyMessage.visibility = View.VISIBLE
        } else {
            emptyMessage.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //inflate and bind variable
        //val listItemBinding :FragmentEventBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_event, container, false)
        //listItemBinding.viewModel= model
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param day Day.
         * @return A new instance of fragment EventFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(day: String) =
                EventFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_DAY, day)
                    }
                }
    }
}
