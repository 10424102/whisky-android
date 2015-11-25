package org.team10424102.whisky.controllers;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.models.Profile;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏, 会导致 toolbar 向上提 25dp 所以要设置 padding
        // 但是 4.4 以后这段代码是不起作用的, 所以不要 padding
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setContentView(R.layout.activity_main);

        FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        addTab(tabhost, "activities", R.string.tab_activities, R.drawable.tab_activities, ActivitiesFragment.class);
        addTab(tabhost, "matches", R.string.tab_matches, R.drawable.tab_matches, MatchesFragment.class);
        addTab(tabhost, "messages", R.string.tab_messages, R.drawable.tab_messages, MessagesFragment.class);
        addTab(tabhost, "profile", R.string.tab_profile, R.drawable.tab_profile, ProfileFragment.class);

        loadDataFromInternet();
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

    private void loadDataFromInternet() {
        App.api().getProfile().enqueue(new ApiCallback<Profile>(200) {
            @Override
            public void handleSuccess(Profile profile) {
                Log.d(TAG, "获得用户资料：" + profile.toString());

                Profile p = App.getProfile();

                p.setPhone(profile.getPhone());
                p.setUsername(profile.getUsername());
                p.setEmail(profile.getEmail());
                p.setBirthday(profile.getBirthday());
                p.setCollege(profile.getCollege());
                p.setAcademy(profile.getAcademy());
                p.setGrade(profile.getGrade());
                p.setSignature(profile.getSignature());
                p.setHometown(profile.getHometown());
                p.setHighschool(profile.getHighschool());
                p.setAvatar(profile.getAvatar());
                p.setBackground(profile.getBackground());
                p.setGender(profile.getGender());
                p.setNickname(profile.getNickname());
            }
        });
    }

}
