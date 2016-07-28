package com.example.gaurav.gitfetchapp;

import android.support.v4.app.Fragment;
import android.app.FragmentContainer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roger.catloadinglibrary.CatLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GAURAV on 28-07-2016.
 */
public class RepositoryFragment extends Fragment {
    private static final String TAG = RepositoryFragment.class.getName();
    private View rootView;

    @BindView(R.id.repoResponse) TextView textView;

    public RepositoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter,
                             Bundle savedInstanceState){
        rootView = (View) inflater.inflate(R.layout.fragment_repositories, containter, false);
        ButterKnife.bind(getActivity(),rootView);
        textView.setText("Your Repositories");
        return rootView;
    }

}
