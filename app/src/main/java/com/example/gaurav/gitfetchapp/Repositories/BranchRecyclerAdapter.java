package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.DataBus.BusProvider;
import com.example.gaurav.gitfetchapp.DataBus.UserInteractionEvent;
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
    private String selected_branch;
    private int selectedBranchPosition;

    public BranchRecyclerAdapter(Context context, ArrayList<BranchesJson> item, String default_branch){
        this.mContext = context;
        branches = item;
        this.selected_branch = default_branch;
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
        if(selected_branch.compareTo(branches.get(position).getName()) == 0){
            holder.branch_name_textview.setSelected(true);
            selectedBranchPosition = position;
        }
        //holder.branch_name_textview.setSelected(selected_branch.compareTo(branches.get(position).getName()) == 0);
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
            Typeface tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");
            branch_name_textview = (TextView) view.findViewById(R.id.branch_name_text);
            branch_name_textview.setTypeface(tf_2);
            branch_details_button = (ImageButton) view.findViewById(R.id.branch_expand_button);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPos = getAdapterPosition();
            selected_branch = branches.get(clickPos).getName();
            Log.v(TAG,"selected branch "+selected_branch);
            selectedBranchPosition = clickPos;
            //branch_name_textview.setSelected(selected_branch.compareTo(
              //      branches.get(selectedBranchPosition).getName())==0);
            //branch_name_textview.setSelected(true);
            BusProvider.getInstance().post(new UserInteractionEvent(true, selected_branch));
        }
    }
}
