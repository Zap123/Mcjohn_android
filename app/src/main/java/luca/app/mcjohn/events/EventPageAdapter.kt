package luca.app.mcjohn.events

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import luca.app.mcjohn.EventFragment
import luca.app.mcjohn.network.Day

class EventPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return EventFragment.newInstance(getPageTitle(position).toString())
    }

    override fun getCount(): Int {
        return NUM_ITEMS
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> return Day.TODAY.text
            1 -> return Day.TOMORROW.text
            else -> ""
        }

    }

    companion object {
        const val NUM_ITEMS = 2
    }
}