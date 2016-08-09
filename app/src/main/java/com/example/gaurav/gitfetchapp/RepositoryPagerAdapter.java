package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivityFragment;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryPagerFragment;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;

/**
 * Created by GAURAV on 06-08-2016.
 */
public class RepositoryPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "BRANCHES", "FILES", "COMMITS" };
    private Context context;
    private UserRepoJson userRepoJson;

    public RepositoryPagerAdapter(FragmentManager fm, Context context, UserRepoJson item) {
        super(fm);
        this.context = context;
        this.userRepoJson = item;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return RepositoryPagerFragment.newInstance(position + 1, userRepoJson);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}