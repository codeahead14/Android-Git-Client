package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.example.gaurav.gitfetchapp.Repositories.RepositoryBranchPagerFragment;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivityFragment;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryPagerFragment;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;

/**
 * Created by GAURAV on 06-08-2016.

 Changed Base class from FragmentStatePagerAdapter to FragmentPagerAdapter
 on 04-06-2017
 */

public class RepositoryPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = RepositoryPagerAdapter.class.getName();
    private final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "INFO", "FILES", "COMMITS", "ISSUES" };
    private Context context;
    private UserRepoJson userRepoJson;
    private String selectedBranch;

    public RepositoryPagerAdapter(FragmentManager fm, Context context, UserRepoJson item, String branch) {
        super(fm);
        this.context = context;
        this.userRepoJson = item;
        this.selectedBranch = branch;
    }

    public void setSelectedBranch(String branch){
        this.selectedBranch = branch;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return RepositoryBranchPagerFragment.newInstance(position + 1, userRepoJson,
                    selectedBranch);
            case 1: return RepositoryPagerFragment.newInstance(position + 1, userRepoJson,
                    selectedBranch);
            case 2: return RepositoryPagerFragment.newInstance(position + 1, userRepoJson,
                    selectedBranch);
            case 3: return RepositoryPagerFragment.newInstance(position + 1, userRepoJson,
                    selectedBranch);
            default: return RepositoryBranchPagerFragment.newInstance(position + 1, userRepoJson,
                    selectedBranch);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}