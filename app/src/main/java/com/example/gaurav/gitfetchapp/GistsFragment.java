package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.gaurav.gitfetchapp.Gists.GistsJson;
import com.example.gaurav.gitfetchapp.Gists.GistsRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GistsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GistsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GistsFragment extends Fragment {
    private static final String TAG = GistsFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private GistsRecyclerAdapter gistsRecyclerAdapter;

    @BindView(R.id.gists_recycler_view) RecyclerView recyclerView;

    public GistsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GistsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GistsFragment newInstance(String param1, String param2) {
        GistsFragment fragment = new GistsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        gistsRecyclerAdapter = new GistsRecyclerAdapter(getContext(), new ArrayList<GistsJson>());

        GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                GitHubEndpointInterface.class);
        Call<ArrayList<GistsJson>> call = gitHubEndpointInterface.getPrivateGists(//"hemanth");
                MainActivityFragment.userNameField);
        call.enqueue(new Callback<ArrayList<GistsJson>>() {
            @Override
            public void onResponse(Call<ArrayList<GistsJson>> call, Response<ArrayList<GistsJson>> response) {
                ArrayList<GistsJson> gists = response.body();
                gistsRecyclerAdapter.clear();
                for(GistsJson elem: gists)
                    gistsRecyclerAdapter.addItem(elem);
                gistsRecyclerAdapter.notifyDataSetChanged();
                Log.v(TAG,"gists: "+gists.get(0).getFiles().size());
            }

            @Override
            public void onFailure(Call<ArrayList<GistsJson>> call, Throwable t) {
                Log.v(TAG,"Failed Miserably in Gists"+t.getMessage());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.deepPurple500)));
        //new ColorDrawable(getResources().getColor(R.color.red600)));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gists");

        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.setStatusBarColor(getResources().getColor(R.color.red900));
        window.setStatusBarColor(getResources().getColor(R.color.deepPurple800));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gists, container, false);
        ButterKnife.bind(this,rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        /*RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);*/
        recyclerView.setAdapter(gistsRecyclerAdapter);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
