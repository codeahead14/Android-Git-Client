package com.example.gaurav.gitfetchapp.Gists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.FileViewActivity;

import java.util.ArrayList;

import static com.example.gaurav.gitfetchapp.Repositories.FileViewActivity.FILE_NAME;
import static com.example.gaurav.gitfetchapp.Repositories.FileViewActivity.FILE_URL;

/**
 * Created by GAURAV on 11-08-2016.
 */
public class GistsFileRecyclerAdapter extends
        RecyclerView.Adapter<GistsFileRecyclerAdapter.GistsFileViewHolder>{

    private static final String TAG = GistsFileRecyclerAdapter.class.getName();
    private Context mContext;
    private ArrayList<Filename> fileNameList = new ArrayList<>();

    public GistsFileRecyclerAdapter(Context context, ArrayList<Filename> list){
        this.mContext = context;
        this.fileNameList = list;
    }

    @Override
    public GistsFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.gists_file_list_item, parent, false);
        GistsFileViewHolder gistsFileViewHolder = new GistsFileViewHolder(view);
        return gistsFileViewHolder;
    }

    @Override
    public void onBindViewHolder(GistsFileViewHolder holder, int position) {
        holder.gists_fileName_textView.setText(fileNameList.get(position).getFilename());
    }

    public void clear(){
        fileNameList.clear();
    }

    public void addItem(Filename item){
        fileNameList.add(item);
        notifyItemInserted(fileNameList.size()-1);
    }

    @Override
    public int getItemCount() {
        if(fileNameList != null)
            return fileNameList.size();
        return 0;
    }

    public class GistsFileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView gists_fileName_textView;

        public GistsFileViewHolder(View view){
            super(view);
            gists_fileName_textView = (TextView) view.findViewById(R.id.gist_filename_text);
            gists_fileName_textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int clickPos = getAdapterPosition();
            Intent intent = new Intent(mContext,FileViewActivity.class);
            intent.putExtra(FILE_NAME,fileNameList.get(clickPos).getFilename());
            intent.putExtra(FILE_URL, fileNameList.get(clickPos).getRawUrl());
            mContext.startActivity(intent);
        }
    }
}
