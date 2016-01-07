package org.team10424102.whisky.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.team10424102.whisky.App;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App app = (App)getApplication();
        app.getObjectGraph().inject(this);
    }
}
