package com.example.gaurav.gitfetchapp.IntentServices;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.Events.PublicEventsRecyclerAdapter;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivity;
import com.example.gaurav.gitfetchapp.Repositories.UserRepoJson;
import com.example.gaurav.gitfetchapp.ServiceGenerator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 18-09-2016.
 */
public class RepositoryDetailsService extends IntentService {
    private static final String TAG = RepositoryDetailsService.class.getName();
    public static final String BROADCAST_ACTION = "com.example.gaurav.gitfetchapp.BROADCAST";
    private ServiceCallback serviceCallback;

    public interface ServiceCallback{
        void serviceCallbackListener();
    }

    public RepositoryDetailsService(){
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(RepositoryDetailsService.this, "Wait while we Fetch Repository Details",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ///ResultReceiver receiver = intent.getStringExtra(PublicEventsRecyclerAdapter.ServiceTag);
        final ResultReceiver receiver = intent.getParcelableExtra("ServiceCallback");

        String url = intent.getStringExtra(PublicEventsRecyclerAdapter.ServiceTag);
        GitHubEndpointInterface endpointInterface = ServiceGenerator
                .createService(GitHubEndpointInterface.class);
        Call<UserRepoJson> call = endpointInterface.getRepoContentsWithUrl(url);
        call.enqueue(new Callback<UserRepoJson>() {
            @Override
            public void onResponse(Call<UserRepoJson> call, Response<UserRepoJson> response) {
                if(response.isSuccessful()){
                    UserRepoJson item = response.body();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Intent.EXTRA_TEXT,item);
                    receiver.send(Activity.RESULT_OK,bundle);
                }
            }

            @Override
            public void onFailure(Call<UserRepoJson> call, Throwable t) {

            }
        });

    }
}
