package com.example.gaurav.gitfetchapp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by GAURAV on 02-10-2016.
 */
public abstract class EndlessLoadingRecyclerView extends RecyclerView.OnScrollListener {
    private int lastVisibleItemPosition, itemCount, threshold = 2;
    private boolean loading = false;
    private static final String TAG = EndlessLoadingRecyclerView.class.getName();
    private LinearLayoutManager linearLayoutManager;
    private int page_number = 1;

    public EndlessLoadingRecyclerView(LinearLayoutManager layoutManager){
        linearLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        itemCount = linearLayoutManager.getItemCount();

        if(loading){

        }

        if(dy > 0 && itemCount - lastVisibleItemPosition < threshold) {
            page_number += 1;
            OnLoadMore(loading, page_number);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public abstract void OnLoadMore(boolean loading, int page_number);
}
