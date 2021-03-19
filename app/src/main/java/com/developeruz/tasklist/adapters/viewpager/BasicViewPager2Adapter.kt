package com.developeruz.tasklist.adapters.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;

public class BasicViewPager2Adapter extends FragmentStateAdapter {

    private final List<Fragment> mFragments;

    public BasicViewPager2Adapter(FragmentActivity fa, List<Fragment> fragments) {
        super(fa);
        this.mFragments = fragments;

    }

    @NonNull
    @Override
    public Fragment createFragment(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
