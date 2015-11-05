package org.team10424102.whisky.controllers.sub;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.team10424102.whisky.Global;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.databinding.FragmentProfileBinding;
import org.team10424102.whisky.models.Profile;
import org.team10424102.whisky.utils.DimensionUtils;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yy on 11/5/15.
 */
public class FragmentProfile extends Fragment {
    private static final String TAG = "FragmentProfile";

    private CircleImageView mAvatar;
    private ImageView mBackground;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setProfile(Global.getProfile());

        mAvatar = binding.avatar;
        mBackground = binding.background;

        // 初始化标签页
        ViewPager viewPager = binding.viewpager;
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), getActivity()));

        TabLayout tabLayout = binding.tabs;
        tabLayout.setupWithViewPager(viewPager);

        // 初始化 Toolbar
        Toolbar toolbar = binding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        binding.collapseToolbar.setTitleEnabled(false);

        if (Build.VERSION.SDK_INT < 21) {
            int statusBarHeight = DimensionUtils.getStatusBarHeight(getResources());
            int paddingTop = binding.fuckstatusbar.getPaddingTop() - statusBarHeight;
            binding.fuckstatusbar.setPadding(0, paddingTop, 0, 0);
            AppBarLayout.LayoutParams lp =
                    (AppBarLayout.LayoutParams) binding.collapseToolbar.getLayoutParams();
            lp.height -= statusBarHeight;
            binding.collapseToolbar.setLayoutParams(lp);
        }

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Global.apiService.getProfile().enqueue(new ApiCallback<Profile>(getActivity(), 200) {
            @Override
            public void handleSuccess(Profile profile) {
                Global.setProfile(profile);
                Log.d(TAG, "获得用户资料：" + profile.toString());

                if (profile.getAvatar() != null) {
                    Global.picasso.load(Global.host + "/api/image?q=" + profile.getAvatar().getAccessToken()).into(mAvatar);
                }

                if (profile.getBackground() != null) {
                    Global.picasso.load(Global.host + "/api/image?q=" + profile.getBackground().getAccessToken()).into(mBackground);
                }
            }
        });
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
                new FragmentMyActivities(),
                new FragmentMyMatches(),
                new FragmentMyInfo(),
                new FragmentMyPosts(),
                new FragmentMyPhotos()
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
