package com.developeruz.tasklist.ui.adapters.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BasicViewPager2Adapter(fa: FragmentActivity?, private val mFragments: List<Fragment>) :
    FragmentStateAdapter(
        fa!!
    ) {
    override fun createFragment(i: Int): Fragment {
        return mFragments[i]
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }
}