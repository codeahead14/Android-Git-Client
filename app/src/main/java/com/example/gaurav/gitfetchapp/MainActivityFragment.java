package com.example.gaurav.gitfetchapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
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

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OAuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static final String TAG = MainActivityFragment.class.getName();

    private View rootView;
    private TextView responseText;
    private String userUrl;
    private EditText userName;
    private EditText password;

    private final String clientId = "158a0d1c5f2352735a22";
    private final String clientSecret = "add98d28020b075d669e76a799deb67b110dbc96";
    private final String redirectUri = "welcome://com.project.github/";

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        responseText = (TextView) rootView.findViewById(R.id.responseText);
        userName = (EditText) rootView.findViewById(R.id.userName);
        password = (EditText) rootView.findViewById(R.id.password);

        Button loginButton = (Button) rootView.findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameField =userName.getText().toString();
                String passwordField =userName.getText().toString();
                if(userNameField.matches("") || passwordField.matches("")){
                    Toast.makeText(getActivity(), "Cannot Leave UserName/Password Blank",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.github.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    GitHubEndpointInterface gitInterface = ServiceGenerator.createService(
                            GitHubEndpointInterface.class, userNameField, passwordField);
                    /*Call<AccessToken> call = gitInterface.getLoginCode(userNameField,
                            passwordField, clientId);*/
                    /*Call<AccessToken> call = gitInterface.getLoginCode(clientId);

                    call.enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            AccessToken item=response.body();
                            Log.v(TAG,"item received: "+item);
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
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

                }
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(ServiceGenerator.WEB_BASE_URL + "/login/oauth/authorize" + "?client_id=" + clientId
                                + "&redirect_uri=" + redirectUri));
                startActivity(intent);
            }
        });
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

    private void setResponse(String response){
        if(userUrl != null)
            responseText.setText(userUrl);
    }
}
