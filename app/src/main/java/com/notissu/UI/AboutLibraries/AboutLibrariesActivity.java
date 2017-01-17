package com.notissu.UI.AboutLibraries;

import android.os.Bundle;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;
import com.notissu.Util.ResString;

public class AboutLibrariesActivity extends LibsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        LibsBuilder libsBuilder = new LibsBuilder()
                .withLibraries("numberpicker","rome")
                .withActivityTitle(ResString.getInstance().getString(ResString.RES_SETTING_TITLE_ABOUT_LIBRARIES))
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withActivityTheme(com.mikepenz.aboutlibraries.R.style.AboutLibrariesTheme_Light_DarkToolbar);

        setIntent(libsBuilder.intent(this));
        super.onCreate(savedInstanceState);

    }
}
