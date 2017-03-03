package com.notissu.UI.Setting.AboutLibraries;

import android.os.Bundle;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;
import com.notissu.R;

import butterknife.BindString;
import butterknife.ButterKnife;

public class AboutLibrariesActivity extends LibsActivity {
    @BindString(R.string.setting_title_about_libraries)
    String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        LibsBuilder libsBuilder = new LibsBuilder()
                .withLibraries("numberpicker", "rome")
                .withActivityTitle(mTitle)
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withActivityTheme(com.mikepenz.aboutlibraries.R.style.AboutLibrariesTheme_Light_DarkToolbar);

        setIntent(libsBuilder.intent(this));
        super.onCreate(savedInstanceState);

    }
}
