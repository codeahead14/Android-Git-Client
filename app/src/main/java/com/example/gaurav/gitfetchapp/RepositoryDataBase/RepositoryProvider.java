package com.example.gaurav.gitfetchapp.RepositoryDataBase;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by GAURAV on 21-08-2016.
 */
public class RepositoryProvider extends ContentProvider {
    private static final String TAG = RepositoryProvider.class.getName();
    private UriMatcher uriMatcher = buildUriMatcher();
    private static final int REPOSITORY = 100;
    private static final int REPOSITORY_WITH_OWNER = 101;
    private static final int REPOSITORY_WITH_OWNER_AND_NAME = 102;
    private static final int OWNER = 103;

    private RepositoryDBHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RepositoryContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,RepositoryContract.PATH_REPOSITORY,REPOSITORY);
        matcher.addURI(authority,RepositoryContract.PATH_REPOSITORY + "/*",REPOSITORY_WITH_OWNER);
        matcher.addURI(authority,RepositoryContract.PATH_REPOSITORY + "/*/*",REPOSITORY_WITH_OWNER_AND_NAME);
        matcher.addURI(authority,RepositoryContract.PATH_OWNER,OWNER);

        return matcher;
    }

    private static final SQLiteQueryBuilder sRepositoryByOwnerNameQueryBuilder;

    static{
        sRepositoryByOwnerNameQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sRepositoryByOwnerNameQueryBuilder.setTables(
                RepositoryContract.RepositoryEntry.TABLE_NAME + " INNER JOIN " +
                        RepositoryContract.OwnerEntry.TABLE_NAME +
                        " ON " + RepositoryContract.RepositoryEntry.TABLE_NAME +
                        "." + RepositoryContract.RepositoryEntry.COLUMN_OWNER_KEY +
                        " = " + RepositoryContract.OwnerEntry.TABLE_NAME +
                        "." + RepositoryContract.OwnerEntry._ID);
    }

    //owner.login = ?
    private static final String sOwnerLoginSelection =
            RepositoryContract.OwnerEntry.TABLE_NAME+
                    "." + RepositoryContract.OwnerEntry.COLUMN_LOGIN + " = ? ";

    //owner.login = ? AND repository.name = ?
    private static final String sOwnerLoginAndRepoNameSelection =
            RepositoryContract.OwnerEntry.TABLE_NAME +
                    "." + RepositoryContract.OwnerEntry.COLUMN_LOGIN + " = ? AND " +
                    RepositoryContract.RepositoryEntry.COLUMN_NAME + " = ? ";

    private Cursor getRepositoryByOwner(Uri uri, String[] projection, String sortOrder) {
        String owner = RepositoryContract.RepositoryEntry.getOwnerFromUri(uri);
        Log.v(TAG,"uri: "+uri+" ");
        //String repoName = RepositoryContract.RepositoryEntry.getRepoNameFromUri(uri);

        String[] selectionArgs;
        String selection;

        //if (repoName == null) {
            selection = sOwnerLoginSelection;
            selectionArgs = new String[]{owner};
        //} else {
          //  selectionArgs = new String[]{owner, repoName};
          //  selection = sOwnerLoginAndRepoNameSelection;
        //}

        return sRepositoryByOwnerNameQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getRepositoryByOwnerAndRepoName(
            Uri uri, String[] projection, String sortOrder) {
        String owner = RepositoryContract.RepositoryEntry.getOwnerFromUri(uri);
        String repoName = RepositoryContract.RepositoryEntry.getRepoNameFromUri(uri);

        return sRepositoryByOwnerNameQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                sOwnerLoginAndRepoNameSelection,
                new String[]{owner, repoName},
                null,
                null,
                sortOrder
        );
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = RepositoryDBHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match){
            case REPOSITORY:
                return RepositoryContract.RepositoryEntry.CONTENT_TYPE;
            case REPOSITORY_WITH_OWNER:
                return RepositoryContract.RepositoryEntry.CONTENT_TYPE;
            case REPOSITORY_WITH_OWNER_AND_NAME:
                return RepositoryContract.RepositoryEntry.CONTENT_ITEM_TYPE;
            case OWNER:
                return RepositoryContract.OwnerEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unkown Uri: "+ uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case REPOSITORY:
                rowsDeleted = db.delete(
                        RepositoryContract.RepositoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case OWNER:
                rowsDeleted = db.delete(
                        RepositoryContract.OwnerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowsDeleted;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            // "repository/*/*"
            case REPOSITORY_WITH_OWNER_AND_NAME:
            {
                retCursor = getRepositoryByOwnerAndRepoName(uri, projection, sortOrder);
                break;
            }
            // "repository/*"
            case REPOSITORY_WITH_OWNER: {
                retCursor = getRepositoryByOwner(uri, projection, sortOrder);
                break;
            }
            // "repository"
            case REPOSITORY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        RepositoryContract.RepositoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            // "owner"
            case OWNER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        RepositoryContract.OwnerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;
        db.beginTransaction();
        switch (match) {
            case REPOSITORY: {
                long _id = db.insert(RepositoryContract.RepositoryEntry.TABLE_NAME, null, contentValues);
                if (_id > 0){
                    returnUri = RepositoryContract.RepositoryEntry.buildRepositoryUri(_id);
                db.setTransactionSuccessful();
                }else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                db.endTransaction();
                break;
            }
            case OWNER: {
                long _id = db.insert(RepositoryContract.OwnerEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 ) {
                    returnUri = RepositoryContract.OwnerEntry.buildOwnerUri(_id);
                    db.setTransactionSuccessful();
                }else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                db.endTransaction();
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsUpdated;
        db.beginTransaction();
        switch (match) {
            case REPOSITORY:
                rowsUpdated = db.update(RepositoryContract.RepositoryEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                db.setTransactionSuccessful();
                db.endTransaction();
                break;
            case OWNER:
                rowsUpdated = db.update(RepositoryContract.OwnerEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case REPOSITORY:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(RepositoryContract.RepositoryEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
