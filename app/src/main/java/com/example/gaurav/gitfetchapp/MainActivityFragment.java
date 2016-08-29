package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    private final String clientId = "158a0d1c5f2352735a22";
    private final String clientSecret = "add98d28020b075d669e76a799deb67b110dbc96";
    private final String redirectUri = "welcome://com.project.github";
    private String userNameField = null;
    private String passwordField = null;
    private SharedPreferences prefs;

    public static String loginName = null;

    @BindView(R.id.email) EditText userEmail;
    @BindView(R.id.pass) EditText userPassword;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        catView = new CatLoadingView();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }

    @OnClick(R.id.loginbutton) void submit(){
        userNameField = "codeahead14"; //userEmail.getText().toString();
        passwordField = "Gaurav14"; //userPassword.getText().toString();
        String[] scopes = {"user","public_repo","repo","delete_repo","gist"};

        if(userNameField.matches("") || passwordField.matches("")){
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

            if(Utility.hasConnection(getContext())) {
                catView.show(getFragmentManager(),TAG);
                call.enqueue(new Callback<LoginJson>() {
                    @Override
                    public void onResponse(Call<LoginJson> call, Response<LoginJson> response) {
                        LoginJson item = response.body();
                        Log.v(TAG,"response: "+item.getScopes());
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
                    }

                    @Override
                    public void onFailure(Call<LoginJson> call, Throwable t) {

                    }
                });
            }else
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.notOnline), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

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
                Call<AccessToken> call = loginService.getAccessToken(clientId, clientSecret,code);

                try {
                    AccessToken accessToken = call.execute().body();
                } catch (IOException e ){
                    // handle error
                }
            } else if (uri.getQueryParameter("error") != null) {
                // show an error message here
            }
        }
    }
}


    /*Button loginButton = (Button) rootView.findViewById(R.id.loginbutton);
loginButton.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        String userNameField = userName.getText().toString();
        String passwordField = password.getText().toString();
        if(userNameField.matches("") || passwordField.matches("")){
        Toast.makeText(getActivity(), "Cannot Leave UserName/Password Blank",
        Toast.LENGTH_SHORT).show();
        } else {
        //Retrofit retrofit = new Retrofit.Builder()
        //      .baseUrl("https://api.github.com")
        //    .addConverterFactory(GsonConverterFactory.create())
        //  .build();
        GitHubEndpointInterface gitInterface = ServiceGenerator.createService(
        GitHubEndpointInterface.class, userNameField, passwordField);
                    /*Call<AccessToken> call = gitInterface.getLoginCode(userNameField,
                            passwordField, clientId);
        //Call<String> call = gitInterface.getLoginCode("note");//,clientId,clientSecret);
        LoginPost loginPost = new LoginPost();
        loginPost.setScopes(null);
        loginPost.setNote("myapp");
        loginPost.setNote_url(null);
        loginPost.setClient_id(clientId);
        loginPost.setClient_secret(clientSecret);
        Call<LoginJson> call = gitInterface.getLoginCode(loginPost);
        Log.v(TAG, call.toString());

        call.enqueue(new Callback<LoginJson>() {
@Override
public void onResponse(Call<LoginJson> call, Response<LoginJson> response) {
        LoginJson item=response.body();
        responseBody.setText(item.getToken());
        }

@Override
public void onFailure(Call<LoginJson> call, Throwable t) {
        //Handle failure
        }
        });*/

                    /*GitHubClient client = new GitHubClient();
                    client.setCredentials(userNameField, passwordField);

                    String description = "GitFork - " + Build.MANUFACTURER + " " + Build.MODEL;
                    String fingerprint = Settings.Secure.ANDROID_ID;
                    if (fingerprint == null) {
                        fingerprint = Build.FINGERPRINT;
                    }
                    int index = 1;
                    OAuthService authService = new OAuthService(client);*/


                /*Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(ServiceGenerator.WEB_BASE_URL + "/login/oauth/authorize" + "?client_id=" + clientId
                                + "&redirect_uri=" + redirectUri));
                Log.v(TAG,Uri.parse(ServiceGenerator.WEB_BASE_URL + "/login/oauth/authorize" + "?client_id=" + clientId
                        + "&redirect_uri=" + redirectUri).toString());
                startActivity(intent);*/
