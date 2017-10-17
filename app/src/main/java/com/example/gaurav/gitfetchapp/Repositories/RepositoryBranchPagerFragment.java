package com.example.gaurav.gitfetchapp.Repositories;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.AccessToken;
import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.BranchDetailJson;
import com.example.gaurav.gitfetchapp.Repositories.ReadMe.ReadMeJson;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;

import com.github.rjeschke.txtmark.Processor;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.feras.mdv.MarkdownView;

/**
 * Created by GAURAV on 17-08-2016.
 */
public class RepositoryBranchPagerFragment extends Fragment {
    public static final String BRANCH_ARG_PAGE = "BRANCH_ARG_PAGE";
    public static final String BRANCH_USER_REPO = "BRANCH_USER_REPO";
    public static final String BRANCH_SELECTED = "BRANCH_SELECTED";

    private static final String TAG = RepositoryBranchPagerFragment.class.getName();
    private int mPage;
    private UserRepoJson userRepoJson;
    private String default_branch;
    private String repo_branch;
    private Parcelable state;
    private GitHubEndpointInterface gitHubEndpointInterface;
    private Snackbar connectionSnackbar;
    private BroadcastReceiver broadcastReceiver;
    private View view;

    // Variables to save state
    private static final String BRANCH_DETAILS_KEY = "BRANCH_DETAILS";
    private BranchDetailJson branchDetails;

    @BindView(R.id.branch_detail_commit_text)
    TextView branch_commit_textView;
    @BindView(R.id.branch_detail_committer_text)
    TextView branch_committer_textView;
    @BindView(R.id.branch_detail_name_text)
    TextView branch_detail_name_textView;
    @BindView(R.id.branch_committer_img)
    ImageView branch_committer_imageView;
    @BindView(R.id.collaborators_list)
    ListView collaboratorsList;
    @BindView(R.id.markdownView)
    //eu.fiskur.markdownview.MarkdownView markdownView;   // markdown View from https://github.com/fiskurgit/MarkdownView
    MarkdownView markdownView;
    @BindView(R.id.readme_text)
    TextView readme_textView;
    @BindView(R.id.readme_title_text)
    TextView readme_Title_Text;
    @BindView(R.id.readme_cardView)
    CardView readme_Card;
    @BindView(R.id.branch_details_progress_bar)
    MaterialProgressBar materialProgressBar;


    public static RepositoryBranchPagerFragment newInstance(int page,UserRepoJson item,String branch) {
        Bundle args = new Bundle();
        args.putInt(BRANCH_ARG_PAGE, page);
        args.putParcelable(BRANCH_USER_REPO, item);
        args.putString(BRANCH_SELECTED,branch);
        RepositoryBranchPagerFragment fragment = new RepositoryBranchPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(BRANCH_ARG_PAGE);
        userRepoJson = getArguments().getParcelable(BRANCH_USER_REPO);
        //repo_branch = RepositoryDetailActivityFragment.repoBranch;
        repo_branch = getArguments().getString(BRANCH_SELECTED);
        gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(!Utility.hasConnection(context)){
                    materialProgressBar.setVisibility(View.GONE);
                }else if(Utility.hasConnection(context)){
                    materialProgressBar.setVisibility(View.VISIBLE);
                    fetchBranchDetails();
                    fetchRepoCollaborators();
                    fetchReadme();
                }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BRANCH_DETAILS_KEY, branchDetails);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*connectionSnackbar = Snackbar.make(view, getResources().getString(R.string.notOnline),
                Snackbar.LENGTH_INDEFINITE);
        if(!Utility.hasConnection(getActivity())) {
            connectionSnackbar.setAction(R.string.network_settings, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                }
            });
            connectionSnackbar.setActionTextColor(getResources().getColor(R.color.teal300));
            connectionSnackbar.show();
        }else{
            materialProgressBar.setVisibility(View.VISIBLE);
        }*/

        if(Utility.hasConnection(getContext())) {
            materialProgressBar.setVisibility(View.VISIBLE);
        /* The reason for placing these calls in onActivityCreated
        * is due viewpagerfragment clearing out this view for other views.*/
            fetchBranchDetails();
            fetchRepoCollaborators();
            fetchReadme();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.repository_branch_details_layout, container, false);
        ButterKnife.bind(this, view);
        Typeface tf_1 = Typeface.createFromAsset(getContext().getResources().getAssets(), "font/RobotoCondensed-Regular.ttf");
        Typeface tf_2 = Typeface.createFromAsset(getContext().getResources().getAssets(), "font/Roboto-Light.ttf");
        branch_commit_textView.setTypeface(tf_1);
        branch_detail_name_textView.setTypeface(tf_2);
        branch_committer_textView.setTypeface(tf_2);
        return view;
    }


    public void fetchReadme() {
        Call<ReadMeJson> call = gitHubEndpointInterface.getReadMe(
                userRepoJson.getOwner().getLogin(), userRepoJson.getName());
        call.enqueue(new Callback<ReadMeJson>() {
            @Override
            public void onFailure(Call<ReadMeJson> call, Throwable t) {

            }

            @Override
            public void onResponse(Call<ReadMeJson> call, Response<ReadMeJson> response) {
                if (response.isSuccessful()) {
                    ReadMeJson item = response.body();
                    //setUpReadme(item);
                    String download_url = item.getDownloadUrl();
                    Call<ResponseBody> call1 = gitHubEndpointInterface.downloadFileWithDynamicUrlSync(download_url);
                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                BufferedReader reader = null;
                                StringBuilder sb = new StringBuilder();
                                try {
                                    reader = new BufferedReader(new InputStreamReader(
                                            response.body().byteStream()));
                                    String line;
                                    try {
                                        while ((line = reader.readLine()) != null) {
                                            sb.append(line);
                                            sb.append("\n");
                                        }
                                        setUpReadme(Processor.process(sb.toString()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ;
                                } finally {
                                }
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
                materialProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setUpReadme(String text) {
        if(text != null) {
            readme_Title_Text.setVisibility(View.VISIBLE);
            readme_Card.setVisibility(View.VISIBLE);
            markdownView.loadMarkdown(text);
        }
    }

    public void fetchBranchDetails() {
        Call<BranchDetailJson> call = gitHubEndpointInterface.getBranchDetails(
                userRepoJson.getOwner().getLogin(), userRepoJson.getName(), repo_branch);
        call.enqueue(new Callback<BranchDetailJson>() {
            @Override
            public void onResponse(Call<BranchDetailJson> call, Response<BranchDetailJson> response) {
                if (response.isSuccessful()) {
                    branchDetails = response.body();
                    setUpView(branchDetails);
                }
            }

            @Override
            public void onFailure(Call<BranchDetailJson> call, Throwable t) {

            }
        });
    }

    public void fetchRepoCollaborators() {
        GitHubEndpointInterface endpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class, AccessToken.getInstance());
        Call<ArrayList<CollaboratorsJson>> call = endpointInterface.getRepoCollaborators(
                userRepoJson.getOwner().getLogin(), userRepoJson.getName());
        call.enqueue(new Callback<ArrayList<CollaboratorsJson>>() {
            @Override
            public void onResponse(Call<ArrayList<CollaboratorsJson>> call, Response<ArrayList<CollaboratorsJson>> response) {
                if (response.isSuccessful()) {
                    List<String> items = new ArrayList<String>();
                    if(items.size() > 0 ) {
                        for (CollaboratorsJson elem : response.body())
                            items.add(elem.getLogin());
                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getContext(), R.layout.repository_files_layout, items);
                        collaboratorsList.setAdapter(itemsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CollaboratorsJson>> call, Throwable t) {

            }
        });
    }

    public void setUpView(BranchDetailJson item) {
        String committer = item.getCommit().getCommit().getAuthor().getName();
        branch_commit_textView.setText(item.getCommit().getCommit().getMessage());
        branch_detail_name_textView.setText(item.getName());

        Spanned commit_action = Html.fromHtml("<b>" + committer + "</b>" + " committed on " + "<b>" +
                Utility.formatDateString(item.getCommit().getCommit().getAuthor().getDate()) + " </b>");
        // item.getCommit().getCommit().getCommitter().getDate() + "</b>");
        branch_committer_textView.setText(commit_action);

        Picasso.with(getContext())
                .load(item.getCommit().getCommitter().getAvatarUrl())
                .resize(14, 14)
                .transform(new CircleTransform())
                .into(branch_committer_imageView);
    }
}
