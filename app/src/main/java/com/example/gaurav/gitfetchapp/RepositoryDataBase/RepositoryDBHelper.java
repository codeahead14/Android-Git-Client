package com.example.gaurav.gitfetchapp.RepositoryDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gaurav.gitfetchapp.Repositories.Owner;
import com.example.gaurav.gitfetchapp.RepositoryDataBase.RepositoryContract.*;

import org.eclipse.egit.github.core.Repository;

/**
 * Created by GAURAV on 21-08-2016.
 */
public class RepositoryDBHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "repository.db";

    /** **/
    private static RepositoryDBHelper sInstance;

    /* Usage for Singleton Pattern
     * In any activity just pass the context and use the singleton method
      * MovieDBHelper helper = MovieDBHelper.getInstance(this);*/
    public static synchronized RepositoryDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new RepositoryDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /* Constructor is declared private as in that case it cannot be called from
     * outside and that help us keeping a Singleton Instance of Database
      * so as to memory leaks and unnecessary allocations.
       * One has to call getInstance to obtain a reference to the MovieDBHelper*/
    private RepositoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_OWNER_TABLE = "CREATE TABLE " + OwnerEntry.TABLE_NAME + " (" +
                OwnerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OwnerEntry.COLUMN_LOGIN + " TEXT UNIQUE NOT NULL, "
                + OwnerEntry.COLUMN_ID + " INTEGER NOT NULL, "
                + OwnerEntry.COLUMN_AVATAR_URL + " TEXT, "
                + OwnerEntry.COLUMN_GRAVATAR_ID + " TEXT, "
                + OwnerEntry.COLUMN_URL + " TEXT, "
                + OwnerEntry.COLUMN_HTML_URL + " TEXT, "
                + OwnerEntry.COLUMN_FOLLOWERS_URL + " TEXT, "
                + OwnerEntry.COLUMN_FOLLOWING_URL + " TEXT, "
                + OwnerEntry.COLUMN_GISTS_URL + " TEXT, "
                + OwnerEntry.COLUMN_STARRED_URL + " TEXT, "
                + OwnerEntry.COLUMN_SUBSCRIPTION_URL + " TEXT, "
                + OwnerEntry.COLUMN_ORGANIZATIONS_URL + " TEXT, "
                + OwnerEntry.COLUMN_REPOS_URL + " TEXT, "
                + OwnerEntry.COLUMN_EVENTS_URL + " TEXT, "
                + OwnerEntry.COLUMN_RECEIVED_EVENTS_URL + " TEXT, "
                + OwnerEntry.COLUMN_TYPE + " TEXT, "
                + OwnerEntry.COLUMN_SITE_ADMIN + " BOOLEAN "  +
                ");";


        final String SQL_CREATE_REPOSITORY_TABLE = "Create TABLE " + RepositoryEntry.TABLE_NAME + " (" +
                RepositoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RepositoryEntry.COLUMN_ID + " INTEGER NOT NULL, "
                + RepositoryEntry.COLUMN_OWNER_KEY + " INTEGER NOT NULL, "
                + RepositoryEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + RepositoryEntry.COLUMN_FULL_NAME + " TEXT, "
                + RepositoryEntry.COLUMN_IS_PRIVATE + " BOOLEAN, "
                + RepositoryEntry.COLUMN_HTML_URL + " TEXT, "
                + RepositoryEntry.COLUMN_DESCRIPTION + " TEXT, "
                + RepositoryEntry.COLUMN_IS_FORK + " BOOLEAN, "
                + RepositoryEntry.COLUMN_URL + " TEXT, "
                + RepositoryEntry.COLUMN_FORKS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_KEY_URL + " TEXT, "
                + RepositoryEntry.COLUMN_COLLABORATORS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_TEAMS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_ISSUE_EVENTS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_EVENTS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_ASSIGNEES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_BRANCHES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_TAGS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_BLOBS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_GIT_TAGS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_REFS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_TREES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_STATUSES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_LANGUAGES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_STARGAZERS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_CONTRIBUTORS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_SUBSCRIBERS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_SUBSCRIPTION_URL + " TEXT, "
                + RepositoryEntry.COLUMN_COMMITS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_GIT_COMMITS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_COMMENTS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_ISSUE_COMMENT_URL + " TEXT, "
                + RepositoryEntry.COLUMN_CONTENTS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_COMPARE_URL + " TEXT, "
                + RepositoryEntry.COLUMN_MERGES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_ARCHIVE_URL + " TEXT, "
                + RepositoryEntry.COLUMN_DOWNLOADS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_ISSUES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_PULLS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_MILESTONES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_NOTIFICATIONS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_LABELS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_RELEASES_URL + " TEXT, "
                + RepositoryEntry.COLUMN_DEPLOYMENTS_URL + " TEXT, "
                + RepositoryEntry.COLUMN_CREATED_AT + " TEXT, "
                + RepositoryEntry.COLUMN_PUSHED_AT + " TEXT, "
                + RepositoryEntry.COLUMN_GIT_URL + " TEXT, "
                + RepositoryEntry.COLUMN_SSH_URL + " TEXT, "
                + RepositoryEntry.COLUMN_CLONE_URL + " TEXT, "
                + RepositoryEntry.COLUMN_SVN_URL + " TEXT, "
                + RepositoryEntry.COLUMN_HOMEPAGE + " TEXT, "
                + RepositoryEntry.COLUMN_SIZE + " INTEGER, "
                + RepositoryEntry.COLUMN_STARGAZERS_COUNT + " INTEGER, "
                + RepositoryEntry.COLUMN_WATCHERS_COUNT + " INTEGER, "
                + RepositoryEntry.COLUMN_LANGUAGE + " TEXT, "
                + RepositoryEntry.COLUMN_HAS_ISSUES + " BOOLEAN, "
                + RepositoryEntry.COLUMN_HAS_DOWNLOADS + " BOOLEAN, "
                + RepositoryEntry.COLUMN_HAS_WIKI + " BOOLEAN, "
                + RepositoryEntry.COLUMN_HAS_PAGES + " BOOLEAN, "
                + RepositoryEntry.COLUMN_FORKS_COUNT + " INTEGER, "
                + RepositoryEntry.COLUMN_MIRROR_URL + " TEXT, "
                + RepositoryEntry.COLUMN_OPEN_ISSUES_COUNT + " INTEGER, "
                + RepositoryEntry.COLUMN_FORKS + " INTEGER, "
                + RepositoryEntry.COLUMN_OPEN_ISSUES + " INTEGER, "
                + RepositoryEntry.COLUMN_WATCHERS + " INTEGER, "
                + RepositoryEntry.COLUMN_DEFAULT_BRANCH + " TEXT, "

                // Set up the location column as a foreign key to location table.
                + " FOREIGN KEY (" + RepositoryEntry.COLUMN_OWNER_KEY + ") REFERENCES " +
                OwnerEntry.TABLE_NAME + " (" + OwnerEntry._ID + "), "

                // Using the Repository ID as well as Repository Name to be unique
                + " UNIQUE (" + RepositoryEntry.COLUMN_ID + ", " +
                RepositoryEntry.COLUMN_NAME + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_OWNER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REPOSITORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OwnerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RepositoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
