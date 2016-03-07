package org.team10424102.whisky.controllers;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.team10424102.whisky.R;
import org.team10424102.whisky.components.BlackServerApi;
import org.team10424102.whisky.databinding.ActivitiesFragmentBinding;
import org.team10424102.whisky.models.Activity;
import org.team10424102.whisky.ui.ActivitySliderView;
import org.team10424102.whisky.ui.MarginDownDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ActivitiesFragment extends BaseFragment {
    public static final int PAGE_SIZE = 5;
    private RecyclerView list;
    private List<Activity> activities = new ArrayList<>();
    private SliderLayout slider;
    private static final String[] categories = {"school", "friends", "focuses"};
    private int currentCategory = 0;
    private String[] localizedCategory;
    private ObservableField<String> category = new ObservableField<>();

    @Inject BlackServerApi api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localizedCategory = new String[]{
                getString(R.string.activities_category_school),
                getString(R.string.activities_category_friends),
                getString(R.string.activities_category_focuses)
        };
        category.set(localizedCategory[currentCategory]);
    }

    private void loadPage(int page) {
        api.getActivities(categories[currentCategory], page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Activity>>() {
                    @Override
                    public void call(List<Activity> activities) {
                        ActivitiesFragment.this.activities.addAll(activities);
                        list.getAdapter().notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e("failed to fetch " + categories[currentCategory] + " activities", throwable);
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        slider.startAutoCycle();
    }

    @Override
    public void onPause() {
        slider.stopAutoCycle();
        super.onPause();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActivitiesFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.activities_fragment, container, false);
        binding.setCategory(category);

        Toolbar toolbar = binding.toolbar;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);

        Spinner spinner = binding.spinner;
        spinner.setAdapter(new ArrayAdapter<>(ab.getThemedContext(),
                android.R.layout.simple_spinner_item, localizedCategory));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentCategory != position) {
                    currentCategory = position;
                    category.set(localizedCategory[currentCategory]);
                    activities.clear();
                    list.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        slider = binding.slider;
        slider.setCustomIndicator(binding.customIndicator);
        slider.setDuration(3500);
        slider.setPresetTransformer(SliderLayout.Transformer.Fade);
        api.getActivities("recommendations", 0, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Activity>>() {
                    @Override
                    public void call(List<Activity> activities) {
                        slider.stopAutoCycle();
                        slider.removeAllSliders();
                        for (Activity activity : activities) {
                            ActivitySliderView view = new ActivitySliderView(getContext(), activity);
                            view.bundle(new Bundle());
                            view.getBundle().putLong("id", activity.getId());
                            view.setOnSliderClickListener(sliderClickListener);
                            slider.addSlider(view);
                        }
                        slider.startAutoCycle();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e("failed to fetch recommended activities", throwable);
                        throwable.printStackTrace();
                    }
                });

        list = binding.list;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        list.addItemDecoration(new MarginDownDecoration(getContext()));
        list.setAdapter(new ActivitiesAdapter(activities));
        list.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadPage(currentPage);
            }
        });

        //loadPage(0);

        return binding.getRoot();
    }

    private final BaseSliderView.OnSliderClickListener sliderClickListener = new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView slider) {
            Bundle bundle = slider.getBundle();
            long id = bundle.getLong("id");
            api.getActivity(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Activity>() {
                        @Override
                        public void call(Activity activity) {
                            Intent intent = new Intent(getContext(), ActivitiesDetailsActivity.class);
                            intent.putExtra("activity", activity);
                            startActivity(intent);
                        }
                    });
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activities_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.school:
                break;
            case R.id.friends:
                break;
            case R.id.focuses:
                break;
        }
        return false;
    }
}
