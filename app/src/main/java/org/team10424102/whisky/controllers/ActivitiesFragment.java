package org.team10424102.whisky.controllers;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.team10424102.whisky.App;
import org.team10424102.whisky.R;
import org.team10424102.whisky.components.ApiCallback;
import org.team10424102.whisky.databinding.FragmentActivitiesBinding;
import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.models.enums.EActivitiesCategory;
import org.team10424102.whisky.ui.ActivitySliderView;
import org.team10424102.whisky.ui.MarginDownDecoration;
import org.team10424102.whisky.utils.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesFragment extends Fragment {
    public static final int PAGE_SIZE = 5;
    private final ObservableField<EActivitiesCategory> mType = new ObservableField<>(EActivitiesCategory.SCHOOL);
    private RecyclerView mList;
    private List<Activity> mActivities = new ArrayList<>();
    private SliderLayout mSlider;

    private void loadActivitiesFromServer(int page) {
        App.api()
                .getActivities(EActivitiesCategory.SCHOOL.toString(), page, PAGE_SIZE).enqueue(
                new ApiCallback<List<Activity>>() {
                    @Override
                    protected void handleSuccess(List<Activity> result) {
                        mActivities.addAll(result);
                        mList.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    private void initToolbar(Toolbar toolbar, Spinner spinner) {
        // 初始化 Toolbar
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);

        spinner.setAdapter(new ArrayAdapter<>(ab.getThemedContext(),
                android.R.layout.simple_spinner_item,
                EnumUtils.getStringList(getContext(), EActivitiesCategory.class)));
    }

    private void initImageSlider(SliderLayout slider, PagerIndicator indicator) {
        mSlider = slider;
        mSlider.setCustomIndicator(indicator);
        //mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        App.api().getActivities(EActivitiesCategory.RECOMMENDATIONS.toString(), 0, 5).enqueue(
                new ApiCallback<List<Activity>>() {
                    @Override
                    protected void handleSuccess(List<Activity> result) {
                        mSlider.stopAutoCycle();
                        mSlider.removeAllSliders();
                        for (Activity activity : result) {
                            ActivitySliderView view = new ActivitySliderView(getContext());
                            final Activity ac = activity;
                            view
                                    .description(ac.getTitle())
                                    .image(ac.getCover().url())
                                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                        @Override
                                        public void onSliderClick(BaseSliderView slider) {
                                            Toast.makeText(getContext(), "Activity: " + ac.getId(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                            view.setPicasso(App.getPicasso());
                            view.setGame(ac.getGame().getType());
                            view.bundle(new Bundle());
                            view.getBundle()
                                    .putLong("id", ac.getId());
                            mSlider.addSlider(view);
                        }
                        mSlider.startAutoCycle();
                    }
                }
        );

        mSlider.setDuration(4000);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
    }

    private void initRecyclerView(RecyclerView list) {
        mList = list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(new MarginDownDecoration(getContext()));
        mList.setAdapter(new ActivitiesAdapter(mActivities));
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadActivitiesFromServer(current_page);
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivitiesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false);
        binding.setType(mType);

        initToolbar(binding.toolbar, binding.spinner);
        initImageSlider(binding.slider, binding.customIndicator);
        initRecyclerView(binding.list);

        loadActivitiesFromServer(0);

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }
}
