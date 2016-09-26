package com.example.gaurav.gitfetchapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Nivedita on 24-09-2016.
 */
public abstract class RecyclerViewParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int VIEW_ITEM = 1;
    public final static int VIEW_PROG = 0;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_ITEM;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        public MaterialProgressBar materialProgressBar;

        public ProgressBarViewHolder(View view){
            super(view);
            materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.recyclerview_progress_bar);
        }
    }
}
