package com.sanke.ilafedoseev.statisticapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabPageAdapter(fm : FragmentManager, var tabCount : Int) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return Tab1Fragment()
            1 -> return Tab3Fragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}
