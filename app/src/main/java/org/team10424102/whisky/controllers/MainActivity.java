package org.team10424102.whisky.controllers;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.controllers.sub.FragmentActivities;
import org.team10424102.whisky.controllers.sub.FragmentMatches;
import org.team10424102.whisky.controllers.sub.FragmentMessages;
import org.team10424102.whisky.controllers.sub.FragmentProfile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT < 21 && Build.VERSION.SDK_INT > 15) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        setContentView(R.layout.activity_main);

        FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        addTab(tabhost, "activities", R.string.tab_activities, R.drawable.tab_activities, FragmentActivities.class);
        addTab(tabhost, "matches", R.string.tab_matches, R.drawable.tab_matches, FragmentMatches.class);
        addTab(tabhost, "messages", R.string.tab_messages, R.drawable.tab_messages, FragmentMessages.class);
        addTab(tabhost, "profile", R.string.tab_profile, R.drawable.tab_profile, FragmentProfile.class);
    }

    private void addTab(FragmentTabHost tabhost, String tag, @StringRes int tiltle,
                        @DrawableRes int icon, Class<? extends Fragment> cls) {
        TabHost.TabSpec spec = tabhost.newTabSpec(tag);
        View indicator = LayoutInflater.from(this)
                .inflate(R.layout.tab_indicator, tabhost.getTabWidget(), false);
        ((TextView) indicator.findViewById(R.id.title)).setText(getResources().getString(tiltle));
        ((ImageView) indicator.findViewById(R.id.icon)).setImageResource(icon);
        spec.setIndicator(indicator);
        tabhost.addTab(spec, cls, null);
    }

}
