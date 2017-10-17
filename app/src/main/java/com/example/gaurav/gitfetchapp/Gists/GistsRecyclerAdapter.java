package com.example.gaurav.gitfetchapp.Gists;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Repositories.FileViewActivity;
import com.example.gaurav.gitfetchapp.Repositories.RepositoryDetailActivity;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.Utility;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gaurav.gitfetchapp.Repositories.FileViewActivity.FILE_NAME;
import static com.example.gaurav.gitfetchapp.Repositories.FileViewActivity.FILE_URL;

/**
 * Created by GAURAV on 10-08-2016.
 */
public class GistsRecyclerAdapter extends
        RecyclerView.Adapter<GistsRecyclerAdapter.GistsRecyclerViewHolder> {
    private static final String TAG = GistsRecyclerAdapter.class.getName();
    private ArrayList<GistsJson> gistsJsons;
    private GistsFileRecyclerAdapter gistsFileAdapter;
    private Context mContext;
    private String firstFileUrl;
    private String firstFileName;
    private GitHubEndpointInterface gitHubEndpointInterface;
    private Filename objects[];
    private boolean fileListExpanded;
    private Typeface tf_1, tf_2;

    public GistsRecyclerAdapter(Context context, ArrayList<GistsJson> gistsJson){
        this.mContext = context;
        this.gistsJsons = gistsJson;
        this.fileListExpanded = false;
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
        objects = new Filename[filenameMap.size()];
        int count=0;
        gistsFileAdapter = new GistsFileRecyclerAdapter(mContext,new ArrayList<Filename>());
        holder.gist_file_recyclerview.setAdapter(gistsFileAdapter);
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            fileNames[count] = (String) pair.getKey();
            //gistsFileAdapter.addItem((String)pair.getKey());
            objects[count] = (Filename) pair.getValue();
            gistsFileAdapter.addItem(objects[count]);
            count++;
        }

        if(gistsJsons.get(position).getOwner() != null) {

            Picasso.with(mContext)
                    .load(gistsJsons.get(position).getOwner().getAvatarUrl())
                    .transform(new CircleTransform())
                    .into(holder.login_avatar_imageView);

            holder.login_filename_button.setText(String.format(mContext.getResources().getString(
                    R.string.login_filename), gistsJsons.get(position).getOwner().getLogin(),
                    fileNames[0]));
        }
        holder.login_filename_button.setTypeface(tf_1);

        Spanned text = Html.fromHtml("created on "+"<b>"+ Utility.formatDateString(
                gistsJsons.get(position).getCreatedAt())+"</b>");
        holder.gist_created_textView.setText(text);
        holder.gist_description_text_view.setText(gistsJsons.get(position).getDescription());
        holder.gist_file_textView.setText(String.format(mContext.getResources().getString(
                R.string.num_files),fileNames.length));
        holder.gist_comments_textView.setText(String.format(mContext.getResources().
                getString(R.string.num_comments),gistsJsons.get(position).getComments()));
        holder.gist_forks_textView.setText(String.format(mContext.getResources().getString(R.string.num_forks),
                0));
        holder.gist_stars_textView.setText(String.format(mContext.getResources().getString(R.string.num_stars),
                0));
    }

    @Override
    public int getItemCount() {
        Log.v(TAG,"gists size "+gistsJsons.size());
        if (gistsJsons != null)
            return gistsJsons.size();
        return 0;
    }

    private void fetchFileContents(String download_url, final String fileName){
        gitHubEndpointInterface = ServiceGenerator.createService(GitHubEndpointInterface.class);
        Call<ResponseBody> call = gitHubEndpointInterface.downloadFileWithDynamicUrlSync(download_url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");
                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    try{
                        reader = new BufferedReader(new InputStreamReader(
                                response.body().byteStream()));
                        String line;
                        try{
                            while ((line=reader.readLine())!=null) {
                                sb.append(line);
                                sb.append("\n");
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        };
                    }finally {

                    }
                    Log.v(TAG,sb.toString());
                    //file_name_textview.setText(sb.toString());
                    Intent intent = new Intent(mContext,FileViewActivity.class);
                    intent.putExtra(FILE_NAME,fileName);
                    intent.putExtra(FILE_URL,sb.toString());
                    mContext.startActivity(intent);
                    //boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                    //Log.d(TAG, "file download was a success? " + writtenToDisk);
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });
    }

    public class GistsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //@BindView(R.id.gist_description_text) TextView gist_description_text_view;

        private TextView gist_description_text_view;
        private TextView login_filename_button;
        private TextView gist_created_textView;
        private TextView gist_file_textView;
        private TextView gist_forks_textView;
        private TextView gist_comments_textView;
        private TextView gist_stars_textView;
        private ImageView login_avatar_imageView;
        private RecyclerView gist_file_recyclerview;

        public GistsRecyclerViewHolder(View view){
            super(view);
            tf_1 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/RobotoCondensed-Regular.ttf");
            tf_2 = Typeface.createFromAsset(mContext.getResources().getAssets(),"font/Roboto-Light.ttf");

            login_filename_button = (TextView) view.findViewById(R.id.login_filename_button);
            //login_filename_button.setTypeface(tf_1);
            gist_created_textView =  (TextView) view.findViewById(R.id.gist_created_text);
            gist_created_textView.setTypeface(tf_2);
            gist_file_textView =  (TextView) view.findViewById(R.id.gist_files_text);
            gist_forks_textView = (TextView) view.findViewById(R.id.gist_forks_text);
            gist_comments_textView = (TextView) view.findViewById(R.id.gist_comments_text);
            gist_stars_textView = (TextView) view.findViewById(R.id.gist_stars_text);
            gist_description_text_view = (TextView) view.findViewById(R.id.gist_description_text);
            gist_description_text_view.setTypeface(tf_2);
            login_avatar_imageView = (ImageView) view.findViewById(R.id.login_avatar_img);

            gist_file_recyclerview = (RecyclerView) view.findViewById(R.id.gists_file_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            gist_file_recyclerview.setLayoutManager(layoutManager);
            gist_file_recyclerview.setOnClickListener(this);
            gist_file_textView.setOnClickListener(this);
            login_filename_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //intent.putExtra("FILE_LIST")
            if (fileListExpanded) {
                gist_file_recyclerview.setVisibility(View.GONE);
                fileListExpanded = false;
            }else{
                fileListExpanded = true;
                gist_file_recyclerview.setVisibility(View.VISIBLE);
            }
        }
    }
}
