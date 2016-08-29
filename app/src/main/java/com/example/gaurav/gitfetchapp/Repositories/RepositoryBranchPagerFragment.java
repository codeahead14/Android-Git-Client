package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import okhttp3.ResponseBody;
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
    private static final String TAG = RepositoryBranchPagerFragment.class.getName();
    private int mPage;
    private UserRepoJson userRepoJson;
    private String default_branch;
    private String repo_branch;
    private Parcelable state;
    private GitHubEndpointInterface gitHubEndpointInterface;

    // Variables to save state
    private static final String BRANCH_DETAILS_KEY = "BRANCH_DETAILS";
    private BranchDetailJson branchDetails;

    @BindView(R.id.branch_detail_commit_text) TextView branch_commit_textView;
    @BindView(R.id.branch_detail_committer_text) TextView branch_committer_textView;
    @BindView(R.id.branch_detail_name_text) TextView branch_detail_name_textView;
    @BindView(R.id.branch_committer_img)
    ImageView branch_committer_imageView;
    @BindView(R.id.collaborators_list)
    ListView collaboratorsList;
    @BindView(R.id.markdownView)
    MarkdownView markdownView;
    @BindView(R.id.readme_text)
    TextView readme_textView;

    public static RepositoryBranchPagerFragment newInstance(int page, UserRepoJson item) {
        Log.v(TAG, "creating branch instance");
        Bundle args = new Bundle();
        args.putInt(BRANCH_ARG_PAGE, page);
        args.putParcelable(BRANCH_USER_REPO, item);
        RepositoryBranchPagerFragment fragment = new RepositoryBranchPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(BRANCH_ARG_PAGE);
        userRepoJson = getArguments().getParcelable(BRANCH_USER_REPO);
        default_branch = userRepoJson.getDefaultBranch();
        repo_branch = RepositoryDetailActivityFragment.repoBranch;
        gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);

        //if (savedInstanceState != null){
          //  branchDetails = savedInstanceState.getParcelable(BRANCH_DETAILS_KEY);
          //  setUpView(branchDetails);
        //}else
            //fetchBranchDetails();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG,"saving state");
        outState.putParcelable(BRANCH_DETAILS_KEY,branchDetails);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchBranchDetails();
        fetchRepoCollaborators();
        fetchReadme();
    }

    public void fetchReadme(){


        Call<ReadMeJson> call = gitHubEndpointInterface.getReadMe(
                userRepoJson.getOwner().getLogin(),userRepoJson.getName());
        call.enqueue(new Callback<ReadMeJson>(){
            @Override
            public void onFailure(Call<ReadMeJson> call, Throwable t) {

            }

            @Override
            public void onResponse(Call<ReadMeJson> call, Response<ReadMeJson> response) {
                if(response.isSuccessful()){
                    ReadMeJson item = response.body();
                    //setUpReadme(item);
        String download_url = item.getDownloadUrl().toString();
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
                            Log.v(TAG, Processor.process(sb.toString()));
                            setUpReadme( Processor.process(sb.toString()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ;
                    } finally {

                    }
                } else {
                    Log.d(TAG, "server contact failed");
                    //avLoadingIndicatorView.hide();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
                // avLoadingIndicatorView.hide();
            }
        });
                }
            }
        });
    }

    //private void setUpReadme(ReadMeJson item){
      //  markdownView.loadMarkdownFile(item.getDownloadUrl(),"file://assets/foghorn.css");
    private void setUpReadme(String text){
        readme_textView.setText(text);
        //Log.v(TAG,text);
        //markdownView.setMarkDownText("# Hello World\nThis is a simple markdown");
        //markdownView.loadMarkdownFromAssets("github-markdown-css.css");
        //markdownView.setMarkDownText(text);
    }

    public void fetchBranchDetails(){
        Call<BranchDetailJson> call = gitHubEndpointInterface.getBranchDetails(
                userRepoJson.getOwner().getLogin(),userRepoJson.getName(),repo_branch);
        call.enqueue(new Callback<BranchDetailJson>() {
            @Override
            public void onResponse(Call<BranchDetailJson> call, Response<BranchDetailJson> response) {
                if (response.isSuccessful()){
                    branchDetails = response.body();
                    setUpView(branchDetails);
                }
            }

            @Override
            public void onFailure(Call<BranchDetailJson> call, Throwable t) {

            }
        });
    }

    public void fetchRepoCollaborators(){
        GitHubEndpointInterface endpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class, AccessToken.getInstance());
        Call<ArrayList<CollaboratorsJson>> call = endpointInterface.getRepoCollaborators(
                userRepoJson.getOwner().getLogin(),userRepoJson.getName());
        call.enqueue(new Callback<ArrayList<CollaboratorsJson>>() {
            @Override
            public void onResponse(Call<ArrayList<CollaboratorsJson>> call, Response<ArrayList<CollaboratorsJson>> response) {
                if (response.isSuccessful()){
                    List<String> items = new ArrayList<String>();
                    for(CollaboratorsJson elem: response.body())
                        items.add(elem.getLogin());
                    ArrayAdapter<String> itemsAdapter =
                            new ArrayAdapter<String>(getContext(),R.layout.repository_files_layout,items);
                    Log.v(TAG,"item count list: " + itemsAdapter.getCount());
                    collaboratorsList.setAdapter(itemsAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CollaboratorsJson>> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repository_branch_details_layout, container, false);
        ButterKnife.bind(this,view);
        Typeface tf_1 = Typeface.createFromAsset(getContext().getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
        Typeface tf_2 = Typeface.createFromAsset(getContext().getResources().getAssets(),"font/Roboto-Light.ttf");
        branch_commit_textView.setTypeface(tf_1);
        branch_detail_name_textView.setTypeface(tf_2);
        branch_committer_textView.setTypeface(tf_2);

        //markdownView = (MarkdownView) view.findViewById(R.id.markdown_view);

        /*Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.git_branch_20x32dp);
        Drawable underlyingDrawable =
                new BitmapDrawable(getContext().getResources(),icon);

        final ScaleDrawable scaleDrawable = new ScaleDrawable(underlyingDrawable, Gravity.START,14,14){
            public int getIntrinsicHeight(){
                return Math.max(super.getIntrinsicHeight(), branch_detail_name_textView.getHeight());
            }
        };

        scaleDrawable.setLevel(10000);
        branch_detail_name_textView.setCompoundDrawablesWithIntrinsicBounds(scaleDrawable,
                null,null,null);*/
        return view;
    }

    public void setUpView(BranchDetailJson item){
        String committer = item.getCommit().getCommit().getCommitter().getName();
        branch_commit_textView.setText(item.getCommit().getCommit().getMessage());
        branch_detail_name_textView.setText(item.getName());
        Log.v(TAG,"detail name: "+item.getName());

        Spanned commit_action = Html.fromHtml("<b>" + committer + "</b>" + " committed on " + "<b>" +
                Utility.formatDateString(item.getCommit().getCommit().getAuthor().getDate()) + " </b>");
               // item.getCommit().getCommit().getCommitter().getDate() + "</b>");
        branch_committer_textView.setText(commit_action);

        Log.v(TAG,"avatar url "+item.getCommit().getCommitter().getAvatarUrl());
        Picasso.with(getContext())
                .load(item.getCommit().getCommitter().getAvatarUrl())
                .resize(14,14)
                .transform(new CircleTransform())
                .into(branch_committer_imageView);
    }
}
