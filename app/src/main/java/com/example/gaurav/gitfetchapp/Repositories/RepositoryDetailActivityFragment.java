package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.RepositoryPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class RepositoryDetailActivityFragment extends Fragment {
    @BindView(R.id.repoNameText) TextView repo_name_textview;
    @BindView(R.id.repoUrlText) TextView repo_url_textview;
    @BindView(R.id.starCountText) TextView star_count_textview;
    @BindView(R.id.forkCountText) TextView fork_count_textview;
    @BindView(R.id.repoWatchText) TextView repo_watch_textview;
    @BindView(R.id.repolanguageText) TextView repo_language_textview;
    @BindView(R.id.watch_Img) ImageView watch_count_imageview;

    private static final String TAG = RepositoryDetailActivityFragment.class.getName();
    public static final String ARG_PAGE = "ARG_PAGE";
    private View rootView;
    private UserRepoJson item;

    public RepositoryDetailActivityFragment() {
    }

    public static RepositoryDetailActivityFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RepositoryDetailActivityFragment fragment = new RepositoryDetailActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            item = intent.getExtras().getParcelable(Intent.EXTRA_TEXT);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new RepositoryPagerAdapter(getChildFragmentManager(),
                getActivity(),item));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_repository_detail, container, false);
        ButterKnife.bind(this,rootView);

        /*ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new RepositoryPagerAdapter(getActivity().getSupportFragmentManager(),
                getActivity()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);*/

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundColor(getResources().getColor(R.color.teal500));

        //toolbar.setTitleTextColor(getResources().getColor(R.color.indigo700));

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.teal700));

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.grey50), PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        /*Picasso.with(getContext())
                .load(item.getOwner().getAvatarUrl())
                .transform(new CircleTransform())
                .into(repo_avatar_imageview);*/

        repo_name_textview.setText(item.getName());
        repo_url_textview.setText(item.getHtmlUrl());
        repo_language_textview.setText(item.getLanguage());
        repo_watch_textview.setText(item.getWatchersCount().toString());
        star_count_textview.setText(item.getStargazersCount().toString());
        fork_count_textview.setText(item.getForksCount().toString());
    }
}
