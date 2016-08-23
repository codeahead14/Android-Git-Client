package com.example.gaurav.gitfetchapp.Widgets;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.gaurav.gitfetchapp.MainActivity;
import com.example.gaurav.gitfetchapp.PostLoginActivity;
import com.example.gaurav.gitfetchapp.PreLoginDeciderActivity;
import com.example.gaurav.gitfetchapp.R;

/**
 * Created by GAURAV on 23-08-2016.
 */
public class RepositoriesListWidgetProvider extends AppWidgetProvider {
    private static final String TAG = RepositoriesListWidgetProvider.class.getName();
    String description = "User Avatar";
    //@Override
    //public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
      //  context.startService(new Intent(context, RepositoryWidgetIntentService.class));
    //}

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Log.v(TAG,"In On Update");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.repo_widget_layout);

            // Add the data to the RemoteViews
            views.setImageViewResource(R.id.widget_userimage, R.drawable.repo_12x16);
            // Content Descriptions for RemoteViews were only added in ICS MR1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                setRemoteContentDescription(views, description);
            }
            views.setTextViewText(R.id.widget_username_title, "Repositories");

            Intent intent = new Intent(context, PreLoginDeciderActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget_username_title, pendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                setRemoteAdapter(context, views);
            } else {
                setRemoteAdapterV11(context, views);
            }

            Intent clickIntentTemplate = new Intent(context, PreLoginDeciderActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate);
            views.setEmptyView(R.id.widget_list, R.id.widget_empty);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    /*@Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        context.startService(new Intent(context, RepositoryWidgetIntentService.class));
    }*/

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, getClass()));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, RepositoryWidgetRemoteService.class));
    }

    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list,
                new Intent(context, RepositoryWidgetRemoteService.class));
    }

    /*
    Replace with RepositoryWidgetIntentService.class to fetch REAL DATA
     */
    /*@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.v(TAG, "in update widget");
        int weatherArtResourceId = R.drawable.repo_12x16;
        String description = "User Avatar";

        // Perform this loop procedure for each Today widget
        for (int appWidgetId : appWidgetIds) {
            Log.v(TAG, "app widget id: "+appWidgetId);
            int layoutId = R.layout.repo_widget_layout;
            RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);

            // Add the data to the RemoteViews
            views.setImageViewResource(R.id.widget_userimage, weatherArtResourceId);
            // Content Descriptions for RemoteViews were only added in ICS MR1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                setRemoteContentDescription(views, description);
            }
            views.setTextViewText(R.id.widget_username_title, "codeahead14");

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(context, PostLoginActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget_username_title, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }*/

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews views, String description) {
        views.setContentDescription(R.id.widget_userimage, description);
    }
}
