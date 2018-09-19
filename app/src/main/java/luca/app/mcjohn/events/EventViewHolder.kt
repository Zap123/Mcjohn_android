package luca.app.mcjohn.events

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import luca.app.mcjohn.R
import luca.app.mcjohn.WebViewActivity

//reference to the views for each data item
class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val cardView: CardView = view.findViewById(R.id.card_view)
    val name: TextView = view.findViewById(R.id.title)
    val venue: TextView = view.findViewById(R.id.venue)
    val description: TextView = view.findViewById(R.id.description)
    val date: TextView = view.findViewById(R.id.date)
    val url: TextView = view.findViewById(R.id.url)

    init {
        cardView.setOnClickListener{
            launchBrowser(it)
        }
    }

    private fun launchBrowser(it: View) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url.text.toString())
        it.context.startActivity(i)
    }

    private fun launchInternalBrowser(it: View) {
        val i = Intent(it.context, WebViewActivity::class.java).apply {
            putExtra("website_address", url.text)
        }
        i.data = Uri.parse(url.text.toString())
        it.context.startActivity(i)
    }
}


