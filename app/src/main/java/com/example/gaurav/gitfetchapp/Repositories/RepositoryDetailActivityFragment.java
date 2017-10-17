package com.example.gaurav.gitfetchapp.Repositories;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.RepositoryPagerAdapter;
import com.example.gaurav.gitfetchapp.Utility;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.internal.Util;

/**
 * A placeholder fragment containing a simple view.
 */
public class RepositoryDetailActivityFragment extends Fragment implements BranchDialogFragment.OnBranchFragmentInteractionListener {
    @BindView(R.id.repoNameText) TextView repo_name_textview;
    @BindView(R.id.repoUrlText) TextView repo_url_textview;
    @BindView(R.id.starCountText) TextView star_count_textview;
    @BindView(R.id.forkCountText) TextView fork_count_textview;
    @BindView(R.id.repoWatchText) TextView repo_watch_textview;
    //@BindView(R.id.repolanguageText) TextView repo_language_textview;
    @BindView(R.id.watch_Img) ImageView watch_count_imageview;
    @BindView(R.id.filter_menu_button) ImageButton filter_menu_button;
    @BindView(R.id.repository_details_progress_bar)
    MaterialProgressBar materialProgressBar;

    private static final String TAG = RepositoryDetailActivityFragment.class.getName();
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String BRANCH_STATE = "BRANCH_NAME";
    private SharedPreferences mPrefs;
    private View rootView;
    private UserRepoJson item;
    private String repoLogin;
    private String repoName;
    private ViewPager viewPager;
    private RepositoryPagerAdapter repositoryPagerAdapter;
    private Snackbar connectionSnackbar;
    private BroadcastReceiver broadcastReceiver;

    // Public to allow pager fragments to access the updated value;
    public static String repoBranch;

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
    public void onStop() {
        super.onStop();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString(repoName, repoBranch);
        ed.apply();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            item = intent.getExtras().getParcelable(Intent.EXTRA_TEXT);
            repoName = item.getName();
            repoLogin = item.getOwner().getLogin();

            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            repoBranch = mPrefs.getString(repoName,item.getDefaultBranch());
        }
    }

    @OnClick(R.id.branch_menu_button) public void showBranches(){
        FragmentManager fm = getFragmentManager();
        BranchDialogFragment branchDialogFragment = BranchDialogFragment.newInstance(
                new String[] { repoLogin,repoName,repoBranch});
        branchDialogFragment.setTargetFragment(RepositoryDetailActivityFragment.this,
                1);
        branchDialogFragment.show(fm,TAG);
    }

    @OnClick(R.id.filter_menu_button) public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_repository_detail_actions, popup.getMenu());
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_branch:
                        /*
                        20th November, 2016
                        Replacing BranchDialog Actiivty Call by BranchDialogFragment Transaction
                         */
                        /*Intent intent = new Intent(getContext(), BranchDialogActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, new String[] { repoLogin,repoName,repoBranch});
                        startActivityForResult(intent,1);*/
                        FragmentManager fm = getFragmentManager();
                        BranchDialogFragment branchDialogFragment = BranchDialogFragment.newInstance(
                                new String[] { repoLogin,repoName,repoBranch});
                        branchDialogFragment.setTargetFragment(RepositoryDetailActivityFragment.this,
                                1);
                        branchDialogFragment.show(fm,TAG);

                        return true;
                    case R.id.action_settings:
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Utility.hasConnection(context)){
                    if (connectionSnackbar != null)
                        connectionSnackbar.dismiss();
                }else if(!Utility.hasConnection(context))
                    if(connectionSnackbar != null)
                        connectionSnackbar.show();
            }
        };
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        connectionSnackbar = Snackbar.make(rootView,R.string.notOnline,Snackbar.LENGTH_INDEFINITE);
        if(!Utility.hasConnection(getActivity())) {
            connectionSnackbar.setAction(R.string.network_settings, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                }
            });
            connectionSnackbar.setActionTextColor(getResources().getColor(R.color.teal300));
            connectionSnackbar.show();
        }

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        repositoryPagerAdapter = new RepositoryPagerAdapter(getChildFragmentManager(),
                getActivity(),item,repoBranch);
        viewPager.setAdapter(repositoryPagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_repository_detail, container, false);
        ButterKnife.bind(this,rootView);


        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.repo_fragment_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.teal500));
        //toolbar.setBackgroundColor(getResources().getColor(R.color.deepPurple500));

        //toolbar.setTitleTextColor(getResources().getColor(R.color.indigo700));

        Window window = getActivity().getWindow();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.deepPurple800));

            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.grey50), PorterDuff.Mode.SRC_ATOP);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
        //window.setStatusBarColor(getResources().getColor(R.color.teal700));
        return rootView;
    }


    @OnClick(R.id.repoUrlText) void launchWebView(){

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_repository_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            showMenu(rootView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Typeface tf_1 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Medium.ttf");
        Typeface tf_2 = Typeface.createFromAsset(getResources().getAssets(),"font/Roboto-Light.ttf");

        repo_name_textview.setText(item.getName());
        repo_name_textview.setTypeface(tf_1);
        repo_url_textview.setText(item.getHtmlUrl());
        //repo_language_textview.setText(item.getLanguage());
        //repo_language_textview.setTypeface(tf_2);
        repo_watch_textview.setText(item.getWatchersCount().toString());
        repo_watch_textview.setTypeface(tf_2);
        star_count_textview.setText(item.getStargazersCount().toString());
        star_count_textview.setTypeface(tf_2);
        fork_count_textview.setText(item.getForksCount().toString());
        fork_count_textview.setTypeface(tf_2);
    }

    @Override
    public void branchFragmentInteractionListener(String branchName) {
        repoBranch = branchName;
        repositoryPagerAdapter.setSelectedBranch(repoBranch);
        repositoryPagerAdapter.notifyDataSetChanged();
//        viewPager.setAdapter(new RepositoryPagerAdapter(getChildFragmentManager(),
  //              getActivity(),item,repoBranch));
    }
}
