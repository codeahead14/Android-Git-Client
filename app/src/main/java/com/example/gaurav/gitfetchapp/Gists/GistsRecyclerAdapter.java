package com.example.gaurav.gitfetchapp.Gists;

import android.content.Context;
import android.graphics.Typeface;
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

import org.w3c.dom.Text;

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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.gists_item,parent,false);
        GistsRecyclerViewHolder viewHolder = new GistsRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GistsRecyclerViewHolder holder, int position) {
        Map<String, Filename> filenameMap = gistsJsons.get(position).getFiles();
        Iterator it = filenameMap.entrySet().iterator();
        String[] fileNames = new String[filenameMap.size()];
        int count=0;
        gistsFileAdapter = new GistsFileRecyclerAdapter(mContext,new ArrayList<Filename>());
        //holder.gist_file_recyclerview.setAdapter(gistsFileAdapter);
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            fileNames[count] = (String)pair.getKey();
            //gistsFileAdapter.addItem((String)pair.getKey());
            Filename obj = (Filename)pair.getValue();
            gistsFileAdapter.addItem(obj);
            Log.v(TAG,"filename: = "+ obj.getFilename());
        }

        holder.login_filename_textView.setText(String.format(mContext.getResources().getString(
                R.string.login_filename),gistsJsons.get(position).getOwner().getLogin(),
                fileNames[0]));
        holder.gist_created_textView.setText(String.format(mContext.getString(R.string.created_gist),
                gistsJsons.get(position).getCreatedAt()));
        holder.gist_description_text_view.setText(gistsJsons.get(position).getDescription());
        holder.gist_file_textView.setText(String.format(mContext.getResources().getString(
                R.string.num_files),fileNames.length));
        holder.gist_comments_textView.setText(String.format(mContext.getResources().
                getString(R.string.num_comments),gistsJsons.get(position).getComments()));
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
        private TextView login_filename_textView;
        private TextView gist_created_textView;
        private TextView gist_file_textView;
        private TextView gist_forks_textView;
        private TextView gist_comments_textView;
        private TextView gist_stars_textView;
        private RecyclerView gist_file_recyclerview;

        public GistsRecyclerViewHolder(View view){
            super(view);
            Typeface tf_1 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
            Typeface tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");

            login_filename_textView = (TextView) view.findViewById(R.id.login_filename_text);
            login_filename_textView.setTypeface(tf_1);
            gist_created_textView =  (TextView) view.findViewById(R.id.gist_created_text);
            gist_created_textView.setTypeface(tf_2);
            gist_file_textView =  (TextView) view.findViewById(R.id.gist_files_text);
            gist_forks_textView = (TextView) view.findViewById(R.id.gist_forks_text);
            gist_comments_textView = (TextView) view.findViewById(R.id.gist_comments_text);
            gist_stars_textView = (TextView) view.findViewById(R.id.gist_stars_text);
            gist_description_text_view = (TextView) view.findViewById(R.id.gist_description_text);
            gist_description_text_view.setTypeface(tf_2);

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
