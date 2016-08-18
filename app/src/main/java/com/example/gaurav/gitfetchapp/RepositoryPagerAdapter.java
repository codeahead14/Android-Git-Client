package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.gaurav.gitfetchapp.Repositories.RepositoryBranchPagerFragment;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivityFragment;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryPagerFragment;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;

/**
 * Created by GAURAV on 06-08-2016.
 */
public class RepositoryPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = RepositoryPagerAdapter.class.getName();
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "BRANCHES", "FILES", "COMMITS" };
    private Context context;
    private UserRepoJson userRepoJson;

    public RepositoryPagerAdapter(FragmentManager fm, Context context, UserRepoJson item) {
        super(fm);
        this.context = context;
        this.userRepoJson = item;
        Log.v(TAG,"user repo: "+userRepoJson.getName());
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Log.v(TAG, "getting item: "+position);
        switch (position) {
            case 0: return RepositoryBranchPagerFragment.newInstance(position + 1, userRepoJson);
            case 1: return RepositoryPagerFragment.newInstance(position + 1, userRepoJson);
            case 2: return RepositoryPagerFragment.newInstance(position + 1, userRepoJson);
            default: return RepositoryBranchPagerFragment.newInstance(position + 1, userRepoJson);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}