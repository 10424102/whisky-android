package org.team10424102.whisky.controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.auth.AccountService;
import org.team10424102.whisky.controllers.posts.MyMatchesFragment;
import org.team10424102.whisky.controllers.posts.MyPostsFragment;
import org.team10424102.whisky.databinding.FragmentProfileBinding;
import org.team10424102.whisky.utils.DimensionUtils;

/**
 * Created by yy on 11/5/15.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private FragmentProfileBinding mBinding;

    private void init() {
        // 初始化标签页
        ViewPager viewPager = mBinding.viewpager;
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), getActivity()));

        TabLayout tabLayout = mBinding.tabs;
        tabLayout.setupWithViewPager(viewPager);

        // 初始化 Toolbar
        Toolbar toolbar = mBinding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        mBinding.collapseToolbar.setTitleEnabled(false);

        if (Build.VERSION.SDK_INT < 21) {
            int statusBarHeight = DimensionUtils.getStatusBarHeight();
            int paddingTop = mBinding.fuckstatusbar.getPaddingTop() - statusBarHeight;
            mBinding.fuckstatusbar.setPadding(0, paddingTop, 0, 0);
            AppBarLayout.LayoutParams lp =
                    (AppBarLayout.LayoutParams) mBinding.collapseToolbar.getLayoutParams();
            lp.height -= statusBarHeight;
            mBinding.collapseToolbar.setLayoutParams(lp);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        Intent intent = new Intent(getContext(), AccountService.class);
        getContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                AccountService.InnerBinder binder = (AccountService.InnerBinder) service;
                mBinding.setAccount(binder.getService().getCurrentAccount());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

        init();

        return mBinding.getRoot();
    }

    public static class PagerAdapter extends FragmentPagerAdapter {

        private final int mTitles[] = new int[]{
                R.string.profile_activities,
                R.string.profile_matches,
                R.string.profile_info,
                R.string.profile_posts,
                R.string.profile_photos
        };

        private final Fragment[] mFragments = new Fragment[]{
                new MyActivitiesFragment(),
                new MyMatchesFragment(),
                new MyInfoFragment(),
                new MyPostsFragment(),
                new MyPhotosFragment()
        };

        private final Context mContext;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getString(mTitles[position]);
        }
    }
}
