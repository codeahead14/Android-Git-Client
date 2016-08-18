package com.example.gaurav.gitfetchapp.Repositories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
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
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.BranchDetails.BranchDetailJson;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private GitHubEndpointInterface gitHubEndpointInterface;

    @BindView(R.id.branch_detail_commit_text) TextView branch_commit_textView;
    @BindView(R.id.branch_detail_committer_text) TextView branch_committer_textView;
    @BindView(R.id.branch_detail_name_text) TextView branch_detail_name_textView;
    @BindView(R.id.branch_committer_img)
    ImageView branch_committer_imageView;
    @BindView(R.id.collaborators_list)
    ListView collaboratorsList;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchBranchDetails();
        fetchRepoCollaborators();
    }

    public void fetchBranchDetails(){
        Call<BranchDetailJson> call = gitHubEndpointInterface.getBranchDetails(
                userRepoJson.getOwner().getLogin(),userRepoJson.getName(),repo_branch);
        call.enqueue(new Callback<BranchDetailJson>() {
            @Override
            public void onResponse(Call<BranchDetailJson> call, Response<BranchDetailJson> response) {
                if (response.isSuccessful()){
                    BranchDetailJson item = response.body();
                    setUpView(item);
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

        Spanned commit_action = Html.fromHtml("<b>" + committer + "</b>" + " committed on " + "<b>" + "Monday" + " </b>");
               // item.getCommit().getCommit().getCommitter().getDate() + "</b>");
        branch_committer_textView.setText(commit_action);

        Picasso.with(getContext())
                .load(item.getCommit().getCommitter().getAvatarUrl())
                .resize(14,14)
                .into(branch_committer_imageView);
    }
}
