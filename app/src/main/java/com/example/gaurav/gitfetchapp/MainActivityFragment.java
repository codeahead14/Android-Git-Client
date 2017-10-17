package com.example.gaurav.gitfetchapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static final String TAG = MainActivityFragment.class.getName();
    private View rootView;
    private static CatLoadingView catView;
    private Unbinder unbinder;
    private final String clientId = "ENTER YOUR CLIENT ID HERE";
    private final String clientSecret = "ENTER YOUR CLIENT SECRET HERE";
    private final String redirectUri = "welcome://com.project.github";
    private String userNameField = null;
    private String passwordField = null;
    private SharedPreferences prefs;
    private Snackbar connectionSnackbar;
    private BroadcastReceiver broadcastReceiver;

    @BindView(R.id.email)
    EditText userEmail;
    @BindView(R.id.pass)
    EditText userPassword;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catView = new CatLoadingView();
        Log.v(TAG,"oncreate");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    @OnClick(R.id.loginbutton)
    void submit() {
        userNameField = userEmail.getText().toString();
        passwordField = userPassword.getText().toString();

        String[] scopes = {"user", "public_repo", "repo", "delete_repo", "gist"};

        if (userNameField.isEmpty() || passwordField.isEmpty()) {
            Toast.makeText(getActivity(), "Cannot Leave UserName/Password Blank",
                    Toast.LENGTH_SHORT).show();
        } else {
            GitHubEndpointInterface gitInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class, userNameField, passwordField);
            LoginPost loginPost = new LoginPost();
            loginPost.setScopes(scopes);
            loginPost.setNote("myapp");
            loginPost.setNote_url(null);
            loginPost.setClient_id(clientId);
            loginPost.setClient_secret(clientSecret);
            Call<LoginJson> call = gitInterface.getLoginCode(loginPost);

            if (Utility.hasConnection(getContext())) {
                catView.show(getFragmentManager(), TAG);
                call.enqueue(new Callback<LoginJson>() {
                    @Override
                    public void onResponse(Call<LoginJson> call, Response<LoginJson> response) {
                        if(response.isSuccessful()) {
                            LoginJson item = response.body();
                            Log.v(TAG,"received response ");
                            if (item.getToken() != null) {
                                AccessToken.getInstance().setAccessToken(item.getToken());
                                prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString(PreLoginDeciderActivity.ACCESS_TOKEN_KEY, item.getToken());
                                editor.putString(PreLoginDeciderActivity.USERNAME_KEY, userNameField);
                                PreLoginDeciderActivity.setLoginName(userNameField);
                                editor.apply();
                            }
                            catView.dismiss();
                            Intent intent = new Intent(getActivity(), PostLoginActivity.class);
                            //intent.putExtra(Intent.EXTRA_TEXT,new String[]{userNameField, item.getToken()});
                            startActivity(intent);
                            getActivity().finish();
                        } else{
                            Log.v(TAG,"response "+response.message());
                            Toast.makeText(getActivity(), "Bad Credentials", Toast.LENGTH_LONG).show();
                            catView.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginJson> call, Throwable t) {
                        Log.v(TAG,"Failure "+t.getMessage());
                        catView.dismiss();
                    }
                });
            } else
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.notOnline), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        connectionSnackbar = Snackbar.make(rootView, getResources().getString(R.string.notOnline),
                Snackbar.LENGTH_INDEFINITE);
        if(!Utility.hasConnection(getContext())) {
            connectionSnackbar.setAction(R.string.network_settings, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                }
            });
            connectionSnackbar.setActionTextColor(getResources().getColor(R.color.teal300));
            connectionSnackbar.show();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Utility.hasConnection(context)){
                    if(connectionSnackbar != null){
                        connectionSnackbar.dismiss();
                    }
                }else if(!Utility.hasConnection(context)){
                    if(connectionSnackbar != null){
                        connectionSnackbar.show();
                    }
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getActivity().getIntent().getData();
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            //Log.v(TAG,"redirecting: "+uri.toString());
            // use the parameter your API exposes for the code (mostly it's "code")
            String code = uri.getQueryParameter("code");
            if (code != null) {
                // get access token
                GitHubEndpointInterface loginService =
                        ServiceGenerator.createService(GitHubEndpointInterface.class, clientId, clientSecret);
                Call<AccessToken> call = loginService.getAccessToken(clientId, clientSecret, code);

                try {
                    AccessToken accessToken = call.execute().body();
                } catch (IOException e) {
                    // handle error
                }
            } else if (uri.getQueryParameter("error") != null) {
                // show an error message here
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }
}
