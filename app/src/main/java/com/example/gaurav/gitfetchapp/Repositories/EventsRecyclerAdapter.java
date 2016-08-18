package com.example.gaurav.gitfetchapp.Repositories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.Events.EventsJson;

import java.util.ArrayList;

/**
 * Created by GAURAV on 13-08-2016.
 */
public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>{
    private static final String TAG = EventsRecyclerAdapter.class.getName();
    private ArrayList<EventsJson> eventsJsonArrayList = new ArrayList<>();

    public EventsRecyclerAdapter(Context context, ArrayList<EventsJson> list){
        this.eventsJsonArrayList = list;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.events_item,parent,false);
        EventsViewHolder viewHolder = new EventsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        holder.event_date_text_view.setText(eventsJsonArrayList.get(position).getCreatedAt());
        holder.event_type_text_view.setText(eventsJsonArrayList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        if(eventsJsonArrayList != null)
            return eventsJsonArrayList.size();
        return 0;
    }

    public void addItem(EventsJson item){
        eventsJsonArrayList.add(item);
        notifyItemInserted(eventsJsonArrayList.size()-1);
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView event_date_text_view;
        private TextView event_type_text_view;

        public EventsViewHolder(View view){
            super(view);
            event_date_text_view = (TextView)view.findViewById(R.id.event_date_text);
            event_type_text_view = (TextView)view.findViewById(R.id.event_type_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
