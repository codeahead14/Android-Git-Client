package com.example.gaurav.gitfetchapp.Issues;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by GAURAV on 25-08-2016.
 */
public class IssuesRecyclerAdapter extends RecyclerView.Adapter<IssuesRecyclerAdapter.IssuesViewHolder>  {
    private ArrayList<IssuesJson> issuesList;
    private Context mContext;

    public IssuesRecyclerAdapter(Context context, ArrayList<IssuesJson> items){
        this.mContext = context;
        this.issuesList = items;
    }


    @Override
    public IssuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.issues_layout_item, parent, false);
        IssuesViewHolder holder = new IssuesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IssuesViewHolder holder, int position) {
        Typeface tf_1 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
        Typeface tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");
        holder.issues_title.setText(issuesList.get(position).getTitle());
        String action = "open";

        if(issuesList.get(position).getState().compareTo("open") == 0) {
            action = "opened";
            holder.issues_image_icon.setImageResource(R.drawable.issue_opened_14x16);
        }else if(issuesList.get(position).getState().compareTo("close")==0) {
            action = "closed";
            holder.issues_image_icon.setImageResource(R.drawable.issue_closed_16x16);
        }
        Spanned str = Html.fromHtml("<b>#"+issuesList.get(position).getNumber()+"</b> "+
        action + " by <b>" + issuesList.get(position).getUser().getLogin() + "</b> on <b>"+
                issuesList.get(position).getCreatedAt() + "</b>");
        holder.issues_updated_desc.setText(str);
    }

    @Override
    public int getItemCount() {
        if (issuesList.size() != 0)
            return issuesList.size();
        return 0;
    }

    public void addItem(IssuesJson item){
        issuesList.add(item);
        notifyItemInserted(issuesList.size()-1);
    }

    public class IssuesViewHolder extends RecyclerView.ViewHolder{
        private ImageView issues_image_icon;
        private TextView issues_title;
        private TextView issues_updated_desc;

        public IssuesViewHolder(View view){
            super(view);
            issues_image_icon = (ImageView) view.findViewById(R.id.issues_image_icon);
            issues_title = (TextView) view.findViewById(R.id.issues_title);
            issues_updated_desc = (TextView) view.findViewById(R.id.issues_updated_desc);
        }
    }
}
