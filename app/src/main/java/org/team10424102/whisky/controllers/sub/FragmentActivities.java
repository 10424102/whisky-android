package org.team10424102.whisky.controllers.sub;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.team10424102.whisky.R;
import org.team10424102.whisky.controllers.adapters.ActivitiesAdapter;
import org.team10424102.whisky.databinding.FragmentActivitiesBinding;
import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FragmentActivities extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {
    private final ObservableField<Activity.Type> mType = new ObservableField<>(Activity.Type.SCHOOL);
    private RecyclerView mList;
    private List<Activity> mRecommandedActivities = new ArrayList<>();
    private List<Activity> mActivities = new ArrayList<>();
    private SliderLayout mSlider;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivitiesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false);
        binding.setType(mType);

        // 初始化 Toolbar
        Toolbar toolbar = binding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        binding.collapseToolbar.setTitleEnabled(false);

        // 初始化 Image Slider
        mSlider = binding.slider;

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("DOTA2", R.drawable.demo_activity_poster);
        file_maps.put("我的世界", R.drawable.demo_activity_poster_1);
        file_maps.put("英雄联盟", R.drawable.demo_activity_poster_2);
        file_maps.put("星际争霸", R.drawable.demo_activity_poster_3);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSlider.addSlider(textSliderView);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);

        // 初始化 RecyclerView
        mList = binding.list;
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        Activity a1 = new Activity();
        a1.setTitle("活动一");
        a1.setStartTime(new Date());
        a1.setLocation("地点一");

        Activity a2 = new Activity();
        a2.setTitle("活动二");
        a2.setStartTime(new Date());
        a2.setLocation("地点二");

        Activity a3 = new Activity();
        a3.setTitle("活动三");
        a3.setStartTime(new Date());
        a3.setLocation("地点三");

        List<Activity> data = new ArrayList<>();
        data.add(a1);
        data.add(a2);
        data.add(a3);

        mList.setAdapter(new ActivitiesAdapter(data));


        // 加载推荐活动
//        Global.apiService.getRecommandedActivities().enqueue(new ApiCallback<List<Activity>>(getActivity(), 200) {
//            @Override
//            public void handleSuccess(List<Activity> result) {
//                mRecommandedActivities = result;
//            }
//        });

        // 加载活动列表
//        Global.apiService.getActivities(mType.get(), 0, 5, null).enqueue(new ApiCallback<List<Activity>>(getActivity(), 200) {
//            @Override
//            public void handleSuccess(List<Activity> result) {
//                mActivities = result;
//            }
//        });

        return binding.getRoot();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }
}
