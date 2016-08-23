package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.example.gaurav.gitfetchapp.UserInfo.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GistsFragment.OnFragmentInteractionListener, PublicEventsFragment.OnPublicEventsFragmentInteractionListener {
    private static final String TAG = PostLoginActivity.class.getName();
    public static final String USER_DETAILS = "user_details";
    private Unbinder unbinder;
    private static User userDetails;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @OnClick(R.id.fab) void onFabClick(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

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
        setSupportActionBar(toolbar);
        final Context context = this;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        //String[] intentData = intent.getExtras().getStringArray(Intent.EXTRA_TEXT);
        View headerView = navigationView.getHeaderView(0);
        TextView header_userName_textView = (TextView) headerView.findViewById(R.id.header_user_name_text);
        header_userName_textView.setText(PreLoginDeciderActivity.getLoginName());//intentData[0]);
        final TextView header_userEmail_textView = (TextView) headerView.findViewById(R.id.header_user_email_text);
        final ImageView header_icon = (ImageView) headerView.findViewById(R.id.header_icon);
        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                                    GitHubEndpointInterface.class);
        Call<User> call = gitHubEndpointInterface.getUserDetails(PreLoginDeciderActivity.getLoginName());//"hemanth");//intentData[0]);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User item = response.body();
                    userDetails = item;
                    Log.v(TAG,"userDetails: "+userDetails.getHtmlUrl());
                    if(item.getEmail() == null) {
                        header_userEmail_textView.setText(item.getHtmlUrl());
                        Log.v(TAG,"html url: "+item.getHtmlUrl());
                    }else
                        header_userEmail_textView.setText((String)item.getEmail());

                    Picasso.with(context)
                            .load(item.getAvatarUrl())
                            .transform(new CircleTransform())
                            .into(header_icon);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

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

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(unbinder!=null)
            unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        Log.v("PostLoginActivity","id: "+id);

        Fragment fragment = null;
        Class fragmentClass;

        switch(id) {
            case R.id.nav_camera:
                fragmentClass = RepositoriesFragment.class;
                break;
            case R.id.nav_gallery:
                fragmentClass = FeedsFragment.class;
                break;
            case R.id.nav_slideshow:
                fragmentClass = GistsFragment.class;
                break;
            case R.id.nav_manage:
                fragmentClass = PublicEventsFragment.class;
                break;
            case R.id.nav_share:
                fragmentClass = RepositoriesFragment.class;
                break;
            case R.id.nav_send:
                fragmentClass = RepositoriesFragment.class;
                break;
            default:
                fragmentClass = RepositoriesFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.container_repo,fragment,null).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(PostLoginActivity.this, "GistsFragment Callback", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnPublicEventsFragmentInteractionListener(Uri uri){

    }
}
