package org.team10424102.whisky.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.team10424102.whisky.App;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getObjectGraph().inject(this);
    }
}
