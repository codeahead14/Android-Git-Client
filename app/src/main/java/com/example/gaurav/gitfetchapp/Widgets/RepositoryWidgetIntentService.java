package com.example.gaurav.gitfetchapp.Widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import com.example.gaurav.gitfetchapp.Events.Repo;
import com.example.gaurav.gitfetchapp.RepositoryDataBase.RepositoryContract;

/**
 * Created by GAURAV on 23-08-2016.
 */
public class RepositoryWidgetIntentService extends IntentService {
    private static final String TAG = RepositoryWidgetIntentService.class.getName();
    private static final String[] REPOSITORY_COLUMNS = {
            RepositoryContract.RepositoryEntry.COLUMN_NAME,
            RepositoryContract.RepositoryEntry.COLUMN_LANGUAGE,
            RepositoryContract.RepositoryEntry.COLUMN_STARGAZERS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_FORKS_COUNT,
            RepositoryContract.RepositoryEntry.COLUMN_PUSHED_AT
    };

    private static final int COLUMN_NAME = 0;
    private static final int COLUMN_LANGUAGE = 1;
    private static final int COLUMN_STARGAZERS_COUNT = 2;
    private static final int COLUMN_FORKS_COUNT = 3;
    private static final int COLUMN_PUSHED_AT = 4;

    public RepositoryWidgetIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                RepositoryWidgetIntentService.class));


    }
}
