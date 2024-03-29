package com.sahilmehra.expensemanager.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sahilmehra.expensemanager.ui.tablayout.BusinessFragment
import com.sahilmehra.expensemanager.ui.tablayout.HomeFragment
import com.sahilmehra.expensemanager.ui.tablayout.PersonalFragment

class ViewPagerAdapter(fragment: Fragment, private val tabCount: Int) :
    FragmentStateAdapter(fragment) {
    //set the tab count
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {

        //return particular fragment according to selected tab
        when (position) {
            0 -> return HomeFragment()
            1 -> return PersonalFragment()
            2 -> return BusinessFragment()
        }

        return HomeFragment()
    }
}