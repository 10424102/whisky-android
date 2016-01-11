package org.team10424102.whisky.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.components.ErrorManager;
import org.team10424102.whisky.controllers.BaseFragment;
import org.team10424102.whisky.controllers.EndlessRecyclerOnScrollListener;
import org.team10424102.whisky.databinding.ActivitiesFragmentBinding;
import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.ui.ActivitySliderView;
import org.team10424102.whisky.ui.MarginDownDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivitiesFragment extends BaseFragment {
    public static final int PAGE_SIZE = 5;
    private RecyclerView mList;
    private List<Activity> mActivities = new ArrayList<>();
    private SliderLayout mSlider;
    private ObservableField<String> mCategory = new ObservableField<>("school");
    private String[] mCategories = {"school", "friends", "focuses"};
    private String[] mLocalizedCategory;

    @Inject BlackServerApi mApi;
    @Inject ErrorManager mErrorManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocalizedCategory = new String[]{
                getString(R.string.activities_category_school),
                getString(R.string.activities_category_friends),
                getString(R.string.activities_category_focuses)
        };
    }

    private void loadPage(int page) {
        mApi.getActivities(mCategory.get(), page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activities -> {
                    mActivities.addAll(activities);
                    mList.getAdapter().notifyDataSetChanged();
                });
    }

    private void initImageSlider(SliderLayout slider, PagerIndicator indicator) {
        mSlider = slider;
        mSlider.setCustomIndicator(indicator);
        mSlider.setDuration(3500);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);

        mApi.getActivities("recommendations", 0, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activities -> {
                    mSlider.stopAutoCycle();
                    mSlider.removeAllSliders();
                    for (Activity activity : activities) {
                        ActivitySliderView view = new ActivitySliderView(getContext(), activity);
                        view.bundle(new Bundle());
                        view.getBundle().putLong("id", activity.getId());
                        view.setOnSliderClickListener(slider1 -> {
                            Bundle bundle = slider1.getBundle();
                            long id = bundle.getLong("id");
                            getActivity(id);
                        });
                        mSlider.addSlider(view);
                    }
                    mSlider.startAutoCycle();
                });
    }

    private void getActivity(long id) {
        mApi.getActivity(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activity -> {
                    Intent intent = new Intent(getContext(), ActivitiesDetailsActivity.class);
                    intent.putExtra("activity", activity);
                    startActivity(intent);
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSlider.startAutoCycle();
    }

    @Override
    public void onPause() {
        mSlider.stopAutoCycle();
        super.onPause();
    }

    private void initRecyclerView(RecyclerView list) {
        mList = list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(new MarginDownDecoration(getContext()));
        mList.setAdapter(new ActivitiesAdapter(mActivities));
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadPage(currentPage);
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActivitiesFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.activities_fragment, container, false);
        binding.setCategory(mCategory);

        Toolbar toolbar = binding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);

        Spinner spinner = binding.spinner;
        spinner.setAdapter(new ArrayAdapter<>(ab.getThemedContext(),
                android.R.layout.simple_spinner_item, mLocalizedCategory));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = mCategories[position];
                if (!category.equals(mCategory.get())) {
                    mCategory.set(category);
                    mActivities.clear();
                    mList.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initImageSlider(binding.slider, binding.customIndicator);

        initRecyclerView(binding.list);

        loadPage(0);

        return binding.getRoot();
    }
}
