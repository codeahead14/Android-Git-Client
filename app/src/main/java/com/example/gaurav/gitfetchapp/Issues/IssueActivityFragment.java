package com.example.gaurav.gitfetchapp.Issues;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import us.feras.mdv.MarkdownView;

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
    MarkdownView issue_body;
    @BindView(R.id.issue_avatar)
    ImageView avatar_imageView;
    @BindView(R.id.labels_listView)
    RecyclerView recyclerView;
    @BindView(R.id.issue_events_recyclerView)
    RecyclerView eventsRecyclerView;
    @BindView(R.id.issues_comments_recyclerView)
    RecyclerView commentsRecyclerView;
    @BindView(R.id.issue_labels_linearLayout)
    LinearLayout labelsLayout;
    @BindView(R.id.issue_contents_progress)
    MaterialProgressBar issuesContentsProgress;

    private IssueItem issueItem;
    private Typeface tf_1, tf_2;
    private ArrayAdapter<String> issueLabelAdapter;
    private IssueLabelsRecyclerAdapter labelsRecyclerAdapter;
    private IssueEventsRecyclerAdapter eventsRecyclerAdapter;
    private IssueCommentsRecyclerAdapter issueCommentsRecyclerAdapter;
    private IssueEventsAndCommentsRecyclerAdapter issueEventsAndCommentsAdapter;
    private GitHubEndpointInterface gitHubEndpointInterface;
    private ArrayList<IssueEventsJson> eventsList;
    private ArrayList<IssueCommentsJson> commentsList;
    ArrayList<String> issueLabels;


    public IssueActivityFragment() {
        eventsList = new ArrayList<>();
        commentsList = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            issueItem = intent.getExtras().getParcelable(ISSUE_CONTENTS);
        }

        issueLabels = new ArrayList<String>();
        for (int i = 0; i < issueItem.getLabels().size(); i++)
            issueLabels.add(issueItem.getLabels().get(i).getName());

        labelsRecyclerAdapter = new IssueLabelsRecyclerAdapter(getContext(), issueLabels, issueItem);
        setUpEventsAndComments(issueItem);
        //setUpEvents(issueItem);
        //setUpComments(issueItem);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_issue, container, false);
        ButterKnife.bind(this, rootView);
        tf_1 = Typeface.createFromAsset(getActivity().getResources().getAssets(), "font/RobotoCondensed-Regular.ttf");
        tf_2 = Typeface.createFromAsset(getActivity().getResources().getAssets(), "font/Roboto-Medium.ttf");

        issuesContentsProgress.setVisibility(View.VISIBLE);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(issueLabels.size() <= 0){
            labelsLayout.setVisibility(View.GONE);
        }

        issue_author.setText(String.format("%s opened this issue on %s", issueItem.getUser().getLogin(), Utility.dateFormatConversion(issueItem.getUpdatedAt())));
        issue_number.setText(String.format("Issue #%s", issueItem.getNumber()));
        issue_title.setText(issueItem.getTitle());

        //collapsingToolbarLayout.setTitle(issueItem.getTitle());
        //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingToolbarTextAppearance);

        if(issueItem.getBody().length() != 0) {
            issue_body.loadMarkdown(issueItem.getBody(),"file:///assets/github-markdown.js");
        }else{
            issue_body.loadMarkdown(getContext().getResources().getString(R.string.issues_placeholder_description));
        }
        //issue_body.setTypeface(tf_1);

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

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        commentsRecyclerView.setLayoutManager(layoutManager2);
        commentsRecyclerView.setAdapter(issueEventsAndCommentsAdapter);

        return rootView;
    }

    public void setUpEventsAndComments(IssueItem item){
        eventsRecyclerAdapter = new IssueEventsRecyclerAdapter(getContext(), new ArrayList<IssueEventsJson>());
        //issueCommentsRecyclerAdapter = new IssueCommentsRecyclerAdapter(getContext(), new ArrayList<IssueCommentsJson>());
        issueEventsAndCommentsAdapter = new IssueEventsAndCommentsRecyclerAdapter(getContext(),new ArrayList<Object>());
        gitHubEndpointInterface = ServiceGenerator.createService(GitHubEndpointInterface.class);

        Observable<ArrayList<IssueCommentsJson>> commentsObservable = gitHubEndpointInterface
                .getIssueCommentsWithUrlRx(item.getCommentsUrl())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ArrayList<IssueEventsJson>> eventsObservable = gitHubEndpointInterface
                .getIssueEventsWithUrlRx(item.getEventsUrl())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<IssueEventsAndComments> combinedObservable = Observable.zip(
                commentsObservable, eventsObservable, new Func2<ArrayList<IssueCommentsJson>, ArrayList<IssueEventsJson>, IssueEventsAndComments>() {
                    @Override
                    public IssueEventsAndComments call(ArrayList<IssueCommentsJson> issueCommentsJsons, ArrayList<IssueEventsJson> issueEventsJsons) {
                        return new IssueEventsAndComments(issueCommentsJsons,issueEventsJsons);
                    }
                }
        );

        combinedObservable.subscribe(new Subscriber<IssueEventsAndComments>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(IssueEventsAndComments issueEventsAndComments) {

                TreeMap tm = new TreeMap();
                eventsList = issueEventsAndComments.issueEvents;
                commentsList = issueEventsAndComments.issueComments;

                for(IssueEventsJson elem: eventsList) {
                    if (elem.getEvent().compareTo("labeled") == 0 ||
                            elem.getEvent().compareTo("unlabeled") == 0) {
                        eventsRecyclerAdapter.addItem(elem);
                    } else {
                        try {
                            tm.put(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                                    .parse(elem.getCreatedAt()).getTime(), elem);
                        } catch (ParseException e) {
                        }
                    }
                }

                for(IssueCommentsJson elem: commentsList){
                    try {
                        tm.put(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                                .parse(elem.getCreatedAt()).getTime(), elem);
                    } catch (ParseException e) {
                    }
                }

                Set set = tm.entrySet();
                Iterator it = set.iterator();
                for (Object elem:set) {
                    Map.Entry me = (Map.Entry)elem;
                    issueEventsAndCommentsAdapter.addItem(me.getValue());
                };

                /*while(it.hasNext()){
                    Map.Entry me = (Map.Entry)it.next();
                    issueEventsAndCommentsAdapter.addItem(me.getValue());
                }*/

                issuesContentsProgress.setVisibility(View.GONE);
            }
        });
    }

    /*
        Modified on 22nd January 2017
        Replacing the Retrofit calls with ReactiveX Observable calls
    */

    /*public void setUpEvents(IssueItem item) {
        eventsRecyclerAdapter = new IssueEventsRecyclerAdapter(getContext(), new ArrayList<IssueEventsJson>());
        gitHubEndpointInterface = ServiceGenerator.createService(GitHubEndpointInterface.class);
        Call<ArrayList<IssueEventsJson>> call = gitHubEndpointInterface.getIssueEventsWithUrl(item.getEventsUrl());
        call.enqueue(new Callback<ArrayList<IssueEventsJson>>() {
            @Override
            public void onResponse(Call<ArrayList<IssueEventsJson>> call, Response<ArrayList<IssueEventsJson>> response) {
                if(response.isSuccessful()){
                    ArrayList<IssueEventsJson> list = response.body();

                    for(IssueEventsJson elem: list) {
                        if(elem.getEvent().compareTo("labeled") == 0 ||
                                elem.getEvent().compareTo("unlabeled") == 0)
                            eventsRecyclerAdapter.addItem(elem);
                        else
                            eventsList.add(elem);
                    }
                    eventsRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IssueEventsJson>> call, Throwable t) {

            }
        });
    }

    public void setUpComments(final IssueItem item) {
        if (item.getComments() > 0) {
            issueCommentsRecyclerAdapter = new IssueCommentsRecyclerAdapter(getContext(), new ArrayList<IssueCommentsJson>());
            gitHubEndpointInterface = ServiceGenerator.createService(GitHubEndpointInterface.class);
            Call<ArrayList<IssueCommentsJson>> call = gitHubEndpointInterface.getIssueCommentsWithUrl(item.getCommentsUrl());
            call.enqueue(new Callback<ArrayList<IssueCommentsJson>>() {
                @Override
                public void onResponse(Call<ArrayList<IssueCommentsJson>> call, Response<ArrayList<IssueCommentsJson>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<IssueCommentsJson> list = response.body();
                        if (list.size() > 0) {
                            for (IssueCommentsJson elem : list) {
                                issueCommentsRecyclerAdapter.addItem(elem);
                                commentsList.add(elem);
                            }
                            issueCommentsRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<IssueCommentsJson>> call, Throwable t) {

                }
            });
        }
    }*/
}
