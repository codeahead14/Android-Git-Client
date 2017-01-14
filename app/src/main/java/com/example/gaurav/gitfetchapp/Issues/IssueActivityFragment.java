package com.example.gaurav.gitfetchapp.Issues;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gaurav.gitfetchapp.Issues.IssueActivity.ISSUE_CONTENTS;

/**
 * A placeholder fragment containing a simple view.
 */
public class IssueActivityFragment extends Fragment {
    private static final String TAG = IssueActivityFragment.class.getName();

    @BindView(R.id.issue_fragment_toolbar)
    Toolbar toolbar;
    @BindView(R.id.issue_author_text)
    TextView issue_author;
    @BindView(R.id.issue_number_text)
    TextView issue_number;
    @BindView(R.id.issue_title_text)
    TextView issue_title;
    @BindView(R.id.issue_collapsing_bar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.issue_body_text)
    TextView issue_body;
    @BindView(R.id.issue_avatar)
    ImageView avatar_imageView;
    @BindView(R.id.issue_labels_linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.labels_listView)
    RecyclerView recyclerView;
    @BindView(R.id.issue_events_recyclerView)
    RecyclerView eventsRecyclerView;

    private IssueItem issueItem;
    private Typeface tf_1,tf_2;
    private ArrayAdapter<String> issueLabelAdapter;
    private IssueLabelsRecyclerAdapter labelsRecyclerAdapter;
    private IssueEventsRecyclerAdapter eventsRecyclerAdapter;
    private GitHubEndpointInterface gitHubEndpointInterface;


    public IssueActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null){
            issueItem = intent.getExtras().getParcelable(ISSUE_CONTENTS);
        }

        ArrayList<String> issueLabels = new ArrayList<String >();
        for (int i=0; i<issueItem.getLabels().size();i++)
            issueLabels.add(issueItem.getLabels().get(i).getName());

        labelsRecyclerAdapter = new IssueLabelsRecyclerAdapter(getContext(),issueLabels,issueItem);
        setUpEvents(issueItem);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_issue, container, false);
        ButterKnife.bind(this,rootView);
        tf_1 = Typeface.createFromAsset(getActivity().getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
        tf_2 = Typeface.createFromAsset(getActivity().getResources().getAssets(),"font/Roboto-Medium.ttf");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        issue_author.setText(String.format("%s opened this issue on %s",issueItem.getUser().getLogin(),Utility.dateFormatConversion(issueItem.getUpdatedAt())));
        issue_number.setText(String.format("Issue #%s",issueItem.getNumber()));
        issue_title.setText(issueItem.getTitle());
        issue_body.setText(issueItem.getBody());
        issue_body.setTypeface(tf_1);

        Picasso.with(getContext())
                .load(issueItem.getUser().getAvatarUrl())
                .transform(new CircleTransform())
                .into(avatar_imageView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(labelsRecyclerAdapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);

        //eventsRecyclerView.addItemDecoration(itemDecoration);
        eventsRecyclerView.setLayoutManager(layoutManager1);
        eventsRecyclerView.setAdapter(eventsRecyclerAdapter);

        return rootView;
    }

    public void setUpEvents(IssueItem item){
        eventsRecyclerAdapter = new IssueEventsRecyclerAdapter(getContext(),new ArrayList<IssueEventsJson>());
        gitHubEndpointInterface = ServiceGenerator.createService(GitHubEndpointInterface.class);
        Call<ArrayList<IssueEventsJson>> call = gitHubEndpointInterface.getIssueEventsWithUrl(item.getEventsUrl());
        call.enqueue(new Callback<ArrayList<IssueEventsJson>>() {
            @Override
            public void onResponse(Call<ArrayList<IssueEventsJson>> call, Response<ArrayList<IssueEventsJson>> response) {
                if(response.isSuccessful()){
                    ArrayList<IssueEventsJson> list = response.body();
                    for(IssueEventsJson elem: list)
                        eventsRecyclerAdapter.addItem(elem);
                    eventsRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IssueEventsJson>> call, Throwable t) {

            }
        });
    }
}
