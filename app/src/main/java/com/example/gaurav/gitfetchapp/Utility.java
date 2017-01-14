package com.example.gaurav.gitfetchapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by GAURAV on 01-08-2016.
 */
public class Utility {
    private static final String TAG = Utility.class.getName();
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String dateFormat_1 = "MMM d, ''yy";
    public static final String dateFormat_2 = "dd-MMM-yyyy";

    public static String dateFormatConversion(String input){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(dateFormat_1, Locale.ENGLISH);

        try {
            return outputFormat.format(sdf.parse(input));
        } catch (ParseException e) {
            return input;
        }
    }

    public static String formatDateString(String input) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(dateFormat_2, Locale.ENGLISH);

        try {
            Date date = sdf.parse(input);
            return getTimeAgo(date.getTime());
            //return outputFormat.format(sdf.parse(input));
        } catch (ParseException e) {
            return input;
        }
    }

    public static boolean hasConnection(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }


    private void alert(Context context) {
        String title = "No Internet Connection";
        String message = Integer.toString(R.string.notOnline);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

    private static final long SECOND_MILLIS = 1000;
    private static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final long MONTH_MILLIS = 30 * DAY_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else if (diff < 30 * DAY_MILLIS) {
            return diff / DAY_MILLIS + " days ago";
        } else{
            if(diff/MONTH_MILLIS < 1)
                return diff / MONTH_MILLIS + " month ago";
            else
                return diff / MONTH_MILLIS + " months ago";
        }
    }
}