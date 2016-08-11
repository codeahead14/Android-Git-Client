package com.example.gaurav.gitfetchapp.Gists;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.DividerItemDecoration;
import com.example.gaurav.gitfetchapp.Feeds.TimelineJson.Content;
import com.example.gaurav.gitfetchapp.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by GAURAV on 10-08-2016.
 */
public class GistsRecyclerAdapter extends
        RecyclerView.Adapter<GistsRecyclerAdapter.GistsRecyclerViewHolder> {
    private static final String TAG = GistsRecyclerAdapter.class.getName();
    private ArrayList<GistsJson> gistsJsons;
    private GistsFileRecyclerAdapter gistsFileAdapter;
    private Context mContext;

    public GistsRecyclerAdapter(Context context, ArrayList<GistsJson> gistsJson){
        this.mContext = context;
        this.gistsJsons = gistsJson;
        Log.v(TAG,"Constructor call");
    }

    public void addItem(GistsJson item){
        this.gistsJsons.add(item);
        notifyItemInserted(gistsJsons.size()-1);
    }

    public void clear(){
        gistsJsons.clear();
    }

    @Override
    public GistsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(TAG,"in createviewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.gists_item,parent,false);
        GistsRecyclerViewHolder viewHolder = new GistsRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GistsRecyclerViewHolder holder, int position) {
        Log.v(TAG,"in bindviewHolder");
        holder.gist_description_text_view.setText(gistsJsons.get(position).getDescription());

        gistsFileAdapter = new GistsFileRecyclerAdapter(mContext,new ArrayList<Filename>());
        holder.gist_file_recyclerview.setAdapter(gistsFileAdapter);

        Map<String, Filename> filenameMap = gistsJsons.get(position).getFiles();
        Iterator it = filenameMap.entrySet().iterator();
        String[] fileNames = new String[filenameMap.size()];
        int count=0;
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            fileNames[count] = (String)pair.getKey();
            //gistsFileAdapter.addItem((String)pair.getKey());
            Filename obj = (Filename)pair.getValue();
            gistsFileAdapter.addItem(obj);
            Log.v(TAG,"filename: = "+ obj.getFilename());
        }
    }

    @Override
    public int getItemCount() {

        if (gistsJsons != null)
            return gistsJsons.size();
        return 0;
    }

    public class GistsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //@BindView(R.id.gist_description_text) TextView gist_description_text_view;

        private TextView gist_description_text_view;
        private RecyclerView gist_file_recyclerview;

        public GistsRecyclerViewHolder(View view){
            super(view);
            gist_description_text_view = (TextView) view.findViewById(R.id.gist_description_text);
            gist_file_recyclerview = (RecyclerView) view.findViewById(R.id.gists_file_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            gist_file_recyclerview.setLayoutManager(layoutManager);
            gist_file_recyclerview.setOnClickListener(this);
            //RecyclerView.ItemDecoration itemDecoration = new
              //      DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST);
            //  gist_file_recyclerview.addItemDecoration(itemDecoration);
        }

        @Override
        public void onClick(View view) {
            Log.v(TAG, "Inside onClick");
        }
    }
}
