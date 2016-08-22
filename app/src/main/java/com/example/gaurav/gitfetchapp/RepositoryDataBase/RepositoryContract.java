package com.example.gaurav.gitfetchapp.RepositoryDataBase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by GAURAV on 21-08-2016.
 */
public class RepositoryContract {
    public static final String CONTENT_AUTHORITY = "com.example.gaurav.gitfetchapp";
    // Build Base URI for content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_REPOSITORY = "repository";
    public static final String PATH_OWNER = "owner";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class OwnerEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
              BASE_CONTENT_URI.buildUpon().appendPath(PATH_OWNER).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OWNER;
        public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OWNER;

        public static final String TABLE_NAME = "owner";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_AVATAR_URL = "avatar_url";
        public static final String COLUMN_GRAVATAR_ID = "gravatar_id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_HTML_URL = "html_url";
        public static final String COLUMN_FOLLOWERS_URL = "followers_url";
        public static final String COLUMN_FOLLOWING_URL = "following_url";
        public static final String COLUMN_GISTS_URL = "gists_url";
        public static final String COLUMN_STARRED_URL = "starred_url";
        public static final String COLUMN_SUBSCRIPTION_URL = "subscription_url";
        public static final String COLUMN_ORGANIZATIONS_URL = "organizations_url";
        public static final String COLUMN_REPOS_URL = "repos_url";
        public static final String COLUMN_EVENTS_URL = "events_url";
        public static final String COLUMN_RECEIVED_EVENTS_URL = "received_events_url";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_SITE_ADMIN = "siteAdmin";

        public static Uri buildOwnerUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }

    public static final class RepositoryEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REPOSITORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REPOSITORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REPOSITORY;


        public static final String TABLE_NAME = "repository";

        public static final String COLUMN_OWNER_KEY = "owner_id";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FULL_NAME = "full_name";
        public static final String COLUMN_IS_PRIVATE = "is_private";
        public static final String COLUMN_HTML_URL = "html_url";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_FORK = "is_fork";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_FORKS_URL = "fork_url";
        public static final String COLUMN_KEY_URL = "key_url";
        public static final String COLUMN_COLLABORATORS_URL = "collaborators_url";
        public static final String COLUMN_TEAMS_URL = "teams_url";
        public static final String COLUMN_ISSUE_EVENTS_URL = "issue_events_url";
        public static final String COLUMN_EVENTS_URL = "events_url";
        public static final String COLUMN_ASSIGNEES_URL = "assignees_url";
        public static final String COLUMN_BRANCHES_URL = "branches_url";
        public static final String COLUMN_TAGS_URL = "tags_url";
        public static final String COLUMN_BLOBS_URL = "blobs_url";
        public static final String COLUMN_GIT_TAGS_URL= "git_tags_url";
        public static final String COLUMN_REFS_URL = "git_refs_url";
        public static final String COLUMN_TREES_URL = "trees_url";
        public static final String COLUMN_STATUSES_URL = "statuses_url";
        public static final String COLUMN_LANGUAGES_URL = "languages_url";
        public static final String COLUMN_STARGAZERS_URL = "stargazers_url";
        public static final String COLUMN_CONTRIBUTORS_URL = "contributors_url";
        public static final String COLUMN_SUBSCRIBERS_URL = "subscribers_url";
        public static final String COLUMN_SUBSCRIPTION_URL = "subscription_url";
        public static final String COLUMN_COMMITS_URL = "commits_url";
        public static final String COLUMN_GIT_COMMITS_URL = "git_commits_url";
        public static final String COLUMN_COMMENTS_URL = "comments_url";
        public static final String COLUMN_ISSUE_COMMENT_URL = "issue_comment_url";
        public static final String COLUMN_CONTENTS_URL = "contents_url";
        public static final String COLUMN_COMPARE_URL = "compare_url";
        public static final String COLUMN_MERGES_URL = "merges_url";
        public static final String COLUMN_ARCHIVE_URL = "archive_url";
        public static final String COLUMN_DOWNLOADS_URL = "downloads_url";
        public static final String COLUMN_ISSUES_URL = "issues_url";
        public static final String COLUMN_PULLS_URL = "pulls_url";
        public static final String COLUMN_MILESTONES_URL = "milestones_url";
        public static final String COLUMN_NOTIFICATIONS_URL = "notifications_url";
        public static final String COLUMN_LABELS_URL = "labels_url";
        public static final String COLUMN_RELEASES_URL = "releases_url";
        public static final String COLUMN_DEPLOYMENTS_URL = "deployments_url";
        public static final String COLUMN_CREATED_AT= "created_at";
        public static final String COLUMN_PUSHED_AT = "pushed_at";
        public static final String COLUMN_GIT_URL = "git_url";
        public static final String COLUMN_SSH_URL = "ssh_url";
        public static final String COLUMN_CLONE_URL = "clone_url";
        public static final String COLUMN_SVN_URL = "svn_url";
        public static final String COLUMN_HOMEPAGE = "homepage";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_STARGAZERS_COUNT = "stargazers_count";
        public static final String COLUMN_WATCHERS_COUNT = "watchers_count";
        public static final String COLUMN_LANGUAGE = "language";
        public static final String COLUMN_HAS_ISSUES = "has_issues";
        public static final String COLUMN_HAS_DOWNLOADS = "has_downloads";
        public static final String COLUMN_HAS_WIKI = "has_wiki";
        public static final String COLUMN_HAS_PAGES = "has_pages";
        public static final String COLUMN_FORKS_COUNT = "forks_count";
        public static final String COLUMN_MIRROR_URL = "mirror_url";
        public static final String COLUMN_OPEN_ISSUES_COUNT = "open_issues_count";
        public static final String COLUMN_FORKS = "forks";
        public static final String COLUMN_OPEN_ISSUES = "open_issues";
        public static final String COLUMN_WATCHERS = "watchers";
        public static final String COLUMN_DEFAULT_BRANCH = "default_branch";

        public static Uri buildRepositoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRepositoryUriWithOwner(String ownerName){
            return CONTENT_URI.buildUpon().appendPath(ownerName).build();
        }

        public static Uri buildRepositoryUriWithOwnerAndRepoName(String ownerName, String repoName){
            return CONTENT_URI.buildUpon().appendPath(ownerName)
                    .appendPath(repoName).build();
        }

        public static String getOwnerFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getRepoNameFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }
    }
}
