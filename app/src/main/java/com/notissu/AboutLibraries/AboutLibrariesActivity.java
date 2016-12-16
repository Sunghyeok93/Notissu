package com.notissu.AboutLibraries;

import android.os.Bundle;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;
import com.notissu.Fragment.SettingFragment;

public class AboutLibrariesActivity extends LibsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        LibsBuilder libsBuilder = new LibsBuilder()
                .withLibraries("numberpicker","rome")
                .withActivityTitle(getIntent().getStringExtra(SettingFragment.KEY_ABOUT_LIBRARIES_TITLE))
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withActivityTheme(com.mikepenz.aboutlibraries.R.style.AboutLibrariesTheme_Light_DarkToolbar);

        setIntent(libsBuilder.intent(this));
        super.onCreate(savedInstanceState);

    }
}
