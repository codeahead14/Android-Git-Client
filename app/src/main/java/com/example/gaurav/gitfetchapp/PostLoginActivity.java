package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.GooglePlayServices.TrackerApplication;
import com.example.gaurav.gitfetchapp.Repositories.Owner;
import com.example.gaurav.gitfetchapp.UserInfo.User;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GistsFragment.OnFragmentInteractionListener {
    private static final String TAG = PostLoginActivity.class.getName();
    public static final String USER_DETAILS = "user_details";
    public static final String USER_FOLLOWING = "user_following";
    private Unbinder unbinder;
    private static User userDetails;
    private Tracker mTracker;
    private int screenType;
    private boolean tabletSize;
    private Runnable mRunnable;
    private Handler mHandler;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @OnClick(R.id.search_fab) void onSearchFabClick(View view){
        Intent searchIntent = new Intent(this,SearchGitActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(PostLoginActivity.this,view.findViewById(R.id.search_fab),
                        getString(R.string.searchIconTransition));
        startActivity(searchIntent, options.toBundle());
        //startActivity(searchIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        ButterKnife.bind(this);
        TrackerApplication application = (TrackerApplication) getApplication();
        mTracker = application.getDefaultTracker();
        screenType = getScreenSize();
        tabletSize = getResources().getBoolean(R.bool.isTablet);
        setSupportActionBar(toolbar);
        final Context context = this;
        mHandler = new Handler();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(mRunnable != null){
                    mHandler.post(mRunnable);
                    mRunnable = null;
                }
            }
        };

        if(tabletSize){//screenType == 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }else {
            drawer.setDrawerListener(toggle);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String owner = prefs.getString(PreLoginDeciderActivity.USERNAME_KEY,null);

        Intent intent = getIntent();
        //String[] intentData = intent.getExtras().getStringArray(Intent.EXTRA_TEXT);
        View headerView = navigationView.getHeaderView(0);
        TextView header_userName_textView = (TextView) headerView.findViewById(R.id.header_user_name_text);
        header_userName_textView.setText(owner);//PreLoginDeciderActivity.getLoginName());//intentData[0]);
        final TextView header_userEmail_textView = (TextView) headerView.findViewById(R.id.header_user_email_text);
        final ImageView header_icon = (ImageView) headerView.findViewById(R.id.header_icon);
        final GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                                    GitHubEndpointInterface.class);
        if(Utility.hasConnection(context)) {
            Call<User> call = gitHubEndpointInterface.getUserDetails(owner);//"hemanth");//PreLoginDeciderActivity.getLoginName());//intentData[0]);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User item = response.body();
                        userDetails = item;
                        if (item.getEmail() == null) {
                            header_userEmail_textView.setText(item.getHtmlUrl());
                            Log.v(TAG, "html url: " + item.getHtmlUrl());
                        } else
                            header_userEmail_textView.setText((String) item.getEmail());

                        Picasso.with(context)
                                .load(item.getAvatarUrl())
                                .transform(new CircleTransform())
                                .into(header_icon);

                        final SharedPreferences.Editor editor = prefs.edit();
                        if(!prefs.contains(USER_FOLLOWING)){
                            Call<List<Owner>> followersCall =
                                    gitHubEndpointInterface.getFollowers(owner);
                            followersCall.enqueue(new Callback<List<Owner>>() {
                                @Override
                                public void onResponse(Call<List<Owner>> call, Response<List<Owner>> response) {
                                    if(response.isSuccessful()){
                                        Log.v(TAG,"response successful");
                                        Set<String> set = new HashSet<String>();
                                        for(Owner elem : response.body())
                                            set.add(elem.getLogin());
                                        editor.putStringSet(USER_FOLLOWING,set);
                                        editor.apply();
                                        //editor.
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Owner>> call, Throwable t) {

                                }
                            });
                        }

                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(context,R.string.notOnline,Toast.LENGTH_SHORT).show();
        }

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(context,UserInfoActivity.class);
                userIntent.putExtra(USER_DETAILS,userDetails);
                //Log.v(TAG,"onCLICK userDetails: "+userDetails.getAvatarUrl());
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(PostLoginActivity.this, (ImageView)header_icon, "user_avatar_transition");
                context.startActivity(userIntent, options.toBundle());
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });

        Fragment fragment = new RepositoriesFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_repo,fragment,null);
        fragmentTransaction.commit();
    }

    public int getScreenSize(){
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return 0;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return 1;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return 2;
            default:
                return 1;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(unbinder!=null)
            unbinder.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Screen"+TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(!tabletSize){// screenType == 1 || screenType == 2) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class fragmentClass;

        switch(id) {
            case R.id.nav_repositories:
                fragmentClass = RepositoriesFragment.class;
                break;
            case R.id.nav_issues:
                fragmentClass = IssuesFragment.class;
                break;
            case R.id.nav_gists:
                fragmentClass = GistsFragment.class;
                break;
            case R.id.nav_public_events:
                fragmentClass = PublicEventsFragment.class;
                break;
            case R.id.nav_feeds:
                fragmentClass = PrivateFeedsFragment.class;
                //fragmentClass = FeedsFragment.class;
                break;
            case R.id.nav_share:
                fragmentClass = RepositoriesFragment.class;
                break;
            case R.id.nav_logout:
                fragmentClass = LogoutFragment.class;
                break;
            default:
                fragmentClass = RepositoriesFragment.class;
        }
        if(!tabletSize) {// screenType == 1 || screenType == 2)
            drawer.closeDrawer(GravityCompat.START);
        }

        try {
            /*
            Using Runnable for smooth Navigation Drawer Animation
            http://stackoverflow.com/questions/18871725/how-to-create-smooth-navigation-drawer
            https://developer.android.com/training/implementing-navigation/nav-drawer.html
             */
            final Fragment fragment = (Fragment) fragmentClass.newInstance();
            mRunnable = new Runnable() {
                @Override
               public void run() {
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStack();
                    fragmentManager.beginTransaction().replace(R.id.container_repo,fragment,null).commit();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(PostLoginActivity.this, "GistsFragment Callback", Toast.LENGTH_SHORT).show();
    }
}
