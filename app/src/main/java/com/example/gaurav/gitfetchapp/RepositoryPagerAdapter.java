package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivityFragment;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryPagerFragment;

/**
 * Created by GAURAV on 06-08-2016.
 */
public class RepositoryPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "BRANCHES", "FILES", "COMMITS" };
    private Context context;

    public RepositoryPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return RepositoryPagerFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}