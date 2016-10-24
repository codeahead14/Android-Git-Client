package com.example.gaurav.gitfetchapp.Repositories;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.gaurav.gitfetchapp.R;

/**
 * Created by GAURAV on 02-10-2016.
 */
public class RepositoriesSettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new RepositoriesSettingFragment()).commit();
    }
}
