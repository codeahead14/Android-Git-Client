package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;

import javax.xml.transform.Source;

/**
 * Created by GAURAV on 07-08-2016.
 */
public class SourceContentsAdapter extends RecyclerView.Adapter<SourceContentsAdapter.SourceContentsViewHolder> {
    private Context mContext;
    private String[] source_lines;

    public SourceContentsAdapter(Context context, String[] lines){
        this.mContext = context;
        this.source_lines = lines;
    }

    @Override
    public SourceContentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View sourceView = layoutInflater.inflate(R.layout.source_lines_layout,parent,false);
        SourceContentsViewHolder sourceContentsViewHolder = new SourceContentsViewHolder(sourceView);
        return sourceContentsViewHolder;
    }

    @Override
    public void onBindViewHolder(SourceContentsViewHolder holder, int position) {
        //holder.line_number_text_view.setText(position);
        holder.source_line_text_view.setText(source_lines[position]);
    }

    @Override
    public int getItemCount() {
        return source_lines.length;
    }

    public void addItem(String item){

    }

    public class SourceContentsViewHolder extends RecyclerView.ViewHolder {
        private TextView source_line_text_view;
        private TextView line_number_text_view;

        public SourceContentsViewHolder(View view){
            super(view);
            source_line_text_view = (TextView) view.findViewById(R.id.source_line_text);
            line_number_text_view = (TextView) view.findViewById(R.id.line_number_text);
        }
    }

}
