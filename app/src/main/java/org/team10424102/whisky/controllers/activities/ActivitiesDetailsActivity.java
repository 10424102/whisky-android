package org.team10424102.whisky.controllers.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import org.team10424102.whisky.R;
import org.team10424102.whisky.databinding.ActivitiesDetailsActivityBinding;
import org.team10424102.whisky.models.Activity;

public class ActivitiesDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Activity activity = intent.getParcelableExtra("activity");

        ActivitiesDetailsActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activities_details_activity);


        setSupportActionBar(binding.toolbar);

        binding.toolbar.setNavigationIcon(R.drawable.ic_back);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        binding.toolbar.invalidate();


        binding.setActivity(activity);

    }

}
