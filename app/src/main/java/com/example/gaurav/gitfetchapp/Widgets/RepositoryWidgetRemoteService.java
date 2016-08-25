package com.example.gaurav.gitfetchapp.Widgets;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.gaurav.gitfetchapp.PreLoginDeciderActivity;
import com.example.gaurav.gitfetchapp.R;
import com.example.gaurav.gitfetchapp.RepositoryDataBase.RepositoryContract;

/**
 * Created by GAURAV on 23-08-2016.
 */
public class RepositoryWidgetRemoteService extends RemoteViewsService {
    private static final String TAG = RepositoryWidgetRemoteService.class.getName();
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

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;
            private RemoteViews parentView;

            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                Uri uri = RepositoryContract.RepositoryEntry.buildRepositoryUriWithOwner(PreLoginDeciderActivity.getLoginName());
                Log.v(TAG,"Remote service: "+uri.toString());
                data = getContentResolver().query(uri, REPOSITORY_COLUMNS, null, null, null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    Log.v(TAG,"Invalid Position");
                    return null;
                }
                Log.v(TAG,"Cursor Not null");
                RemoteViews view = new RemoteViews(getPackageName(),
                        R.layout.repository_cardview);

                String name = data.getString(COLUMN_NAME);
                String language = data.getString(COLUMN_LANGUAGE);
                int stars = data.getInt(COLUMN_STARGAZERS_COUNT);
                int forks = data.getInt(COLUMN_FORKS_COUNT);
                String pushedAt = data.getString(COLUMN_PUSHED_AT);

                view.setTextViewText(R.id.repo_name_text,name);
                view.setTextViewText(R.id.languageText,language);
                view.setTextViewText(R.id.stargazerText,Integer.toString(stars));
                view.setTextViewText(R.id.forkText,Integer.toString(forks));
                view.setTextViewText(R.id.pushedText,pushedAt);

                final Intent intent = new Intent();
                view.setOnClickFillInIntent(R.id.widget_list_item, intent);

                return view;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.repository_cardview);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(1);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
