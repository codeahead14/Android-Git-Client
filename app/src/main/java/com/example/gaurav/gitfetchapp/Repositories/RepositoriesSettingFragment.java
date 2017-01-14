package com.example.gaurav.gitfetchapp.Repositories;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.gaurav.gitfetchapp.R;

/**
 * Created by GAURAV on 02-10-2016.
 */
public class RepositoriesSettingFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener{
    private static final String TAG = RepositoriesSettingFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.repositories_settings);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_repositories_sort)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_repositories_direction)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_repositories_type)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_repositories_visibility)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String value = o.toString();
        if(preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        return true;
    }
}
