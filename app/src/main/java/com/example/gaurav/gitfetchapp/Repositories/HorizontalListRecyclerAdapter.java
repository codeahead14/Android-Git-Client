package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;

/**
 * Created by GAURAV on 05-09-2016.
 */
public class HorizontalListRecyclerAdapter extends RecyclerView.Adapter<HorizontalListRecyclerAdapter.HorizontalViewHolder>{
    private static final String TAG = HorizontalListRecyclerAdapter.class.getName();
    private Context mContext;
    private ArrayList<String> itemList;
    private OnItemClick onItemClick;

    public HorizontalListRecyclerAdapter(Context context, ArrayList<String> list){
        mContext = context;
        itemList = list;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.repository_file_navigation_list_item,parent,false);
        HorizontalViewHolder horizontalViewHolder = new HorizontalViewHolder(view);
        return horizontalViewHolder;
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        holder.itemName.setText(itemList.get(position));
    }

    public void addItem(String item){
        itemList.add(item);
        Log.v(TAG,"adding item: "+itemList.get(0));
        notifyItemInserted(itemList.size()-1);
    }

    public void removeItem(String item){
        int index = itemList.indexOf(item);
        for(int i = itemList.size()-1; i >index ; i--) {
            itemList.remove(i);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(itemList.size() > 0)
            return itemList.size();
        return 0;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView itemName;

        public HorizontalViewHolder(View view){
            super(view);
            itemName = (TextView) view.findViewById(R.id.nav_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPos = getAdapterPosition();
            Log.v(TAG,"click pos " + clickPos );
            StringBuilder builder = new StringBuilder();
            if(clickPos == 0)
                builder.append(itemList.get(clickPos)).append("/");
            for(int i=1; i<=clickPos; i++)
                builder.append(itemList.get(i)).append("/");
            onItemClick.OnItemClickListener(builder.toString());
        }
    }

    public void setItemListener(OnItemClick item){
        onItemClick = item;
    }

    public interface OnItemClick{
        void OnItemClickListener(String path);
    }
}
