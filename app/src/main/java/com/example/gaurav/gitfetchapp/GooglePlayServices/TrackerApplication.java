package com.example.gaurav.gitfetchapp.GooglePlayServices;

import android.app.Application;

import com.example.gaurav.gitfetchapp.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by GAURAV on 24-08-2016.
 */
public class TrackerApplication extends Application {
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.track_app);
            analytics.enableAutoActivityReports(this);
            analytics.getLogger()
                    .setLogLevel(Logger.LogLevel.VERBOSE);
        }
        return mTracker;
    }

    //synchronized public Tracker startTracking() {
    /*public void startTracking() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.track_app);
            analytics.enableAutoActivityReports(this);
            analytics.getLogger()
                    .setLogLevel(Logger.LogLevel.VERBOSE);
        }
    }

    public Tracker getTracker() {
        // Make sure the tracker exists
        startTracking();

        // Then return the tracker
        return mTracker;
    }*/
}
