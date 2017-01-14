package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.DataBus.BusProvider;
import com.example.gaurav.gitfetchapp.DataBus.UserInteractionEvent;
import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BranchDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BranchDialogFragment extends DialogFragment {
    private static final String TAG = BranchDialogFragment.class.getName();
    private static final String REPO_DETAILS = "repo_details";
    private String[] mRepoDetails;
    private BranchRecyclerAdapter branchRecyclerAdapter;
    private GitHubEndpointInterface gitHubEndpointInterface;
    private static MaterialProgressBar mProgressBar;
    private static Button cancelButton;

    @BindView(R.id.dialogfragment_branch_recycler)
    RecyclerView dialog_branch_recyclerview;

    private OnBranchFragmentInteractionListener mListener;

    public BranchDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param params new String[] { repoLogin,repoName,repoBranch}
     * @return A new instance of fragment BranchDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BranchDialogFragment newInstance(String[] params) {
        BranchDialogFragment fragment = new BranchDialogFragment();
        Bundle args = new Bundle();
        args.putStringArray(REPO_DETAILS, params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRepoDetails = getArguments().getStringArray(REPO_DETAILS);
            branchRecyclerAdapter = new BranchRecyclerAdapter(getActivity(), new ArrayList<BranchesJson>(),
                    mRepoDetails[2]);
            gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class);
            Call<ArrayList<BranchesJson>> call = gitHubEndpointInterface.getUserBranches(
                    mRepoDetails[0], mRepoDetails[1]);
            if(Utility.hasConnection(getContext())){
                //avLoadingIndicatorView.show();
                call.enqueue(new Callback<ArrayList<BranchesJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<BranchesJson>> call, Response<ArrayList<BranchesJson>> response) {
                        ArrayList<BranchesJson> item = response.body();
                        mProgressBar.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.VISIBLE);
                        for (BranchesJson elem : item) {
                            branchRecyclerAdapter.addItem(elem);
                        }
                        branchRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<BranchesJson>> call, Throwable t) {
                        cancelActivity();
                    }

                });
            }else {
                Toast.makeText(getContext(), R.string.notOnline, Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
                cancelActivity();
            }
        }
    }

    private void cancelActivity(){
        dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = (View) inflater.inflate(R.layout.repository_branch_dialog, container, false);
        ButterKnife.bind(rootView);

        mProgressBar = (MaterialProgressBar) rootView.findViewById(R.id.dialogfragment_progress_bar);
        RecyclerView dialog_branch_recyclerview = (RecyclerView) rootView.findViewById(
                R.id.dialogfragment_branch_recycler);
        cancelButton = (Button) rootView.findViewById(R.id.dialogfragment_branch_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialog_branch_recyclerview.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        dialog_branch_recyclerview.addItemDecoration(itemDecoration);
        dialog_branch_recyclerview.setAdapter(branchRecyclerAdapter);

        String title = "BRANCHES";
        getDialog().setTitle(title);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG,"registering ");
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG,"unregistering ");
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Subscribe
    public void onBranchSelected(UserInteractionEvent event){
        Log.v(TAG,"receiving result");
        if(event.getResult()){
            Log.v(TAG,"result "+event.getName());
            cancelActivity();
            mListener = (OnBranchFragmentInteractionListener) getTargetFragment();
            mListener.branchFragmentInteractionListener(event.getName());
        }
    }

    interface OnBranchFragmentInteractionListener{
        void branchFragmentInteractionListener(String branchName);
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBranchFragmentInteractionListener) {
            mListener = (OnBranchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
