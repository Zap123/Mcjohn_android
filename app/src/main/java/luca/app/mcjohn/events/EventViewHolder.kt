package luca.app.mcjohn.events

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import luca.app.mcjohn.R
import luca.app.mcjohn.WebViewActivity

//reference to the views for each data item
class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val cardView: CardView = view.findViewById(R.id.card_view)
    private val shareBtn : Button = view.findViewById(R.id.share)
    private val openBtn : Button = view.findViewById(R.id.openurl)

    val name: TextView = view.findViewById(R.id.title)
    val venue: TextView = view.findViewById(R.id.venue)
    val description: TextView = view.findViewById(R.id.description)
    val date: TextView = view.findViewById(R.id.date)
    val url: TextView = view.findViewById(R.id.url)

    init {
        // set up listener for tap events
        cardView.setOnClickListener{
            launchBrowser(it)
        }

        openBtn.setOnClickListener{
            launchBrowser(it)
        }

        shareBtn.setOnClickListener{
            shareEvent(it)
        }
    }

    private fun shareEvent(view: View) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            val eventName = name.text
            val url = url.text
            putExtra(Intent.EXTRA_TEXT, "$eventName $url")
            type = "text/plain"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            view.context.startActivity(Intent.createChooser(sendIntent,"Send event to"))
        } else{
            view.context.startActivity(sendIntent)
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


