package com.example.gaurav.gitfetchapp.SearchGit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.gitfetchapp.CircleTransform;
import com.example.gaurav.gitfetchapp.GitHubEndpointInterface;
import com.example.gaurav.gitfetchapp.PostLoginActivity;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.ServiceGenerator;
import com.example.gaurav.gitfetchapp.UserInfo.User;
import com.example.gaurav.gitfetchapp.UserInfoActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GAURAV on 21-08-2016.
 */
public class SearchUserRecyclerAdapter extends RecyclerView.Adapter<SearchUserRecyclerAdapter.SearchViewHolder> {
    private static final String TAG = SearchUserRecyclerAdapter.class.getName();
    private List<Item> items;
    private Context context;


    public SearchUserRecyclerAdapter(Context context, List<Item> items){
        Log.v(TAG,"In Constructor");
        this.context = context;
        this.items = items;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(TAG,"in createView holder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_user_item,parent,false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Log.v(TAG,"in bindView holder");
        holder.search_result_textView.setText(items.get(position).getLogin());
        Picasso.with(context)
                .load(items.get(position).getAvatarUrl())
                .transform(new CircleTransform())
                .into(holder.search_result_ImageView);
    }

    @Override
    public int getItemCount() {
        if(items.size() != 0) {
            Log.v(TAG,"size: "+items.size());
            return items.size();
        }
        return 0;
    }

    public void addItem(Item item){
        items.add(item);
        notifyItemInserted(items.size()-1);
        Log.v(TAG,"added item: "+items.size());
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView search_result_textView;
        private ImageView search_result_ImageView;

        public SearchViewHolder(View view){
            super(view);
            search_result_textView = (TextView) view.findViewById(R.id.search_result_text);
            search_result_ImageView = (ImageView)view.findViewById(R.id.search_result_img);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int clickPos = getAdapterPosition();
            final Intent intent = new Intent(context, UserInfoActivity.class);

            GitHubEndpointInterface gitHubEndpointInterface = ServiceGenerator.createService(
                    GitHubEndpointInterface.class);
            Call<User> call = gitHubEndpointInterface.getUserDetails(items.get(clickPos).getLogin());//intentData[0]);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User item = response.body();
                        intent.putExtra(PostLoginActivity.USER_DETAILS,item);
                        //context.startActivity(intent, options.toBundle());
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(context,"Request Failed: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
