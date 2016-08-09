package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;

/**
 * Created by GAURAV on 06-08-2016.
 */
public class BranchRecyclerAdapter extends RecyclerView.Adapter<
        BranchRecyclerAdapter.BranchViewHolder> {
    private static final String TAG = BranchRecyclerAdapter.class.getName();
    private Context mContext;
    private ArrayList<BranchesJson> branches = new ArrayList<>();

    public BranchRecyclerAdapter(Context context, ArrayList<BranchesJson> item){
        this.mContext = context;
        branches = item;
    }

    @Override
    public BranchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.repository_branches_layout,
                parent,false);
        BranchViewHolder branchViewHolder = new BranchViewHolder(view);
        return branchViewHolder;
    }

    @Override
    public void onBindViewHolder(BranchViewHolder holder, int position) {
        holder.branch_name_textview.setText(branches.get(position).getName());
    }

    public void addItem(BranchesJson item){
        branches.add(item);
        notifyItemInserted(branches.size()-1);
    }

    public void clear(){
        branches.clear();
    }

    @Override
    public int getItemCount() {
        if(branches != null)
            return branches.size();
        return 0;
    }

    public class BranchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView branch_name_textview;
        private ImageButton branch_details_button;

        BranchViewHolder(View view){
            super(view);
            branch_name_textview = (TextView) view.findViewById(R.id.branch_name_text);
            branch_details_button = (ImageButton) view.findViewById(R.id.branch_expand_button);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
