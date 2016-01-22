package org.team10424102.whisky.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.components.auth.AccountService;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private AccountService mAccountService;
    private boolean mBound;
    @Inject BlackServerApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏, 会导致 toolbar 向上提 25dp 所以要设置 padding
        // 但是 4.4 以后这段代码是不起作用的, 所以不要 padding
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setContentView(R.layout.main_activity);

        FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        tabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        addTab(tabhost, "activities", R.string.tab_activities, R.drawable.tab_activities, ActivitiesFragment.class);
        addTab(tabhost, "matches", R.string.tab_matches, R.drawable.tab_matches, MatchesFragment.class);
        addTab(tabhost, "messages", R.string.tab_messages, R.drawable.tab_messages, MessagesFragment.class);
        addTab(tabhost, "profile", R.string.tab_profile, R.drawable.tab_profile, ProfileFragment.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AccountService.class);
        bindService(intent, mConnention, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnention);
    }

    private ServiceConnection mConnention = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AccountService.InnerBinder binder = (AccountService.InnerBinder) service;
            mAccountService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
