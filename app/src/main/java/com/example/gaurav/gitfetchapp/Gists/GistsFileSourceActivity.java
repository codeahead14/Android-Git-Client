package com.example.gaurav.gitfetchapp.Gists;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.FileViewActivity;
import com.example.gaurav.gitfetchapp.ServiceGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GistsFileSourceActivity extends AppCompatActivity {

    private static final String TAG = GistsFileSourceActivity.class.getName();

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.file_source_text) TextView file_source_textview;
    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gists_file_source);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getExtras().getString(Intent.EXTRA_TEXT);
        /* Downloading File Contents - GISTS */
        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        Call<ResponseBody> call = gitHubEndpointInterface.downloadFileWithDynamicUrlSync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    try{
                        reader = new BufferedReader(new InputStreamReader(
                                response.body().byteStream()));
                        String line;
                        try{
                            while ((line=reader.readLine())!=null) {
                                sb.append(line);
                                sb.append("\n");
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        };
                    }finally {

                    }
                    file_source_textview.setText(sb.toString());
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.fab) void showSnack(View view){
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
