package com.example.gaurav.gitfetchapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AbstractParentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbstractParentFragment extends Fragment {
    @BindView(R.id.networkLayout)
    RelativeLayout networkLayout;
    @BindView(R.id.networkButton)
    Button networkButton;


    public AbstractParentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AbstractParentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AbstractParentFragment newInstance(String param1, String param2) {
        AbstractParentFragment fragment = new AbstractParentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = (View)inflater.inflate(R.layout.fragment_abstract_parent, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }
}
